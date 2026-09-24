package com.example.data.model

import com.example.R

object RoamLaneChampions {

  val list: List<Champion> = listOf(
    // 1. DA QIAO
    ChampionFactory.makeHero(
      id = "daqiao",
      name = "Da Qiao",
      title = "A Guardiã dos Oceanos",
      heroClass = "Suporte",
      lane = Lane.SUPORTE,
      tier = HeroTier.SS,
      gtimgId = 191,
      localDrawableRes = R.drawable.img_hero_daqiao,
      difficultyStars = 4,
      winRate = "54.5%",
      winRateChange = "+1.6%",
      pickRate = "17.9%",
      banRate = "48.7%",
      isBanPriority = true,
      coreItemSummary = "Armadura Ágata + Manto Glacial",
      buildSubtitle = "Teleporte imediato de aliados de volta à base com cura total e invocação coletiva de equipe no Supremo.",
      spellName = "Golpe de Fagulha",
      spellDescription = "Permite se afastar de iniciações inimigas para posicionar os portais em segurança.",
      items = listOf(
        EquipmentItem("dq_1", "Emblema do Guardião", "Suporte", "Aura de Defesa", "+800 Vida • Aura de Proteção", "shield"),
        EquipmentItem("dq_2", "Botas de Agilidade", "Movimento", "Rotação rápida", "+80 Vel. Movimento fora de combate", "snowshoeing"),
        EquipmentItem("dq_3", "Cálice Sagrado", "Mágica", "Regeneração Contínua", "+160 AP • Cura de Vida e Mana", "gavel"),
        EquipmentItem("dq_4", "Clamor de Gelo", "Defesa", "Armadura e CDR", "+360 Defesa • 10% CDR", "shield"),
        EquipmentItem("dq_5", "Olho da Fênix", "Defesa", "Resistência Mágica", "+240 Def. Mágica • +1200 Vida", "shield"),
        EquipmentItem("dq_6", "Lâmina Sábia", "Defesa", "Ressurreição", "+100 Armadura", "shield")
      ),
      arcanas = ChampionFactory.buildTankArcanas(),
      arcanaStatsSummary = ChampionFactory.standardTankSummary,
      skills = listOf(
        SkillInfo("Elo dos Mares", "Passiva", "VELOCIDADE PARA DUPLA", "Concede velocidade de movimento bônus contínua a Da Qiao e ao aliado mais próximo.", "radar"),
        SkillInfo("Onda Rompedora (H1)", "Habilidade 1", "EMPURRÃO E ACELERAÇÃO", "Invoca carpa d'água que empurra inimigos para os lados e acelera aliados que pisarem.", "waves"),
        SkillInfo("Silêncio dos Mares (H2)", "Habilidade 2", "SILÊNCIO EM ÁREA", "Cria piscina d'água que silencia inimigos instantaneamente impedindo magias.", "waves", isMaxPriority = true),
        SkillInfo("Portal de Retorno (H3)", "Habilidade 3", "TELEPORTE DE BASE", "Cria círculo que envia todos os aliados dentro de volta à base com vida 100% cheia.", "autorenew"),
        SkillInfo("Olho do Tufão", "Supremo", "INVOCAÇÃO GLOBAL", "Cria portal gigante: qualquer aliado vivo no mapa pode apertar o botão para se teleportar para ele.", "thunderstorm")
      ),
      combos = listOf(
        ComboStep(1, "Resgate com Portal H3", "Coloque o portal da H3 sob o aliado quase morto para enviá-lo à base instantaneamente."),
        ComboStep(2, "Supremo de Retorno Imediato", "Abra o Supremo na luta para que o aliado curado da base clique e volte com vida 100% cheia em 2 segundos.")
      ),
      proTips = listOf(
        ProTip("Teleporte Coletivo", "O Supremo pode reunir todos os 5 jogadores no dragão ou na torre inimiga em 1 segundo.")
      ),
      shortTip = "A combinação H3 (cura na base) + Supremo (retorno instantâneo à luta) permite que seu atirador recupere toda a vida e volte em 2 segundos."
    ),

    // 2. DOLIA
    ChampionFactory.makeHero(
      id = "dolia",
      name = "Dolia",
      title = "A Sereia dos Mares",
      heroClass = "Suporte",
      lane = Lane.SUPORTE,
      tier = HeroTier.SS,
      gtimgId = 564,
      localDrawableRes = R.drawable.img_hero_dolia,
      difficultyStars = 3,
      winRate = "54.8%",
      winRateChange = "+1.8%",
      pickRate = "22.1%",
      banRate = "51.3%",
      isBanPriority = true,
      coreItemSummary = "Emblema de Proteção + Manto Glacial",
      buildSubtitle = "Reset imediato do Supremo de um aliado para soltar dois ultimates devastadores em sequência.",
      spellName = "Golpe de Fagulha",
      spellDescription = "Garante reposicionamento para alcançar o aliado que precisa do reset de habilidade.",
      items = listOf(
        EquipmentItem("dl_1", "Emblema do Guardião", "Suporte", "Aura de Vida", "+800 HP • Proteção", "shield"),
        EquipmentItem("dl_2", "Botas de Resistência", "Movimento", "Tenacidade", "+110 Def. Mágica", "snowshoeing"),
        EquipmentItem("dl_3", "Cálice Sagrado", "Mágica", "Sustentação", "+160 AP • Regen Mana", "gavel"),
        EquipmentItem("dl_4", "Clamor de Gelo", "Defesa", "Armadura", "+360 Defesa", "shield"),
        EquipmentItem("dl_5", "Olho da Fênix", "Defesa", "Defesa Mágica", "+240 Def. Mágica", "shield"),
        EquipmentItem("dl_6", "Lâmina Sábia", "Defesa", "Ressurreição", "+100 Armadura", "shield")
      ),
      arcanas = ChampionFactory.buildTankArcanas(),
      arcanaStatsSummary = ChampionFactory.standardTankSummary,
      skills = listOf(
        SkillInfo("Forma de Sereia", "Passiva", "NADO NA ÁGUA", "Na água ou no rio, transforma-se em sereia recuperando vida e mana continuamente.", "radar"),
        SkillInfo("Canção Eclética (H1)", "Habilidade 1", "ONDAS E ATORDOAMENTO", "Emite 4 ondas sonoras; se estiver na água, empurra e atordoa todos os alvos.", "waves"),
        SkillInfo("Salto Marinho (H2)", "Habilidade 2", "CRIAÇÃO DE ÁGUA", "Salta criando uma poça d'água sob seus pés que cura aliados e ativa a forma de sereia.", "waves", isMaxPriority = true),
        SkillInfo("Canto Celestial do Renascimento", "Supremo", "RESET DE ULTIMATE", "Zera instantaneamente o tempo de recarga da habilidade mais longa de um aliado selecionado.", "thunderstorm")
      ),
      combos = listOf(
        ComboStep(1, "Criar Poça d'Água com H2", "Pule com a H2 para gerar o lago de água sob o grupo de aliados."),
        ComboStep(2, "Reset de Supremo no Aliado", "Use o Supremo em heróis de dano absurdo como Lu Bu, Kaizer ou Angela para que usem dois ultimates seguidos.")
      ),
      proTips = listOf(
        ProTip("Dois Ultimates do Lu Bu", "Com Dolia, Lu Bu pode usar o Supremo duas vezes na mesma luta, tornando o time imbatível.")
      ),
      shortTip = "Use o Supremo em Lu Bu, Kaizer ou Angela assim que eles gastarem o ultimate: o tempo de recarga deles zera instantaneamente para um segundo ultimate devastador."
    ),

    // 3. CAI YAN
    ChampionFactory.makeHero(
      id = "caiyan",
      name = "Cai Yan",
      title = "A Melodia da Cura",
      heroClass = "Suporte",
      lane = Lane.SUPORTE,
      tier = HeroTier.S,
      gtimgId = 184,
      difficultyStars = 1,
      winRate = "53.0%",
      winRateChange = "+0.7%",
      pickRate = "23.8%",
      banRate = "24.5%",
      coreItemSummary = "Cálice Sagrado + Livro dos Santos",
      buildSubtitle = "Cura massiva de toda a equipe e atordoamento sonoro ricocheteante entre inimigos agrupados.",
      spellName = "Golpe de Fagulha",
      spellDescription = "Permite acompanhar o atirador e fugir de focos inimigos.",
      items = listOf(
        EquipmentItem("cy_1", "Emblema do Guardião", "Suporte", "Proteção", "+800 Vida", "shield"),
        EquipmentItem("cy_2", "Botas de Resistência", "Movimento", "Tenacidade", "+110 Def. Mágica", "snowshoeing"),
        EquipmentItem("cy_3", "Cálice Sagrado", "Mágica", "Regeneração", "+160 AP • Mana Ilimitada", "gavel"),
        EquipmentItem("cy_4", "Cetro do Eco", "Mágica", "Poder Mágico", "+240 AP", "gavel"),
        EquipmentItem("cy_5", "Clamor de Gelo", "Defesa", "Armadura", "+360 Defesa", "shield"),
        EquipmentItem("cy_6", "Livro dos Santos", "Mágica", "Cura Máxima", "+400 AP • Cura Elevada", "gavel")
      ),
      arcanas = ChampionFactory.buildTankArcanas(),
      arcanaStatsSummary = ChampionFactory.standardTankSummary,
      skills = listOf(
        SkillInfo("Acorde Curativo", "Passiva", "CURA E VELOCIDADE", "Ao sofrer dano, ganha velocidade de movimento e regenera vida por 2 segundos.", "radar"),
        SkillInfo("Melodia Restauradora (H1)", "Habilidade 1", "CURA EM ÁREA", "Cria aura que cura a si mesma e todos os aliados ao redor continuamente.", "waves", isMaxPriority = true),
        SkillInfo("Nota Ricocheteante (H2)", "Habilidade 2", "ATORDOAMENTO RICOCHETE", "Lança nota musical que ricocheteia até 6 vezes atordoando cada alvo a cada toque.", "autorenew"),
        SkillInfo("Concerto da Vida", "Supremo", "CURA MASSIVA E ARMADURA", "Cura o aliado de menor vida a cada 0.5s e concede centenas de pontos de defesa física e mágica.", "thunderstorm")
      ),
      combos = listOf(
        ComboStep(1, "Ricochete H2 na Equipe Inimiga", "Dispare a H2 quando dois ou mais heróis inimigos estiverem juntos para mantê-los atordoados."),
        ComboStep(2, "H1 + Supremo Coletivo", "Ative a H1 e o Supremo juntos durante o confronto para tornar o atirador quase imortal.")
      ),
      proTips = listOf(
        ProTip("Mais Poder Mágico = Mais Cura", "Construa itens com Poder Mágico alto, pois a taxa de escala de cura de Cai Yan é muito elevada.")
      ),
      shortTip = "A nota da H2 ricocheteia entre múltiplos alvos interrompendo habilidades canalizadas enquanto o Supremo cura centenas de vida e dá armadura ao mesmo tempo."
    ),

    // 4. SUN BIN
    ChampionFactory.makeHero(
      id = "sunbin",
      name = "Sun Bin",
      title = "O Viajante do Tempo",
      heroClass = "Suporte",
      lane = Lane.SUPORTE,
      tier = HeroTier.S,
      gtimgId = 118,
      difficultyStars = 2,
      winRate = "53.4%",
      winRateChange = "+1.1%",
      pickRate = "19.3%",
      banRate = "18.2%",
      coreItemSummary = "Manto Glacial + Clamor de Gelo",
      buildSubtitle = "Aceleração coletiva de 50%, reversão no tempo de 40% do dano sofrido e silêncio em área no Supremo.",
      spellName = "Golpe de Fagulha",
      spellDescription = "Permite se posicionar com precisão para acertar a bomba do Supremo no meio dos inimigos.",
      items = listOf(
        EquipmentItem("sb_1", "Emblema do Guardião", "Suporte", "Proteção", "+800 Vida", "shield"),
        EquipmentItem("sb_2", "Botas de Agilidade", "Movimento", "Velocidade", "+80 Vel. Fora de Luta", "snowshoeing"),
        EquipmentItem("sb_3", "Cálice Sagrado", "Mágica", "Sustentação", "+160 AP • Mana", "gavel"),
        EquipmentItem("sb_4", "Clamor de Gelo", "Defesa", "Armadura", "+360 Defesa", "shield"),
        EquipmentItem("sb_5", "Olho da Fênix", "Defesa", "Defesa Mágica", "+240 Def. Mágica", "shield"),
        EquipmentItem("sb_6", "Lâmina Sábia", "Defesa", "Ressurreição", "+100 Armadura", "shield")
      ),
      arcanas = ChampionFactory.buildTankArcanas(),
      arcanaStatsSummary = ChampionFactory.standardTankSummary,
      skills = listOf(
        SkillInfo("Engrenagens Temporais", "Passiva", "ACELERAÇÃO PRÓPRIA", "Usar qualquer habilidade aumenta a velocidade de movimento de Sun Bin em 20%.", "radar"),
        SkillInfo("Bomba Temporal (H1)", "Habilidade 1", "BOMBA RELÓGIO", "Fixa bomba em um herói que explode após 3s causando dano baseado na vida perdida.", "waves"),
        SkillInfo("Fluxo Temporal (H2)", "Habilidade 2", "ACELERAÇÃO E REVERSÃO DE DANO", "Acelera todos os aliados em até 50%; ao final, devolve 40% de todo o dano sofrido nesse período.", "waves", isMaxPriority = true),
        SkillInfo("Campo Temporal Estático", "Supremo", "SILÊNCIO EM ÁREA", "Dispara dispositivo que abre campo gigante: silencia inimigos por 1s e desacelera em 90%.", "thunderstorm")
      ),
      combos = listOf(
        ComboStep(1, "Acelerar com H2", "Ative a H2 quando o time inimigo iniciar com suas principais habilidades para devolver o dano sofrido."),
        ComboStep(2, "Supremo de Silêncio Coletivo", "Jogue o Supremo no meio da luta de equipes para silenciar todos os conjuradores inimigos.")
      ),
      proTips = listOf(
        ProTip("Reversão Não é Cura", "A reversão de dano da H2 não é afetada por itens corta-cura inimigos.")
      ),
      shortTip = "Aperte a H2 no exato momento em que os inimigos soltarem o burst de habilidades: seu time recuperará 40% de todo o dano tomado logo em seguida."
    ),

    // 5. DONGHUANG TAIYI
    ChampionFactory.makeHero(
      id = "donghuang",
      name = "Donghuang Taiyi",
      title = "O Dragão do Caos",
      heroClass = "Tank / Suporte",
      lane = Lane.SUPORTE,
      tier = HeroTier.S,
      gtimgId = 187,
      difficultyStars = 2,
      winRate = "52.7%",
      winRateChange = "+0.5%",
      pickRate = "16.4%",
      banRate = "26.3%",
      isBanPriority = true,
      coreItemSummary = "Capa Flamejante + Clamor de Gelo",
      buildSubtitle = "Supressão absoluta que prende o alvo vinculando a vida de ambos: se Donghuang toma dano, o alvo também morre.",
      spellName = "Golpe de Fagulha",
      spellDescription = "Essencial para alcançar o atirador inimigo e aplicar o vínculo supremo instantâneo.",
      items = listOf(
        EquipmentItem("dh_1", "Emblema do Guardião", "Suporte", "Vida", "+800 Vida", "shield"),
        EquipmentItem("dh_2", "Botas de Resistência", "Movimento", "Tenacidade", "+110 Def. Mágica", "snowshoeing"),
        EquipmentItem("dh_3", "Manto Ardente", "Defesa", "Queima", "+1000 Vida", "shield"),
        EquipmentItem("dh_4", "Clamor de Gelo", "Defesa", "Armadura", "+360 Defesa", "shield"),
        EquipmentItem("dh_5", "Olho da Fênix", "Defesa", "Cura Ampliada", "+240 Def. Mágica • +1200 HP", "shield"),
        EquipmentItem("dh_6", "Armadura de Espinhos", "Defesa", "Reflexo de Dano", "+400 Armadura", "shield")
      ),
      arcanas = ChampionFactory.buildTankArcanas(),
      arcanaStatsSummary = ChampionFactory.standardTankSummary,
      skills = listOf(
        SkillInfo("Orbes Sombrios", "Passiva", "CURA AO GIRAR", "Três orbes giram ao seu redor curando vida contínua sempre que encostarem em heróis inimigos.", "radar"),
        SkillInfo("Invocar Orbes (H1)", "Habilidade 1", "TRÊS ORBES", "Invoca até 3 esferas gravitacionais de energia cósmica.", "waves"),
        SkillInfo("Queda dos Dragões (H2)", "Habilidade 2", "TRÊS IMPACTOS", "Lança os 3 orbes causando lentidão e atordoamento no terceiro impacto.", "waves"),
        SkillInfo("Supressão do Destino", "Supremo", "SUPRESSÃO ABSOLUTA", "Tranca no herói inimigo suprimindo-o: nenhum efeito remove isso. Todo dano sofrido por Donghuang é espelhado no alvo.", "thunderstorm", isMaxPriority = true)
      ),
      combos = listOf(
        ComboStep(1, "Flash + Supremo de Trancamento", "Use Golpe de Fagulha para colar no atirador inimigo e ative o Supremo para prendê-lo debaixo da torre."),
        ComboStep(2, "Orbes Girando no Meio", "Depois da supressão, continue colado nos inimigos girando as 3 esferas para cura contínua.")
      ),
      proTips = listOf(
        ProTip("Torre Mata o Alvo", "Prenda o alvo debaixo da torre com o Supremo: os tiros da torre atingindo você derreterão a vida do herói preso.")
      ),
      shortTip = "A supressão do Supremo não pode ser limpa por nenhuma Purificação: use Flash + Supremo direto no atirador inimigo para anulá-lo da luta."
    )
  )
}
