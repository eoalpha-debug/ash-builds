#!/usr/bin/env python3
"""
sync_counters.py — Baixa counters/sinergias/forte-contra do HOK Pro para todos
os heróis. Rode uma vez por patch:

    python tools/sync_counters.py

Entrada: tools/out/camp_full.json
Saída:   app/src/main/assets/meta/counters.json
    { "<slug>": {"counters":[{name, role, effect}], "synergies":[...], "strongAgainst":[...] } }
"""
import asyncio
import json
import os
import sys

import httpx

BASE = "https://hokpro.gg"
OUT = os.path.join(os.path.dirname(__file__), "..", "app", "src", "main", "assets", "meta", "counters.json")
IN = os.path.join(os.path.dirname(__file__), "out", "camp_full.json")
ROLES = ["Superior", "Selva", "Meio", "Atirador", "Suporte"]
LANE_TO_ROLE = {
    "Rota superior": "Superior", "Rota inferior": "Atirador", "Rota do meio": "Meio",
    "Caça": "Selva", "Apoio": "Suporte",
    "Clash Lane": "Superior", "Jungle": "Selva", "Mid": "Meio",
    "Farm": "Atirador", "Farm Lane": "Atirador", "Roam": "Suporte",
}
ALIASES = {
    "luban-no-7": "Luban N7", "gao-changgong": "Gao", "mai-shiranui": "Mai",
    "wang-zhaojun": "Zhaojun", "ukyo-tachibana": "Ukyo", "lapulapu": "Lapu Lapu",
    "annette": "Anette", "li-xin": "Li Xin Amarelo", "mayene": "Mayenne",
    "ao-yin": "Ao Yin", "shouyue": "Shoyue", "yango": "Yang Jian",
    "ser-do-fluxo-mago": "Ser do Fluxo", "ser-do-fluxo-tanque": "Ser do Fluxo",
    "ser-do-fluxo-atirador": "Ser do Fluxo", "ser-do-fluxo-assassino": "Ser do Fluxo",
    "ser-do-fluxo-apoio": "Ser do Fluxo",
}


async def fetch(client, name, role=None):
    payload = {"hero": name}
    if role:
        payload["role"] = role
    try:
        r = await client.post(f"{BASE}/api/counter", json=payload)
        if r.status_code == 200:
            d = r.json()
            if d.get("counters"):
                return d
    except Exception:
        pass
    return None


def norm_entries(lst):
    out = []
    for it in (lst or []):
        if it.get("locked"):
            continue
        out.append({
            "name": it.get("name", ""),
            "role": it.get("role", ""),
            "effect": it.get("effectiveness", 0),
        })
    return out[:4]


async def main():
    heroes = json.load(open(IN, encoding="utf-8"))
    result = {}
    async with httpx.AsyncClient(timeout=20, follow_redirects=True) as client:
        for i, h in enumerate(heroes, 1):
            slug = h["slug"]
            name = ALIASES.get(slug, h["name"])
            role = LANE_TO_ROLE.get(h.get("lane", ""), None)
            data = await fetch(client, name, role) or await fetch(client, name)
            if not data:
                for r in ROLES:
                    data = await fetch(client, name, r)
                    if data:
                        break
            if data:
                result[slug] = {
                    "counters": norm_entries(data.get("counters")),
                    "synergies": norm_entries(data.get("synergies")),
                    "strongAgainst": norm_entries(data.get("strongAgainst")),
                }
                print(f"[{i}/{len(heroes)}] {h['name']}: ok")
            else:
                print(f"[{i}/{len(heroes)}] {h['name']}: SEM DADOS")
            await asyncio.sleep(0.3)

    os.makedirs(os.path.dirname(OUT), exist_ok=True)
    with open(OUT, "w", encoding="utf-8") as f:
        json.dump(result, f, ensure_ascii=False)
    print(f"OK — {len(result)}/{len(heroes)} heróis com counters em {OUT}")
    return 0


if __name__ == "__main__":
    sys.exit(asyncio.run(main()))
