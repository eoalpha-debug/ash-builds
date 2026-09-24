#!/usr/bin/env python3
"""
sync_camp_full.py â€” Snapshot COMPLETO do HoK Camp global (S16+), gerado a partir
dos endpoints oficiais. Rode a cada patch:

    python tools/sync_camp_full.py

SaÃ­das em tools/out/ (copie para app/src/main/assets/meta/):
  - camp_rankings_snapshot.json : { nome: {tier, winRate, pickRate, banRate} }  (WR oficial)
  - camp_full.json              : [ {slug,name,heroImgId?,icon,roles,hot,skills,baseData,adjust} ]  (skills/ajustes oficiais)
  - camp_patch.json             : { season, date, changes: [...] }  (ajustes do patch atual, ex.: S16)
"""
import asyncio
import json
import os
import re
import sys
import unicodedata

OUT_DIR = os.path.join(os.path.dirname(__file__), "out")
TIER_MAP = {0: "SS", 1: "SS", 2: "S", 3: "A", 4: "B"}  # tRank observado no endpoint

HTML_TAG = re.compile(r"<[^>]+>")


def slugify(name: str) -> str:
    s = unicodedata.normalize("NFKD", name).encode("ascii", "ignore").decode()
    return re.sub(r"[^a-z0-9]+", "-", s.lower()).strip("-")


def clean(text: str) -> str:
    return HTML_TAG.sub("", text or "").replace("\r", "").strip()


async def main() -> int:
    try:
        from hok import HOKAPI, Position, RankType, cache_manager
    except ImportError:
        print("pip install hok-camp-api orjson")
        return 1

    await cache_manager.initialize()
    api = HOKAPI(region=608, language="pt-br")   # textos em PT-BR
    api_en = HOKAPI(region=608, language="en")   # nomes canônicos EN para mapear com o app

    try:
        heroes = await api.get_all_heroes()
        heroes_en = await api_en.get_all_heroes()
        en_name = {h.heroId: h.heroName for h in heroes_en}
        print(f"Heróis no Camp global: {len(heroes)}")

        rankings = await api_en.get_hero_rankings(rank_type=RankType.TIER, position=Position.ALL)

        def pct(v):
            v = float(v or 0)
            return round(v * 100 if v <= 1.0 else v, 1)

        snap = {}
        for e in rankings:
            name = getattr(e, "heroName", None) or next(
                (h.heroName for h in heroes if h.heroId == e.heroId), str(e.heroId))
            t = getattr(e, "tRank", 4)
            snap[name] = {
                "tier": TIER_MAP.get(t if t in TIER_MAP else 4, "B"),
                "winRate": pct(getattr(e, "winRate", 0)),
                "pickRate": pct(getattr(e, "showRate", 0)),
                "banRate": pct(getattr(e, "banRate", 0)),
            }

        full = []
        current_adjusts = {}
        total = len(heroes)
        for i, h in enumerate(heroes, 1):
            try:
                det = await api.get_hero_details(h.heroId)
            except Exception as ex:
                print(f"[{i}/{total}] {h.heroName}: falha ({ex.__class__.__name__}), pulando")
                continue

            sd = det.get("strategyData", {}) or {}
            hd = det.get("heroData", {}) or {}
            base = hd.get("baseData", {}) or {}

            skills = []
            for group in sd.get("skill", []) or []:
                for sk in group.get("skillList", []) or []:
                    skills.append({
                        "name": clean(sk.get("skillName", "")),
                        "description": clean(sk.get("skillDesc", "")),
                        "icon": sk.get("skillIcon", ""),
                        "isPassive": bool(sk.get("isPassive")),
                        "isUlt": bool(sk.get("isUlt")),
                        "tags": [t.get("name", "") for t in sk.get("skillTag", []) or []],
                    })

            adjust = (hd.get("adjustData") or [])
            current = next((a for a in adjust if a.get("isCurrent")), None)
            if current:
                tag = (current.get("adjustContent") or {}).get("contentTag", {}) or {}
                current_adjusts[en_name.get(h.heroId, h.heroName)] = {
                    "shortDesc": (current.get("adjustContent") or {}).get("shortDesc", ""),
                    "tag": tag.get("text", ""),
                    "season": current.get("seasonName", ""),
                    "date": current.get("versionName", ""),
                }

            full.append({
                "heroId": h.heroId,
                "slug": slugify(en_name.get(h.heroId, h.heroName)),
                "name": en_name.get(h.heroId, h.heroName),  # nome EN canónico para casar com o app
                "icon": h.icon,
                "roles": [x for x in [h.mainJobName, h.minorJobName] if x],
                "lane": h.recommendRoadName,
                "hot": base.get("hot", ""),  # tier oficial S/A/B...
                "baseData": {
                    "winRate": base.get("winRate", ""),
                    "matchRate": base.get("matchRate", ""),
                    "banRate": base.get("banRate", ""),
                },
                "skills": skills,
            })
            print(f"[{i}/{total}] {h.heroName} ok ({len(skills)} skills)")

        patch_changes = []
        season = ""
        date = ""
        for name, adj in current_adjusts.items():
            season = adj["season"] or season
            date = adj["date"] or date
            patch_changes.append({"heroName": name, "changeType": adj["tag"] or adj["shortDesc"], "summary": adj["shortDesc"]})

        os.makedirs(OUT_DIR, exist_ok=True)
        with open(os.path.join(OUT_DIR, "camp_rankings_snapshot.json"), "w", encoding="utf-8") as f:
            json.dump(snap, f, ensure_ascii=False, indent=1)
        with open(os.path.join(OUT_DIR, "camp_full.json"), "w", encoding="utf-8") as f:
            json.dump(full, f, ensure_ascii=False)
        with open(os.path.join(OUT_DIR, "camp_patch.json"), "w", encoding="utf-8") as f:
            json.dump({"season": season or "S16", "date": date, "changes": patch_changes}, f, ensure_ascii=False, indent=1)

        print(f"OK â€” rankings: {len(snap)}, full: {len(full)}, patch {season} changes: {len(patch_changes)}")
        return 0
    finally:
        await api.close()
        await api_en.close()


if __name__ == "__main__":
    sys.exit(asyncio.run(main()))

