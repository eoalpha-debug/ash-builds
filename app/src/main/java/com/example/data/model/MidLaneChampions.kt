package com.example.data.model

import com.example.R

object MidLaneChampions {

  val list: List<Champion> = listOf(
    // 1. ANGELA
    ChampionFactory.makeHero(
      id = "angela",
      name = "Angela",
      title = "A Feiticeira do Fogo",
      heroClass = "Maga",
      lane = Lane.MEIO,
      tier = HeroTier.SS,
      gtimgId = 142,
      localDrawableRes = R.drawable.img_hero_angela,
      difficultyStars = 2,
      winRate = "53.2%",
      winRateChange = "+1.0%",
      pickRate = "28.6%",
      banRate = "33.8%",
      isBanPriority = true,
      coreItemSummary = "Scepter of Echo + Staff of Sorcery",
      buildSubtitle = "Atordoamento de longo alcance com o vórtice de fogo e raio térmico contínuo imune a controle.",
      spellName = "Golpe de Fagulha",
      spellDescription = "Permite reposicionar Angela caso inimigos tentem flanquear durante o raio supremo.",
      items = listOf(
        EquipmentItem("ang_1", "Botas Arcanas", "Movimento", "Regeneração de Mana", "+25 Regen Mana", "snowshoeing"),
        EquipmentItem("ang_2", "Cetro do Eco", "Mágica", "Explosão Mágica", "+240 Poder Mágico • Explosão", "gavel"),
        EquipmentItem("ang_3", "Máscara da Agonia", "Mágica", "Dano Contínuo", "+120 Poder Mágico • Queimadura", "gavel"),
        EquipmentItem("ang_4", "Barreira Lunar", "Mágica", "Imunidade temporária", "+140 AP • Invulnerabilidade 1.5s", "shield"),
        EquipmentItem("ang_5", "Cajado do Vácuo", "Mágica", "Perfuração Mágica", "+45% Pen. Mágica", "gavel"),
        EquipmentItem("ang_6", "Livro dos Santos", "Mágica", "Poder Máximo", "+400 Poder Mágico • +1400 HP", "gavel")
      ),
      arcanas = ChampionFactory.buildMagicalPenArcanas(),
      arcanaStatsSummary = ChampionFactory.standardMagicalPenSummary,
      skills = listOf(
        SkillInfo("Feitiçaria Incendiária", "Passiva", "QUEIMADURA ACUMULATIVA", "Suas habilidades queimam o alvo, acumulando até 10 vezes para dano mágico titânico.", "radar"),
        SkillInfo("Projéteis de Fogo (H1)", "Habilidade 1", "CINCO ESFERAS DE FOGO", "Invoca 5 esferas flamejantes que convergem no ponto alvo com alto dano de rajada.", "waves"),
        SkillInfo("Vórtice do Caos (H2)", "Habilidade 2", "ATORDOAMENTO E LENTIDÃO", "Lança vórtice flamejante que atordoa o primeiro herói atingido e desacelera alvos.", "waves", isMaxPriority = true),
        SkillInfo("Raio Ardente Destruidor", "Supremo", "RAIO CONTÍNUO E ESCUDO", "Canaliza um feixe colossal de fogo ganhando escudo; enquanto o escudo durar, Angela é imune a atordoamentos.", "thunderstorm")
      ),
      combos = listOf(
        ComboStep(1, "Emboscada no Arbusto com H2", "Fique oculta na moita e acerte a H2 para atordoar o inimigo de surpresa."),
        ComboStep(2, "H1 + Supremo Imediato", "Dispare as 5 esferas da H1 e imediatamente abra o Raio do Supremo para evaporar a vida inteira do alvo.")
      ),
      proTips = listOf(
        ProTip("Imunidade do Raio", "O escudo do Supremo protege Angela contra qualquer puxão ou atordoamento inimigo.")
      ),
      shortTip = "Fique de tocaia no arbusto do rio e acerte a H2 primeiro: o atordoamento garante que as 5 bolas de fogo e o laser do Supremo acertem 100% dos ticks."
    ),

    // 2. DIAOCHAN
    ChampionFactory.makeHero(
      id = "diaochan",
      name = "Diaochan",
      title = "A Lótus Encantada",
      heroClass = "Maga / Assassina",
      lane = Lane.MEIO,
      tier = HeroTier.S,
      gtimgId = 141,
      localDrawableRes = R.drawable.img_hero_diaochan,
      difficultyStars = 4,
      winRate = "53.5%",
      winRateChange = "+1.2%",
      pickRate = "21.3%",
      banRate = "27.4%",
      isBanPriority = true,
      coreItemSummary = "Cetro do Eco + Livro da Vida",
      buildSubtitle = "Dano real explosivo, lentidão de 90% e invulnerabilidade momentânea no teleporte da Habilidade 2.",
      spellName = "Purificação",
      spellDescription = "Essencial para não ser interrompida por atordoamentos dentro do círculo do Supremo.",
      items = listOf(
        EquipmentItem("dc_1", "Botas de Resistência", "Movimento", "Tenacidade", "Tenacidade +35%", "snowshoeing"),
        EquipmentItem("dc_2", "Cetro do Eco", "Mágica", "Dano e CDR", "+240 AP", "gavel"),
        EquipmentItem("dc_3", "Cálice Sagrado", "Mágica", "Sustentação e Mana", "+160 AP • Regen Vida e Mana", "gavel"),
        EquipmentItem("dc_4", "Clamor de Gelo", "Defesa", "Vida e Armadura", "+360 Defesa • 10% CDR", "shield"),
        EquipmentItem("dc_5", "Barreira Lunar", "Mágica", "Invulnerabilidade", "+140 AP • Invulnerável", "shield"),
        EquipmentItem("dc_6", "Livro dos Santos", "Mágica", "Poder Máximo", "+400 Poder Mágico", "gavel")
      ),
      arcanas = ChampionFactory.buildMagicalPenArcanas(),
      arcanaStatsSummary = ChampionFactory.standardMagicalPenSummary,
      skills = listOf(
        SkillInfo("Lótus Encantadora", "Passiva", "DANO REAL E LENTIDÃO 90%", "Quatro acertos detonam a marca de lótus causando dano real explosivo e 90% de lentidão.", "radar"),
        SkillInfo("Lótus Retornante (H1)", "Habilidade 1", "DISCO DE IDA E VOLTA", "Arremessa um orbe de lótus que vai e volta aplicando 2 marcas.", "waves"),
        SkillInfo("Passo de Pétalas (H2)", "Habilidade 2", "TELEPORTE E ESQUIVA", "Teleporta e dispara 3 pétalas. No momento do teleporte, Diaochan é invulnerável.", "waves", isMaxPriority = true),
        SkillInfo("Dança das Lótus Deslumbrantes", "Supremo", "CÍRCULO MÁGICO", "Abre círculo no chão que acelera dramaticamente a recarga da H1 e H2 enquanto estiver dentro.", "thunderstorm")
      ),
      combos = listOf(
        ComboStep(1, "Abrir o Supremo", "Abra o Supremo no meio da luta e dance com H2 constante ao redor dos alvos."),
        ComboStep(2, "Ciclo Contínuo (H2 + H1)", "Use H2 para desviar de habilidades e H1 para estourar a passiva de dano real a cada segundo.")
      ),
      proTips = listOf(
        ProTip("Frames de Invulnerabilidade", "A H2 torna Diaochan invulnerável por uma fração de segundo, permitindo desviar até de tiros de torre ou ultimates.")
      ),
      shortTip = "Dentro da área do seu Supremo, os tempos de recarga da H1 e H2 caem para quase 1 segundo: dance continuamente com a H2 para ser inalcançável."
    ),

    // 3. LADY ZHEN
    ChampionFactory.makeHero(
      id = "lady_zhen",
      name = "Lady Zhen",
      title = "A Ninfa do Gelo",
      heroClass = "Maga",
      lane = Lane.MEIO,
      tier = HeroTier.S,
      gtimgId = 127,
      difficultyStars = 2,
      winRate = "52.6%",
      winRateChange = "+0.8%",
      pickRate = "23.4%",
      banRate = "16.1%",
      coreItemSummary = "Cetro do Eco + Cajado do Vácuo",
      buildSubtitle = "Congelamento em massa de equipes inteiras ricocheteando água e invocando lago congelante.",
      spellName = "Golpe de Fagulha",
      spellDescription = "Permite escapar de emboscadas e reposicionar na retaguarda das lutas.",
      items = listOf(
        EquipmentItem("lz_1", "Botas Arcanas", "Movimento", "Mana", "+25 Regen Mana", "snowshoeing"),
        EquipmentItem("lz_2", "Cetro do Eco", "Mágica", "Dano em área", "+240 AP", "gavel"),
        EquipmentItem("lz_3", "Máscara da Agonia", "Mágica", "Dano contínuo", "+120 AP", "gavel"),
        EquipmentItem("lz_4", "Barreira Lunar", "Mágica", "Congelamento próprio", "+140 AP", "shield"),
        EquipmentItem("lz_5", "Cajado do Vácuo", "Mágica", "Penetração", "+45% Pen. Mágica", "gavel"),
        EquipmentItem("lz_6", "Livro dos Santos", "Mágica", "Poder Máximo", "+400 AP", "gavel")
      ),
      arcanas = ChampionFactory.buildMagicalPenArcanas(),
      arcanaStatsSummary = ChampionFactory.standardMagicalPenSummary,
      skills = listOf(
        SkillInfo("Alma Congelada", "Passiva", "CONGELAMENTO", "Três acúmulos de habilidades congelam o herói inimigo por 1.2s e causam dano extra.", "radar"),
        SkillInfo("Geiser de Gelo (H1)", "Habilidade 1", "ARREMESSO", "Faz brotar um pilar de água que arremessa inimigos após breve retardo.", "waves"),
        SkillInfo("Água Ricocheteante (H2)", "Habilidade 2", "RICOCHETE", "Lança esfera de água que ricocheteia até 6 vezes entre inimigos.", "waves", isMaxPriority = true),
        SkillInfo("Enchente Destruidora", "Supremo", "LAGO DE GELO", "Dispara torrente de água que se transforma em um lago congelante desacelerando e congelando alvos.", "thunderstorm")
      ),
      combos = listOf(
        ComboStep(1, "Ricochete H2 na Tropa", "Lance H2 nas tropas quando heróis inimigos estiverem perto para a água ricochetear neles."),
        ComboStep(2, "Supremo + H1 Congelante", "Abra o lago do Supremo para congelar múltiplos inimigos e finalize com a H1 sob os alvos.")
      ),
      proTips = listOf(
        ProTip("Andar Conjura", "Lady Zhen pode conjurar todas as suas habilidades sem precisar interromper seu movimento.")
      ),
      shortTip = "Use o ricochete da H2 nas tropas quando os adversários estiverem próximos para aplicar marcas seguras sem se expor a riscos."
    ),

    // 4. MAI SHIRANUI
    ChampionFactory.makeHero(
      id = "mai_shiranui",
      name = "Mai Shiranui",
      title = "A Kunoichi Flamejante",
      heroClass = "Maga / Assassina",
      lane = Lane.MEIO,
      tier = HeroTier.SS,
      gtimgId = 157,
      difficultyStars = 5,
      winRate = "53.9%",
      winRateChange = "+1.4%",
      pickRate = "15.7%",
      banRate = "39.2%",
      isBanPriority = true,
      coreItemSummary = "Cetro do Eco + Livro dos Santos",
      buildSubtitle = "Burst mágico assassino capaz de eliminar o atirador inimigo com 1 leque e empurrão do Supremo.",
      spellName = "Golpe de Fagulha",
      spellDescription = "Combina com o leque para garantir que ninguém escape do raio de ação das chamas.",
      items = listOf(
        EquipmentItem("ms_1", "Botas de Resistência", "Movimento", "Tenacidade", "+110 Def. Mágica", "snowshoeing"),
        EquipmentItem("ms_2", "Cetro do Eco", "Mágica", "Burst", "+240 AP", "gavel"),
        EquipmentItem("ms_3", "Cajado do Vácuo", "Mágica", "Penetração", "+45% Pen. Mágica", "gavel"),
        EquipmentItem("ms_4", "Barreira Lunar", "Mágica", "Invulnerabilidade", "+140 AP", "shield"),
        EquipmentItem("ms_5", "Livro dos Santos", "Mágica", "Poder Máximo", "+400 AP", "gavel"),
        EquipmentItem("ms_6", "Lâmina Sábia", "Defesa", "Ressurreição", "+100 Armadura", "shield")
      ),
      arcanas = ChampionFactory.buildMagicalPenArcanas(),
      arcanaStatsSummary = ChampionFactory.standardMagicalPenSummary,
      skills = listOf(
        SkillInfo("Rolamento Ninja", "Passiva", "MOBILIDADE LIVRE", "Após usar qualquer habilidade, rola na direção escolhida ganhando velocidade.", "radar"),
        SkillInfo("Chute Voador (H1)", "Habilidade 1", "SALTO E ARREMESSO", "Chuta para cima arremessando todos os inimigos atingidos.", "waves"),
        SkillInfo("Leque de Chamas (H2)", "Habilidade 2", "POKE E PERFURAÇÃO", "Atira leque que reduz resistência mágica do alvo em até 200 e aplica 90% de lentidão.", "waves", isMaxPriority = true),
        SkillInfo("Investida Mortal das Chamas", "Supremo", "BURST E EMPURRÃO", "Avança envolvida em chamas empurrando e reduzindo o ataque físico dos inimigos em 20%.", "thunderstorm")
      ),
      combos = listOf(
        ComboStep(1, "Leque H2 para Desacelerar", "Acerte o leque da H2 para diminuir a defesa mágica do inimigo pela metade."),
        ComboStep(2, "H1 Chute + Supremo", "Avance com a H1 arremessando o alvo e emende o Supremo empurrando para seu time.")
      ),
      proTips = listOf(
        ProTip("Poke Constante", "Use a H2 de longe a cada 2 segundos: se acertar, recupera energia permitindo lançar leques infinitos.")
      ),
      shortTip = "Acerte a H2 primeiro para destruir a defesa mágica do alvo antes de entrar com o combo devastador de H1 + Supremo."
    ),

    // 5. KONGMING
    ChampionFactory.makeHero(
      id = "kongming",
      name = "Kongming",
      title = "O Estrategista Celestial",
      heroClass = "Mago / Assassino",
      lane = Lane.MEIO,
      tier = HeroTier.S,
      gtimgId = 190,
      difficultyStars = 3,
      winRate = "52.4%",
      winRateChange = "+0.6%",
      pickRate = "18.9%",
      banRate = "17.5%",
      coreItemSummary = "Cetro do Eco + Livro dos Santos",
      buildSubtitle = "Reset de Supremo ao abater alvos com o disparo celestial e orbes passivos teleguiados.",
      spellName = "Golpe de Fagulha",
      spellDescription = "Permite reposicionar a linha de visão do Supremo para não ser bloqueado por tanks.",
      items = listOf(
        EquipmentItem("km_1", "Botas Arcanas", "Movimento", "Mana", "+25 Regen Mana", "snowshoeing"),
        EquipmentItem("km_2", "Cetro do Eco", "Mágica", "Dano", "+240 AP", "gavel"),
        EquipmentItem("km_3", "Barreira Lunar", "Mágica", "Invulnerabilidade", "+140 AP", "shield"),
        EquipmentItem("km_4", "Cajado do Vácuo", "Mágica", "Penetração", "+45% Pen. Mágica", "gavel"),
        EquipmentItem("km_5", "Máscara da Agonia", "Mágica", "Dano contínuo", "+120 AP", "gavel"),
        EquipmentItem("km_6", "Livro dos Santos", "Mágica", "Poder Máximo", "+400 AP", "gavel")
      ),
      arcanas = ChampionFactory.buildMagicalPenArcanas(),
      arcanaStatsSummary = ChampionFactory.standardMagicalPenSummary,
      skills = listOf(
        SkillInfo("Mente Brilhante", "Passiva", "ORBES ORBITAIS", "Acertar habilidades gera acúmulos; ao atingir 5, 5 orbes orbitam Kongming atacando alvos automaticamente.", "radar"),
        SkillInfo("Três Orbes de Energia (H1)", "Habilidade 1", "DISPARO TRIPLO", "Dispara 3 orbes de energia em cone; quanto mais perto estiver do alvo, mais orbes acertam.", "waves", isMaxPriority = true),
        SkillInfo("Deslocamento Espacial (H2)", "Habilidade 2", "TRÊS TELEPORTES", "Armazena até 3 cargas de teleporte rápido desacelerando alvos no ponto de partida e chegada.", "autorenew"),
        SkillInfo("Bombardeio Vital Celestial", "Supremo", "EXECUÇÃO E RESET", "Trava em um inimigo e dispara canhão de luz; se o alvo morrer, o tempo de recarga reduz em 80%.", "thunderstorm")
      ),
      combos = listOf(
        ComboStep(1, "Carregar Passiva com H1 e H2", "Acerte a H1 de perto com os 3 orbes e use H2 para ativar a passiva dos 5 orbes."),
        ComboStep(2, "Execução com o Supremo", "Trave o Supremo em inimigos com pouca vida para garantir o abate e resetar a recarga.")
      ),
      proTips = listOf(
        ProTip("Flash no Supremo", "Se um tank inimigo tentar ficar na frente do seu tiro de Supremo, use o Golpe de Fagulha lateralmente para alterar a trajetória.")
      ),
      shortTip = "Finalize inimigos de pouca vida com o Supremo para reduzir 80% do tempo de recarga e ativar imediatamente os 5 orbes da passiva."
    )
  )
}
