#!/usr/bin/env python3
"""
scrape_patch_notes.py — Lê a NOTA OFICIAL do patch no site honorofkings.com/br
usando Playwright (headless) e gera assets/meta/patch_details.json com o texto
completo por herói (a seção "Antes/Agora" mostrada no site).

Uso (uma vez por patch):
    pip install playwright
    playwright install chromium
    python tools/scrape_patch_notes.py

Saída: app/src/main/assets/meta/patch_details.json → { "Umbrosa": "texto completo...", ... }
"""
import asyncio
import json
import os
import re
import sys

PATCH_URL = "https://www.honorofkings.com/br/news-detail.html?from=2&tid=0&sid=584&pid=0&news_type=&father_content_id=d7924564ab20ca489cabc96a6799d5403f0a&content_id=d7924564ab20ca489cabc96a6799d5403f0a"
OUT = os.path.join(os.path.dirname(__file__), "..", "app", "src", "main", "assets", "meta", "patch_details.json")


def split_by_heroes(full_text: str) -> dict:
    """Divide o texto longo da nota de patch em blocos por nome de herói.

    O site lista heróis como títulos em linha própria (ex.: "Umbrosa"). Usamos
    os nomes conhecidos do camp_full.json para delimitar.
    """
    known = ["Umbrosa", "Ukyo Tachibana", "Chano", "Da Qiao", "Florentino", "Luara",
             "Ser do Fluxo (Mago)", "Ser do Fluxo (Assassino)", "Ser do Fluxo (Atirador)",
             "Ser do Fluxo (Tanque)", "Ser do Fluxo (Apoio)"]
    result = {}
    pattern = "|".join(re.escape(n) for n in sorted(known, key=len, reverse=True))
    matches = list(re.finditer(rf"^({pattern})$", full_text, re.MULTILINE))
    for i, m in enumerate(matches):
        name = m.group(1)
        start = m.end()
        end = matches[i + 1].start() if i + 1 < len(matches) else len(full_text)
        result[name] = full_text[start:end].strip()
    return result


async def main():
    try:
        from playwright.async_api import async_playwright
    except ImportError:
        print("Instale playwright: pip install playwright && playwright install chromium")
        return 1

    async with async_playwright() as p:
        browser = await p.chromium.launch(headless=True)
        page = await browser.new_page(user_agent="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
        await page.goto(PATCH_URL, wait_until="networkidle", timeout=60000)
        await page.wait_for_timeout(3000)
        content = await page.inner_text("body")
        await browser.close()

    details = split_by_heroes(content)
    os.makedirs(os.path.dirname(OUT), exist_ok=True)
    with open(OUT, "w", encoding="utf-8") as f:
        json.dump(details, f, ensure_ascii=False, indent=1)
    print(f"OK — {len(details)} heróis extraídos para {OUT}")
    return 0


if __name__ == "__main__":
    sys.exit(asyncio.run(main()))
