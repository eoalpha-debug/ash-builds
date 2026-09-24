# 🛡️ Ash Builds

**Guia oficial do meta de Honor of Kings (servidor global)** — tier list ao vivo, catálogo de todos os heróis, builds reais PT-BR, counters e notas de patch oficiais.

## ✨ O que o app faz

- **Tier list por rota** (Top / Selva / Mid / ADC / Suporte) com dados oficiais do HoK Camp global
- **Catálogo com 118 campeões** — imagens oficiais, dificuldade real (1–5★), WR/Pick/Ban oficiais
- **Builds PT-BR verificadas** por pro players brasileiros (itens, arcanas e feitiços com imagens reais)
- **Counters & Sinergias** — fraco contra / forte contra / melhor combinação por herói
- **Mudanças do patch atual (S16)** — buffs, nerfs e reajustes oficiais com detalhes ao clicar
- **Modo offline** — salva builds localmente (Room) e usa cache quando sem internet
- **Painel Admin Web** para editar manualmente o conteúdo (tier, destaque, banner, builds, stats)

## 🛠️ Estrutura do projeto

```
Ash Builds/
├── app/                      # App Android (Kotlin + Jetpack Compose)
│   └── src/main/assets/meta/ # Banco de dados embutido (heroes, itens, builds, counters)
├── admin/                    # Painel web (GitHub Pages) para controle manual
│   ├── index.html            # Interface do painel
│   └── overrides.json        # Arquivo baixado pelo app no sync
└── tools/                    # Scripts de sincronização de dados (Python)
    ├── sync_camp_full.py     # Dados oficiais do HoK Camp global
    ├── sync_hokpro_builds.py # Builds validadas pela comunidade BR
    ├── sync_counters.py      # Counters/sinergias
    └── scrape_patch_notes.py # Detalhes completos do site oficial
```

## 📱 Rodar o app local

**Pré-requisito:** [Android Studio](https://developer.android.com/studio)

1. Abra o Android Studio
2. **Open** → selecione a pasta deste projeto
3. Espere a sincronização do Gradle
4. Rode em um dispositivo USB (debug) ou emulador (recomendado: API 24+)

## 🎛️ Rodar o painel admin

É só uma página HTML estática — abra direto no navegador ou suba via GitHub Pages.

URL usada pelo app por padrão:
```
https://raw.githubusercontent.com/eoalpha-debug/ash-builds/main/admin/overrides.json
```

## 📝 Licença

Projeto educacional da comunidade. Honor of Kings é marca da TiMi Studio Group / Level Infinite.
