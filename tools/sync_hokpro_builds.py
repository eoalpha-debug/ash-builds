#!/usr/bin/env python3
"""
sync_hokpro_builds.py â€” Baixa builds reais (itens/arcanas/feitiÃ§o) do HOK Pro
para todos os herÃ³is do servidor global.

    python tools/sync_hokpro_builds.py

Entrada: tools/out/camp_full.json (rode sync_camp_full.py antes)
SaÃ­da:   tools/out/hokpro_builds.json
    { "<slug>": {"lane","itens":[{nome,id}],"arcanas":[{nome,qtd,id}],"feitico":{nome,id}} }

Copie para app/src/main/assets/meta/hokpro_builds.json
"""
import asyncio
import json
import os
import sys

import httpx

OUT_DIR = os.path.join(os.path.dirname(__file__), "out")
IN_FILE = os.path.join(OUT_DIR, "camp_full.json")
OUT_FILE = os.path.join(OUT_DIR, "hokpro_builds.json")
BASE = "https://hokpro.gg"

LANE_TO_ROLE = {
    "Clash Lane": "Superior",
    "Jungle": "Selva",
    "Mid": "Meio",
    "Farm": "Atirador",
    "Farm Lane": "Atirador",
    "Roam": "Suporte",
    "Roamer": "Suporte",
}
ROLES = ["Superior", "Selva", "Meio", "Atirador", "Suporte"]

# Nomes do HOK Pro (PT-BR) que diferem dos nomes EN do Camp/global
ALIASES = {
    "luban-no-7": ["Luban N7"],
    "gao-changgong": ["Gao", "Changgong"],
    "mai-shiranui": ["Mai"],
    "wang-zhaojun": ["Zhaojun Meio", "Zhaojun"],
    "ukyo-tachibana": ["Ukyo Superior", "Ukyo Selva", "Ukyo"],
    "lapulapu": ["Lapu Lapu Suporte", "Lapu Lapu"],
    "annette": ["Anette Meio", "Anette Suporte", "Anette"],
    "li-xin": ["Li Xin Amarelo", "Li Xin Vermelho", "Li Xin"],
    "mayene": ["Mayenne Superior", "Mayenne Selva", "Mayenne"],
    "ao-yin": ["Ao Yin"],
    "shouyue": ["Shoyue"],
    "yango": ["Yang Jian Superior", "Yang Jian Selva", "Yang Jian"],
    "agudo": ["Agudo"],
    "flowborn-tank": ["Ser do Fluxo Superior"],
    "flowborn-marksman": ["Ser do Fluxo Atirador"],
    "flowborn-mage": ["Ser do Fluxo Meio"],
    "flowborn-assassin": ["Ser do Fluxo Selva"],
    "flowborn-roamer": ["Ser do Fluxo Suporte"],
}


async def fetch_build(client, name, role=None):
    params = {"role": role} if role else {}
    try:
        r = await client.get(f"{BASE}/api/build/{name}", params=params)
        if r.status_code == 200:
            data = r.json().get("build")
            if data and data.get("itens"):
                return data
    except Exception:
        pass
    return None


async def main() -> int:
    heroes = json.load(open(IN_FILE, encoding="utf-8"))
    out = {}
    async with httpx.AsyncClient(timeout=20, follow_redirects=True) as client:
        for i, h in enumerate(heroes, 1):
            name = h["name"]
            role = LANE_TO_ROLE.get(h.get("lane", ""), None)
            # Sem role primeiro (nome Ãºnico); se 404, tenta com role da lane e todas as rotas
            build = await fetch_build(client, name) or await fetch_build(client, name, role)
            if not build:
                for r in ROLES:
                    build = await fetch_build(client, name, r)
                    if build:
                        break
            if not build and h["slug"] in ALIASES:
                for alias in ALIASES[h["slug"]]:
                    # aliases podem incluir sufixo de rota ("Ukyo Superior")
                    parts = alias.rsplit(" ", 1)
                    if len(parts) == 2 and parts[1] in ROLES:
                        build = await fetch_build(client, parts[0], parts[1])
                    else:
                        build = await fetch_build(client, alias)
                    if build:
                        break
            if build:
                out[h["slug"]] = {
                    "lane": build.get("lane", ""),
                    "itens": [{"nome": it.get("nome", ""), "id": it.get("id", "")} for it in build.get("itens", [])],
                    "arcanas": [{"nome": a.get("nome", ""), "qtd": a.get("qtd", 10), "id": a.get("id", "")} for a in build.get("arcanas", [])],
                    "feitico": build.get("feitico") or {},
                }
                print(f"[{i}/{len(heroes)}] {name}: ok ({len(build['itens'])} itens)")
            else:
                print(f"[{i}/{len(heroes)}] {name}: SEM BUILD")
            await asyncio.sleep(0.3)  # gentil com o servidor

    os.makedirs(OUT_DIR, exist_ok=True)
    with open(OUT_FILE, "w", encoding="utf-8") as f:
        json.dump(out, f, ensure_ascii=False)
    print(f"OK â€” {len(out)}/{len(heroes)} herÃ³is com build em {OUT_FILE}")
    return 0


if __name__ == "__main__":
    sys.exit(asyncio.run(main()))

