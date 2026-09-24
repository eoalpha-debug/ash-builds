#!/usr/bin/env python3
"""
sync_camp_snapshot.py — Gera um snapshot JSON com WR/tier oficiais do HoK Camp
(servidor GLOBAL) para uso no app Ash Builds.

Uso:
    python -m venv .venv && .venv\\Scripts\\pip install hok-camp-api orjson
    python tools/sync_camp_snapshot.py

Saída: tools/out/camp_rankings_snapshot.json
    { "<slug-ou-nome>": {"tier": "S", "winRate": 52.3, "pickRate": 10.1, "banRate": 4.2}, ... }

O app consome esse arquivo via MetaRepository.snapshotUrlOverride
(aponte para o RAW do arquivo hospedado — ex.: GitHub Pages / Cloudflare).
"""
import asyncio
import json
import os
import sys

OUT_DIR = os.path.join(os.path.dirname(__file__), "out")
OUT_FILE = os.path.join(OUT_DIR, "camp_rankings_snapshot.json")

POSITIONS = ["ALL"]  # rank geral já inclui WR por herói; ajuste se quiser por lane


async def main() -> int:
    try:
        from hok import HOKAPI, Position, RankType, cache_manager
    except ImportError:
        print("Dependência ausente. Instale com: pip install hok-camp-api orjson")
        return 1

    await cache_manager.initialize()
    api = HOKAPI(region=608, language="en")  # 608 = servidor GLOBAL (en)

    try:
        heroes = await api.get_all_heroes()
        id_to_name = {h.heroId: h.heroName for h in heroes}
        print(f"Heróis encontrados: {len(heroes)}")

        snapshot = {}
        TIER_MAP = {1: "SS", 2: "S", 3: "A", 4: "B"}
        for position in POSITIONS:
            entries = await api.get_hero_rankings(
                rank_type=RankType.TIER,
                position=getattr(Position, position),
            )
            def pct(v):
                v = float(v or 0)
                return round(v * 100 if v <= 1.0 else v, 1)  # API retorna fração (0.52 = 52%)

            for e in entries:
                name = id_to_name.get(e.heroId, str(e.heroId))
                snapshot[name] = {
                    "tier": TIER_MAP.get(getattr(e, "tRank", 4), "B"),
                    "winRate": pct(getattr(e, "winRate", 0)),
                    "pickRate": pct(getattr(e, "showRate", 0)),
                    "banRate": pct(getattr(e, "banRate", 0)),
                }

        os.makedirs(OUT_DIR, exist_ok=True)
        with open(OUT_FILE, "w", encoding="utf-8") as f:
            json.dump(snapshot, f, ensure_ascii=False, indent=1)
        print(f"OK — snapshot salvo em {OUT_FILE} ({len(snapshot)} heróis)")
        return 0
    finally:
        await api.close()


if __name__ == "__main__":
    sys.exit(asyncio.run(main()))
