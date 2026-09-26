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
import re
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


def scrape_hokstats_countered_by():
    """hokstats.gg/counters/ — 'Countered by' e 'Strong against' reais por herói."""
    import html as html_mod
    try:
        html = httpx.get("https://hokstats.gg/counters/", timeout=30,
                         headers={"User-Agent": "Mozilla/5.0"}).text
    except Exception as e:
        print("hokstats FAIL:", e)
        return {}, {}
    countered = {}
    strong = {}
    for art in re.split(r'<article[^>]*data-counter-hero="', html)[1:]:
        slug = art.split('"', 1)[0]
        m = re.search(r"Countered by</h3>(.*?)</div>", art, re.S)
        if m:
            names = [html_mod.unescape(n).strip() for n in re.findall(r">([^<>]+)</a>", m.group(1))]
            names = [n for n in names if n and "No verified" not in n]
            if names:
                countered[slug] = names[:5]
        m2 = re.search(r"Strong against</h3>(.*?)</div>", art, re.S)
        if m2:
            names = [html_mod.unescape(n).strip() for n in re.findall(r">([^<>]+)</a>", m2.group(1))]
            names = [n for n in names if n and "No verified" not in n]
            if names:
                strong[slug] = names[:5]
    return countered, strong


async def main():
    heroes = json.load(open(IN, encoding="utf-8"))
    # Fonte 2: hokstats.gg — counters verificados por herói (preenche até 4+)
    hokstats_cb, hokstats_strong = scrape_hokstats_countered_by()
    print(f"hokstats: {len(hokstats_cb)} counterd-by, {len(hokstats_strong)} strong-against")
    # Reverso: X 'strong against' Y => Y e counterado por X (dados reais)
    reverse_cb = {}
    for src, victims in hokstats_strong.items():
        for v in victims:
            reverse_cb.setdefault(v, []).append(src)
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
                pro = data.get("proPlayer") or {}
                # Mescla: counters diretos (hokpro) + countered-by (hokstats), dedupe, até 6
                direct = norm_entries(data.get("counters"))
                seen = {c["name"].lower() for c in direct}
                extra = []
                def add_nome(nome):
                    if nome and nome.lower() not in seen:
                        extra.append({"name": nome, "role": "", "effect": 0})
                        seen.add(nome.lower())
                # 1) countered-by explícito do hokstats
                for nome in hokstats_cb.get(h["slug"], []):
                    add_nome(nome)
                # 2) reverso do strong-against do hokstats (X forte contra este herói)
                for nome in reverse_cb.get(h["name"], []):
                    add_nome(nome)
                result[slug] = {
                    "counters": (direct + extra)[:6],
                    "synergies": norm_entries(data.get("synergies")),
                    "strongAgainst": norm_entries(data.get("strongAgainst")),
                    "proName": pro.get("name", ""),
                    "proTeam": pro.get("team", ""),
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
