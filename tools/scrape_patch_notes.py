#!/usr/bin/env python3
"""
scrape_patch_notes.py — Extrai o texto completo (Antes/Agora) da nota oficial
do patch no site honorofkings.com/br via Playwright headless.

Uso (após novo patch):
    pip install playwright && playwright install chromium
    python tools/scrape_patch_notes.py

Saída: app/src/main/assets/meta/patch_details.json → { "Umbrosa": "texto...", ... }
"""
import asyncio
import json
import os
import re
import sys

PATCH_URL = "https://www.honorofkings.com/br/news-detail.html?from=2&tid=0&sid=584&pid=0&news_type=&father_content_id=d7924564ab20ca489cabc96a6799d5403f0a&content_id=d7924564ab20ca489cabc96a6799d5403f0a"
OUT = os.path.join(os.path.dirname(__file__), "..", "app", "src", "main", "assets", "meta", "patch_details.json")
WALK_JS = r"""
(() => {
  function walk(n){
    let s='';
    if(!n) return '';
    if(n.shadowRoot){ s+=walk(n.shadowRoot); }
    n.childNodes.forEach(c=>{
      if(c.nodeType===3){ s+=c.textContent+"\n"; }
      else{
        if(c.tagName==='IFRAME'){ try{ s+=c.contentDocument.body.innerText; }catch(e){} }
        s+=walk(c);
      }
    });
    return s;
  }
  return walk(document.body);
})()
"""


async def main():
    try:
        from playwright.async_api import async_playwright
    except ImportError:
        print("pip install playwright && playwright install chromium")
        return 1

    async with async_playwright() as p:
        browser = await p.chromium.launch(headless=True)
        page = await browser.new_page(user_agent="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
        await page.goto(PATCH_URL, wait_until="networkidle", timeout=90000)
        for _ in range(10):
            await page.mouse.wheel(0, 2500)
            await page.wait_for_timeout(600)
        text = await page.evaluate(WALK_JS)
        await browser.close()

    # Nomes que REALMENTE tiveram mudança neste patch (do camp_patch.json)
    patch = json.load(open(os.path.join(os.path.dirname(__file__), "..", "app", "src", "main", "assets", "meta", "camp_patch.json"), encoding="utf-8"))
    targets = [c["heroName"] for c in patch["changes"]]

    lines = [ln.strip() for ln in text.splitlines()]
    details = {}
    for hero in targets:
        # procurar uma linha que seja exatamente o nome do herói
        idxs = [i for i, ln in enumerate(lines) if ln == hero]
        for i in idxs:
            # coleta até a próxima linha que seja exatamente outro nome de herói
            buf = []
            others = [t for t in targets if t != hero]
            for ln in lines[i + 1:]:
                if ln in others:
                    break
                if ln:
                    buf.append(ln)
            if buf:
                details[hero] = "\n".join(buf).strip()
                break

    os.makedirs(os.path.dirname(OUT), exist_ok=True)
    with open(OUT, "w", encoding="utf-8") as f:
        json.dump(details, f, ensure_ascii=False, indent=1)
    print(f"OK — {len(details)} heróis extraídos → {OUT}")
    return 0


if __name__ == "__main__":
    sys.exit(asyncio.run(main()))
