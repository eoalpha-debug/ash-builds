package com.example.data.model

import com.example.R

object FarmLaneChampions {

  val list: List<Champion> = listOf(
    // 1. ALESSIO
    ChampionFactory.makeHero(
      id = "alessio",
      name = "Alessio",
      title = "O Falcão Flamejante",
      heroClass = "Atirador",
      lane = Lane.ADC,
      tier = HeroTier.SS,
      gtimgId = 515,
      localDrawableRes = R.drawable.img_hero_alessio,
      difficultyStars = 3,
      winRate = "53.6%",
      winRateChange = "+1.2%",
      pickRate = "21.8%",
      banRate = "34.5%",
      isBanPriority = true,
      coreItemSummary = "Lâmina Sem Fim + Quebra-Estrela",
      buildSubtitle = "Voo aéreo prolongado ignorando muros e bombardeio de dano percentual de vida máxima.",
      spellName = "Golpe de Fagulha",
      spellDescription = "Permite se afastar de assassinos no solo antes de subir aos céus com a Habilidade 2.",
      items = listOf(
        EquipmentItem("ale_1", "Botas de Agilidade", "Movimento", "Velocidade de Ataque", "+25% Vel. Ataque", "snowshoeing"),
        EquipmentItem("ale_2", "Lâmina Sem Fim", "Ataque", "Dano e Crítico", "+110 Dano • +20% Crítico", "gavel"),
        EquipmentItem("ale_3", "Lâmina da Sombra", "Ataque", "Crítico e Mobilidade", "+30% Vel. Ataque • +25% Crítico", "gavel"),
        EquipmentItem("ale_4", "Arco da Aurora", "Ataque", "Penetração a Distância", "+50 Dano • +40% Pen. Física", "gavel"),
        EquipmentItem("ale_5", "Sangrenta Carnificina", "Ataque", "Roubo de Vida", "+100 Dano • +25% Roubo de Vida", "gavel"),
        EquipmentItem("ale_6", "Lâmina Sábia", "Defesa", "Ressurreição", "+100 Armadura", "shield")
      ),
      arcanas = ChampionFactory.buildCritArcanas(),
      arcanaStatsSummary = ChampionFactory.standardCritSummary,
      skills = listOf(
        SkillInfo("Canhão Explosivo", "Passiva", "DANO EM ÁREA", "Seus ataques básicos explodem causando dano em área baseado na vida máxima do alvo.", "radar"),
        SkillInfo("Munição Flamejante (H1)", "Habilidade 1", "TRÊS TIROS FORTALECIDOS", "Carrega 3 tiros especiais de alcance e raio de explosão aumentados.", "waves"),
        SkillInfo("Voo Fumaça (H2)", "Habilidade 2", "SALTO AÉREO E INVISIBILIDADE", "Lança bomba de fumaça e salta para o ar: ganha invisibilidade na fumaça e ataca voando.", "waves", isMaxPriority = true),
        SkillInfo("Bombardeio Terminal", "Supremo", "EXECUÇÃO DE MÍSSEIS", "Trava no herói inimigo e dispara saraivada de 5 mísseis com alto dano de execução.", "thunderstorm")
      ),
      combos = listOf(
        ComboStep(1, "Voo e Fumaça com H2", "Pule com a H2 para voar por cima de paredes e escapar de assassinos."),
        ComboStep(2, "H1 no Ar + Supremo", "Dispare os 3 tiros fortalecidos da H1 do ar e finalize o alvo fujão com os mísseis do Supremo.")
      ),
      proTips = listOf(
        ProTip("Ataque no Ar Prolonga o Voo", "Cada ataque básico desferido enquanto estiver no ar reinicia levemente o tempo de queda de Alessio.")
      ),
      shortTip = "Ative a H1 antes de pular com a H2: atacar enquanto voa prolonga o tempo no ar, permitindo fuzilar os inimigos sem ser alcançado."
    ),

    // 2. ARLI
    ChampionFactory.makeHero(
      id = "arli",
      name = "Arli",
      title = "A Dançarina das Folhas",
      heroClass = "Atiradora",
      lane = Lane.ADC,
      tier = HeroTier.SS,
      gtimgId = 199,
      localDrawableRes = R.drawable.img_hero_arli,
      difficultyStars = 5,
      winRate = "54.0%",
      winRateChange = "+1.5%",
      pickRate = "14.6%",
      banRate = "42.0%",
      isBanPriority = true,
      coreItemSummary = "Lâmina Sem Fim + Arco da Aurora",
      buildSubtitle = "Teleportes triplos com o guarda-chuva de bordo para dançar ao redor de qualquer inimigo.",
      spellName = "Purificação",
      spellDescription = "Garante que Arli não seja presa por controles e consiga retornar ao guarda-chuva.",
      items = listOf(
        EquipmentItem("arl_1", "Botas de Agilidade", "Movimento", "Velocidade", "+25% Vel. Ataque", "snowshoeing"),
        EquipmentItem("arl_2", "Lâmina Sem Fim", "Ataque", "Dano e Crítico", "+110 Dano", "gavel"),
        EquipmentItem("arl_3", "Lâmina da Sombra", "Ataque", "Velocidade e Crítico", "+25% Crítico", "gavel"),
        EquipmentItem("arl_4", "Arco da Aurora", "Ataque", "Penetração", "+40% Pen. Física", "gavel"),
        EquipmentItem("arl_5", "Sangrenta Carnificina", "Ataque", "Roubo de Vida", "+25% Roubo de Vida", "gavel"),
        EquipmentItem("arl_6", "Lâmina Sábia", "Defesa", "Ressurreição", "+100 Armadura", "shield")
      ),
      arcanas = ChampionFactory.buildCritArcanas(),
      arcanaStatsSummary = ChampionFactory.standardCritSummary,
      skills = listOf(
        SkillInfo("Folhas de Outono", "Passiva", "EXPLOSÃO DE MARCAS", "Ataques acumulam 4 marcas de folhas; ao estourar, causam dano mágico em área e reduzem recargas.", "radar"),
        SkillInfo("Passo Gracioso (H1)", "Habilidade 1", "AVANÇO E DEIXA GUARDA-CHUVA", "Avança deixando o guarda-chuva no lugar e pode reativar para teleportar de volta.", "waves", isMaxPriority = true),
        SkillInfo("Dança do Vento (H2)", "Habilidade 2", "BLOQUEIO DE PROJÉTEIS", "Gira o guarda-chuva ao redor bloqueando projéteis inimigos e disparando duas folhas.", "autorenew"),
        SkillInfo("Vórtice Solitário", "Supremo", "EMPURRÃO E ARREMESSO", "Arremessa o guarda-chuva para a frente empurrando todos os inimigos atingidos.", "thunderstorm")
      ),
      combos = listOf(
        ComboStep(1, "Deixar Guarda-Chuva com H1", "Avance com a H1, dê 3 tiros rápidos e aperte H1 de novo para voltar com segurança."),
        ComboStep(2, "Empurrão com o Supremo", "Se um assassino pular em você, use o Supremo para empurrá-lo para longe.")
      ),
      proTips = listOf(
        ProTip("Velocidade sem Guarda-Chuva", "Quando Arli está sem o guarda-chuva nas mãos, sua velocidade de movimento e ataque aumentam consideravelmente.")
      ),
      shortTip = "Quando estiver sem o guarda-chuva na mão você ganha bônus de velocidade: use a H2 para deletar projéteis de outros atiradores como Hou Yi."
    ),

    // 3. HOU YI
    ChampionFactory.makeHero(
      id = "houyi",
      name = "Hou Yi",
      title = "O Arqueiro Solar",
      heroClass = "Atirador",
      lane = Lane.ADC,
      tier = HeroTier.S,
      gtimgId = 169,
      localDrawableRes = R.drawable.img_hero_houyi,
      difficultyStars = 1,
      winRate = "52.3%",
      winRateChange = "+0.7%",
      pickRate = "31.2%",
      banRate = "9.4%",
      coreItemSummary = "Lâmina Relâmpago + Lâmina Sem Fim",
      buildSubtitle = "Flecha solar global que cruza o mapa inteiro e ataques básicos que disparam 3 flechas simultâneas.",
      spellName = "Golpe de Fagulha",
      spellDescription = "Garante reposicionamento de emergência caso um assassino flanqueie.",
      items = listOf(
        EquipmentItem("hy_1", "Botas de Agilidade", "Movimento", "Velocidade", "+25% Vel. Ataque", "snowshoeing"),
        EquipmentItem("hy_2", "Lâmina Relâmpago", "Ataque", "Raio em Cadeia", "+35% Vel. Atq • Choque Elétrico", "gavel"),
        EquipmentItem("hy_3", "Lâmina Sem Fim", "Ataque", "Crítico Pesado", "+110 Dano • +20% Crítico", "gavel"),
        EquipmentItem("hy_4", "Sangrenta Carnificina", "Ataque", "Sustentação", "+25% Roubo de Vida", "gavel"),
        EquipmentItem("hy_5", "Arco da Aurora", "Ataque", "Penetração", "+40% Pen. Física", "gavel"),
        EquipmentItem("hy_6", "Lâmina Sábia", "Defesa", "Ressurreição", "+100 Armadura", "shield")
      ),
      arcanas = ChampionFactory.buildCritArcanas(),
      arcanaStatsSummary = ChampionFactory.standardCritSummary,
      skills = listOf(
        SkillInfo("Graça do Sol", "Passiva", "FLECHAS TRIPLAS", "Cada ataque concede velocidade de ataque; com 3 cargas, cada tiro se divide em 3 flechas.", "radar"),
        SkillInfo("Flechas Radiantes (H1)", "Habilidade 1", "DISPARO MULTI-ALVO", "Fortalece os ataques para atingirem até 2 alvos adicionais simultaneamente.", "waves", isMaxPriority = true),
        SkillInfo("Chuva de Luz Solar (H2)", "Habilidade 2", "LENTIDÃO E REVELAÇÃO", "Chama raio de sol que desacelera os inimigos e concede visão da área.", "autorenew"),
        SkillInfo("Flecha Queima-Céus", "Supremo", "ATORDOAMENTO GLOBAL", "Dispara pássaro de fogo que viaja por todo o mapa atordoando por até 3.5s.", "thunderstorm")
      ),
      combos = listOf(
        ComboStep(1, "Flecha Global no Meio/Top", "Dispare o Supremo mirando na rota do topo ou meio para iniciar lutas para seus aliados."),
        ComboStep(2, "Ativar H1 e Fuzilar", "Com a passiva cheia, ative a H1 para espalhar 9 flechas por segundo nos oponentes.")
      ),
      proTips = listOf(
        ProTip("Visão no Arbusto", "Use a H2 dentro de arbustos escuros para verificar se há assassinos escondidos sem precisar entrar.")
      ),
      shortTip = "Dispare o pássaro do Supremo pelo mapa para atordoar alvos a longas distâncias e ative a H1 com 3 cargas da passiva para dano em área avassalador."
    ),

    // 4. MARCO POLO
    ChampionFactory.makeHero(
      id = "marco_polo",
      name = "Marco Polo",
      title = "O Pistoleiro Andarilho",
      heroClass = "Atirador",
      lane = Lane.ADC,
      tier = HeroTier.S,
      gtimgId = 132,
      localDrawableRes = R.drawable.img_hero_marco,
      difficultyStars = 3,
      winRate = "51.9%",
      winRateChange = "+0.5%",
      pickRate = "26.7%",
      banRate = "12.8%",
      coreItemSummary = "Lâmina Relâmpago + Quebra-Estrela",
      buildSubtitle = "Dano real contínuo com pistolas duplas, aviso sonoro de inimigos em arbustos e tornado de balas.",
      spellName = "Purificação",
      spellDescription = "Indispensável para não ter o Supremo cancelado ao pular no meio dos inimigos.",
      items = listOf(
        EquipmentItem("mp_1", "Botas de Agilidade", "Movimento", "Velocidade", "+25% Vel. Ataque", "snowshoeing"),
        EquipmentItem("mp_2", "Lâmina Relâmpago", "Ataque", "Velocidade de Ataque", "+35% Vel. Ataque", "gavel"),
        EquipmentItem("mp_3", "Cetro da Aurora", "Ataque", "Velocidade e Alcance", "+30% Vel. Ataque", "gavel"),
        EquipmentItem("mp_4", "Clamor de Gelo", "Defesa", "Lentidão e Defesa", "+360 Defesa", "shield"),
        EquipmentItem("mp_5", "Quebra-Estrelas", "Ataque", "Penetração", "+40% Pen. Física", "gavel"),
        EquipmentItem("mp_6", "Manto Ardente", "Defesa", "Vida e Armadura", "+1000 HP", "shield")
      ),
      arcanas = ChampionFactory.buildPhysicalPenArcanas(),
      arcanaStatsSummary = ChampionFactory.standardPhysicalPenSummary,
      skills = listOf(
        SkillInfo("Reação em Cadeia", "Passiva", "DANO REAL EM 10 TIROS", "Atingir 10 tiros no mesmo alvo faz todos os ataques seguintes causarem dano real.", "radar"),
        SkillInfo("Tiro Especial (H1)", "Habilidade 1", "RAJADA DE PISTOLA", "Dispara saraivada de tiros em linha reta enquanto se movimenta livremente.", "waves", isMaxPriority = true),
        SkillInfo("Passo Ilusório (H2)", "Habilidade 2", "TELEPORTE E RADAR", "Teleporta para a frente; ganha velocidade e um círculo indicador se houver inimigos por perto.", "autorenew"),
        SkillInfo("Febre da Febre", "Supremo", "TORNADO DE BALAS", "Avança e dispara uma tempestade circular de balas ao redor de Marco Polo.", "thunderstorm")
      ),
      combos = listOf(
        ComboStep(1, "Acumular Passiva com H1", "Acerte a rajada da H1 completa para aplicar as 10 marcas de dano real."),
        ComboStep(2, "Supremo com Purificação", "Pule com o Supremo no meio dos alvos marcados e ative Purificação para não ser interrompido.")
      ),
      proTips = listOf(
        ProTip("Detector de Emboscadas", "O círculo ao redor de Marco Polo na H2 avisa se há inimigos escondidos em moitas próximas.")
      ),
      shortTip = "A passiva da H2 gera um círculo ao seu redor revelando inimigos ocultos em arbustos: nunca caia em emboscadas com esse radar."
    ),

    // 5. LUBAN Nº 7
    ChampionFactory.makeHero(
      id = "luban",
      name = "Luban nº 7",
      title = "O Pequeno Autômato",
      heroClass = "Atirador",
      lane = Lane.ADC,
      tier = HeroTier.A,
      gtimgId = 112,
      difficultyStars = 1,
      winRate = "52.5%",
      winRateChange = "+0.8%",
      pickRate = "29.4%",
      banRate = "8.1%",
      coreItemSummary = "Lâmina Sem Fim + Arco da Aurora",
      buildSubtitle = "Metralhadora de dano percentual de vida máxima capaz de derreter o tank mais resistente do jogo em segundos.",
      spellName = "Golpe de Fagulha",
      spellDescription = "Permite escapar do foco inicial de lutadores inimigos.",
      items = listOf(
        EquipmentItem("lu_1", "Botas de Agilidade", "Movimento", "Velocidade", "+25% Vel. Ataque", "snowshoeing"),
        EquipmentItem("lu_2", "Lâmina Sem Fim", "Ataque", "Crítico", "+110 Dano • +20% Crítico", "gavel"),
        EquipmentItem("lu_3", "Lâmina da Sombra", "Ataque", "Velocidade e Crítico", "+25% Crítico", "gavel"),
        EquipmentItem("lu_4", "Sangrenta Carnificina", "Ataque", "Roubo de Vida", "+25% Roubo de Vida", "gavel"),
        EquipmentItem("lu_5", "Arco da Aurora", "Ataque", "Penetração", "+40% Pen. Física", "gavel"),
        EquipmentItem("lu_6", "Lâmina Sábia", "Defesa", "Ressurreição", "+100 Armadura", "shield")
      ),
      arcanas = ChampionFactory.buildCritArcanas(),
      arcanaStatsSummary = ChampionFactory.standardCritSummary,
      skills = listOf(
        SkillInfo("Fogo Contínuo", "Passiva", "DANO PERCENTUAL", "Após 4 ataques ou usar uma habilidade, o próximo ataque é uma rajada que causa 6% da vida máxima por tiro.", "radar"),
        SkillInfo("Granada Feroz (H1)", "Habilidade 1", "GRANADA E LENTIDÃO", "Lança granada que atordoa e desacelera inimigos no ponto de impacto.", "waves"),
        SkillInfo("Foguete Rompe-Tubarão (H2)", "Habilidade 2", "FOGUETE GLOBAL E EMPURRÃO", "Dispara foguete que cruza todo o mapa e empurra inimigos que estiverem colados nele.", "waves", isMaxPriority = true),
        SkillInfo("Zepelim Bombardeiro", "Supremo", "DIRIGÍVEL DE BOMBARDAS", "Invoca dirigível lento que bombardeia inimigos na área a cada 0.65s.", "thunderstorm")
      ),
      combos = listOf(
        ComboStep(1, "Ciclo H1 + Rajada", "Lance H1 para ativar a metralhadora passiva e descarregue os tiros."),
        ComboStep(2, "H2 Empurrão + Rajada", "Use o foguete da H2 para empurrar quem tentar pular em você e continue fuzilando com a passiva.")
      ),
      proTips = listOf(
        ProTip("Derreter Tanks", "O tiro da passiva causa dano baseado na vida máxima do alvo, ignorando a vida colossal de heróis como Arthur.")
      ),
      shortTip = "Cada habilidade usada reinicia a metralhadora da passiva: intercale H1 -> Ataque -> H2 -> Ataque -> Supremo -> Ataque para derreter qualquer equipe."
    )
  )
}
