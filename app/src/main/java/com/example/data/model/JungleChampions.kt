package com.example.data.model

import com.example.R

object JungleChampions {

  val list: List<Champion> = listOf(
    // 1. LAM
    ChampionFactory.makeHero(
      id = "lam",
      name = "Lam",
      title = "O Predador das Profundezas",
      heroClass = "Assassino",
      lane = Lane.SELVA,
      tier = HeroTier.SS,
      gtimgId = 528,
      localDrawableRes = R.drawable.img_hero_lam,
      difficultyStars = 3,
      winRate = "53.8%",
      winRateChange = "+1.4%",
      pickRate = "22.4%",
      banRate = "46.2%",
      isBanPriority = true,
      coreItemSummary = "Machado Tormento + Quebra-Estrela",
      buildSubtitle = "Dano de rajada extrema, 40% CDR e penetração total para abater atiradores instantaneamente.",
      spellName = "Punição Gelada",
      spellSubtitle = "Smite Selva",
      spellDescription = "Essencial para garantir Monstros da Selva (Dragão Tirano e Soberano). Aplica lentidão de 50% em heróis inimigos ao evoluir.",
      items = listOf(
        EquipmentItem("lam_1", "Faca de Caça", "Selva", "Acelera farm e concede dano acumulativo", "+60 AD contra monstros", "colorize"),
        EquipmentItem("lam_2", "Botas Resistência", "Movimento", "Reduz duração de controles e atordoamentos", "Tenacidade +35% • +110 Def. Mágica", "snowshoeing"),
        EquipmentItem("lam_3", "Machado do Tormento", "Ataque", "Item nuclear para dano de rajada e redução de recarga", "+85 Dano • CDR 15% • Lentidão", "gavel"),
        EquipmentItem("lam_4", "Mestre da Espada", "Ataque", "Concede dano adicional após lançar cada habilidade", "+80 Dano • Efeito Golpe Forte", "gavel"),
        EquipmentItem("lam_5", "Quebra-Estrelas", "Ataque", "Penetração maciça contra alvos blindados", "+80 Dano • +40% Perfuração Física", "gavel"),
        EquipmentItem("lam_6", "Lâmina Sábia", "Defesa", "Ressuscita o campeão após sofrer dano fatal em lutas", "+100 Defesa • Renascimento", "shield")
      ),
      skills = listOf(
        SkillInfo("Caça ao Sangue", "Passiva", "DANO VERDADEIRO EM ALVOS FERIDOS", "Inimigos com menos de 30% de vida são revelados no mapa e sofrem 15% de dano adicional.", "radar"),
        SkillInfo("Mergulho de Tubarão (H1)", "Habilidade 1", "MERGULHO SUBTERRÂNEO", "Mergulha no solo/água ganhando 60 de velocidade (ilimitada no rio). Ao emergir, salta causando dano e lentidão.", "waves", isMaxPriority = true),
        SkillInfo("Corte Giratório das Profundezas (H2)", "Habilidade 2", "DANO MULTI-ALVO", "Gira com lâminas duplas marcando alvos. Cada marca fortalece o próximo ataque com salto rápido e redução de recarga da H2.", "autorenew"),
        SkillInfo("Assalto do Predador", "Supremo", "ARRASTO E ISOLAMENTO", "Arremessa adagas atordoando o alvo e em seguida avança arrastando todos os inimigos no caminho até o final do percurso.", "thunderstorm")
      ),
      combos = listOf(
        ComboStep(1, "Emboscada Aquática (H1 Rio)", "Mergulhe pelo rio para atingir velocidade máxima e surpreenda os adversários emergindo por trás deles."),
        ComboStep(2, "Sequência de Fatiamento (H2 + Ataques)", "Acerte a H2 no grupo para carregar 3 marcas; execute 3 ataques básicos consecutivos para zerar a recarga da H2."),
        ComboStep(3, "Isolamento Decisivo (Supremo)", "Use o Supremo empurrando o atirador inimigo na direção da sua própria torre ou equipe.")
      ),
      proTips = listOf(
        ProTip("Domínio do Rio", "O rio é o terreno natural de Lam. Ele permanece submerso indefinidamente enquanto estiver na água."),
        ProTip("Reset da H2", "Cada ataque fortalecido consome uma marca e reduz o tempo de recarga da H2 em 1s.")
      ),
      shortTip = "Mergulhe pelo rio para alcançar emboscadas de velocidade máxima e use o Supremo para isolar o atirador inimigo diretamente para o seu time."
    ),

    // 2. MUSASHI
    ChampionFactory.makeHero(
      id = "musashi",
      name = "Musashi",
      title = "O Espadachim Lendário",
      heroClass = "Guerreiro / Assassino",
      lane = Lane.SELVA,
      tier = HeroTier.S,
      gtimgId = 121,
      localDrawableRes = R.drawable.img_hero_musashi,
      difficultyStars = 3,
      winRate = "52.9%",
      winRateChange = "+0.7%",
      pickRate = "18.2%",
      banRate = "23.4%",
      coreItemSummary = "Machado Tormento + Mestre Espada",
      buildSubtitle = "Trancamento inescapável com o Supremo que bloqueia qualquer cura do alvo por 5 segundos.",
      spellName = "Punição Gelada",
      spellDescription = "Auxilia na limpeza rápida dos campos e desacelera alvos para encaixar o Supremo.",
      items = listOf(
        EquipmentItem("mu_1", "Faca de Caça", "Selva", "Farm na selva", "+60 AD", "colorize"),
        EquipmentItem("mu_2", "Botas de Resistência", "Movimento", "Tenacidade", "Tenacidade +35%", "snowshoeing"),
        EquipmentItem("mu_3", "Machado do Tormento", "Ataque", "Dano e CDR", "+85 Dano • 15% CDR", "gavel"),
        EquipmentItem("mu_4", "Mestre da Espada", "Ataque", "Dano fortalecido", "+80 Dano • Efeito Ataque", "gavel"),
        EquipmentItem("mu_5", "Presságio Ominoso", "Defesa", "Vida e Armadura", "+270 Defesa • +1200 HP", "shield"),
        EquipmentItem("mu_6", "Lâmina Sábia", "Defesa", "Ressurreição", "+100 Armadura", "shield")
      ),
      skills = listOf(
        SkillInfo("Espírito das Duas Espadas", "Passiva", "CORTES FORTALECIDOS", "Usar habilidades concede cargas de golpes reforçados com salto e dano percentual.", "radar"),
        SkillInfo("Golpe de Espada do Vento (H1)", "Habilidade 1", "BLOQUEIO DE PROJÉTEIS", "Lança onda de vento que anula projéteis inimigos e desacelera os alvos.", "waves"),
        SkillInfo("Investida Rápida (H2)", "Habilidade 2", "AVANÇO E ESCUDO", "Avança e ganha escudo protetor se colidir com um inimigo.", "autorenew", isMaxPriority = true),
        SkillInfo("Duelo do Destino", "Supremo", "BLOQUEIO DE CURA TOTAL", "Tranca no alvo e cai do céu: o alvo fica impedido de recuperar qualquer vida por 5s.", "thunderstorm")
      ),
      combos = listOf(
        ComboStep(1, "Supremo no Campeão de Cura", "Ative o Supremo em alvos como Cai Yan ou heróis com muito roubo de vida."),
        ComboStep(2, "H1 Vento + H2 Cortes", "Lance o vento para bloquear contra-ataques e avance com a H2 encadeando passivas.")
      ),
      proTips = listOf(
        ProTip("Anti-Cura Absoluto", "O Supremo de Musashi anula 100% dos escudos e curas que o alvo receberia durante a marca.")
      ),
      shortTip = "Use o Supremo prioritariamente no atirador inimigo ou no herói de maior sustentação: a marca bloqueia completamente qualquer efeito de cura por 5 segundos."
    ),

    // 3. KAIZER
    ChampionFactory.makeHero(
      id = "kaizer",
      name = "Kaizer",
      title = "O Guerreiro Demoníaco",
      heroClass = "Lutador / Tank",
      lane = Lane.SELVA,
      tier = HeroTier.S,
      gtimgId = 193,
      localDrawableRes = R.drawable.img_hero_kaizer,
      difficultyStars = 2,
      winRate = "52.1%",
      winRateChange = "+0.5%",
      pickRate = "21.6%",
      banRate = "15.8%",
      coreItemSummary = "Machado Tormento + Clamor de Gelo",
      buildSubtitle = "Transformação demoníaca com bloqueio de dano plano e +50% de dano contra alvos isolados.",
      spellName = "Punição Gelada",
      spellDescription = "Garante os objetivos épicos e aplica lentidão aos alvos da selva.",
      items = listOf(
        EquipmentItem("kz_1", "Faca de Caça", "Selva", "Farm na selva", "+60 AD", "colorize"),
        EquipmentItem("kz_2", "Botas de Resistência", "Movimento", "Tenacidade", "Tenacidade +35%", "snowshoeing"),
        EquipmentItem("kz_3", "Machado do Tormento", "Ataque", "Dano e CDR", "+85 Dano • 15% CDR", "gavel"),
        EquipmentItem("kz_4", "Clamor de Gelo", "Defesa", "Lentidão e armadura", "+360 Defesa • +1000 Vida", "shield"),
        EquipmentItem("kz_5", "Olho da Fênix", "Defesa", "Resistência mágica", "+240 Def. Mág. • +1200 Vida", "shield"),
        EquipmentItem("kz_6", "Lâmina Sábia", "Defesa", "Ressurreição", "+100 Armadura", "shield")
      ),
      skills = listOf(
        SkillInfo("Lâmina Solitária", "Passiva", "DANO ISOLADO", "Se seus golpes atingirem apenas um único alvo, causam 50% de dano adicional.", "radar"),
        SkillInfo("Lâmina Giratória (H1)", "Habilidade 1", "CURA E LENTIDÃO", "Arremessa uma adaga ricocheteante que causa lentidão e cura Kaizer.", "waves", isMaxPriority = true),
        SkillInfo("Impacto Demoníaco (H2)", "Habilidade 2", "AVANÇO E ATORDOAMENTO", "Avança com 2 cortes e atordoa com o golpe reforçado.", "autorenew"),
        SkillInfo("Armadura Demoníaca", "Supremo", "TRANSFORMAÇÃO", "Invoca a armadura demoníaca: ganha dano colossal, velocidade e bloqueia dano recebido.", "thunderstorm")
      ),
      combos = listOf(
        ComboStep(1, "Transformação Antecipada (Supremo)", "Ative o Supremo antes de entrar na luta para ter o bônus de dano e bloqueio."),
        ComboStep(2, "Lentidão e Avanço (H1 + H2)", "Acerte a H1 para desacelerar e avance com a H2 no atirador.")
      ),
      proTips = listOf(
        ProTip("Bônus de Alvo Único", "Tente lutar onde não haja tropas inimigas para ativar o bônus de 50% de dano da passiva.")
      ),
      shortTip = "Ative o Supremo antes de entrar no combate para reduzir todo dano recebido com a armadura demoníaca e procure alvos isolados."
    ),

    // 4. WUKONG
    ChampionFactory.makeHero(
      id = "wukong",
      name = "Sun Wukong",
      title = "O Rei Macaco",
      heroClass = "Assassino",
      lane = Lane.SELVA,
      tier = HeroTier.S,
      gtimgId = 167,
      localDrawableRes = R.drawable.img_hero_wukong,
      difficultyStars = 2,
      winRate = "52.8%",
      winRateChange = "+1.0%",
      pickRate = "24.1%",
      banRate = "31.7%",
      isBanPriority = true,
      coreItemSummary = "Lâmina Sem Fim + Mestre Espada",
      buildSubtitle = "Dano crítico nuclear capaz de evaporar atiradores e magos em menos de 1 segundo.",
      spellName = "Punição Gelada",
      spellDescription = "Acelera a conquista de nível 4 para ganks letais com o Supremo.",
      items = listOf(
        EquipmentItem("wk_1", "Faca de Caça", "Selva", "Farm na selva", "+60 AD", "colorize"),
        EquipmentItem("wk_2", "Botas de Agilidade", "Movimento", "Velocidade de Ataque", "+25% Vel. Ataque", "snowshoeing"),
        EquipmentItem("wk_3", "Lâmina Sem Fim", "Ataque", "Dano Crítico Máximo", "+110 Dano • +20% Crítico", "gavel"),
        EquipmentItem("wk_4", "Mestre da Espada", "Ataque", "Dano após Habilidade", "+80 Dano • Golpe Forte", "gavel"),
        EquipmentItem("wk_5", "Quebra-Estrelas", "Ataque", "Ignora Armadura", "+40% Pen. Física", "gavel"),
        EquipmentItem("wk_6", "Lâmina Sábia", "Defesa", "Ressurreição", "+100 Armadura", "shield")
      ),
      arcanas = ChampionFactory.buildCritArcanas(),
      arcanaStatsSummary = ChampionFactory.standardCritSummary,
      skills = listOf(
        SkillInfo("Pancada do Grande Sábio", "Passiva", "CRÍTICO INERTE", "Wukong tem 20% de crítico inato; após cada habilidade, o próximo ataque salta no alvo.", "radar"),
        SkillInfo("Proteção do Macaco (H1)", "Habilidade 1", "IMUNIDADE A HABILIDADE", "Gera escudo se atingido por uma habilidade inimiga e concede invulnerabilidade de 0.2s.", "waves"),
        SkillInfo("Salto nas Nuvens (H2)", "Habilidade 2", "SALTO DUPLO", "Salta para a frente; se atingir um alvo, realiza um segundo salto mais longo.", "autorenew", isMaxPriority = true),
        SkillInfo("Bastão Sagrado Ruyi", "Supremo", "ARREMESSO EM ÁREA", "Fincha o bastão no solo arremessando inimigos ao redor e marcando-os para dano extra.", "thunderstorm")
      ),
      combos = listOf(
        ComboStep(1, "Aproximação com H2", "Salte em uma tropa ou monstro com H2 para fechar distância no ADC."),
        ComboStep(2, "Supremo + H1 com Crítico", "Use o Supremo para arremessar, bata com o ataque crítico e ative H1 para bloquear respostas.")
      ),
      proTips = listOf(
        ProTip("Imunidade da H1", "Ative a H1 bem no momento em que magos forem soltar atordoamentos para ganhar escudo.")
      ),
      shortTip = "Sempre dê um ataque básico após CADA habilidade: a passiva faz o bastão saltar na cabeça do alvo com dano crítico massivo."
    ),

    // 5. LI BAI
    ChampionFactory.makeHero(
      id = "libai",
      name = "Li Bai",
      title = "O Poeta da Espada",
      heroClass = "Assassino",
      lane = Lane.SELVA,
      tier = HeroTier.A,
      gtimgId = 131,
      difficultyStars = 4,
      winRate = "51.3%",
      winRateChange = "+0.3%",
      pickRate = "14.5%",
      banRate = "8.9%",
      coreItemSummary = "Machado Tormento + Quebra-Estrelas",
      buildSubtitle = "Invulnerabilidade dupla com a H2 e o Supremo para dizimar inimigos debaixo da torre.",
      spellName = "Punição Gelada",
      spellDescription = "Permite limpar os monstros e acumular os 4 cortes da passiva antes do gank.",
      items = listOf(
        EquipmentItem("lb_j1", "Faca de Caça", "Selva", "Farm selva", "+60 AD", "colorize"),
        EquipmentItem("lb_j2", "Botas de Agilidade", "Movimento", "Velocidade", "+25% Vel. Ataque", "snowshoeing"),
        EquipmentItem("lb_j3", "Machado do Tormento", "Ataque", "Dano e CDR", "+85 Dano • 15% CDR", "gavel"),
        EquipmentItem("lb_j4", "Mestre da Espada", "Ataque", "Dano ampliado", "+80 Dano", "gavel"),
        EquipmentItem("lb_j5", "Quebra-Estrelas", "Ataque", "Penetração", "+40% Pen. Física", "gavel"),
        EquipmentItem("lb_j6", "Lâmina Sábia", "Defesa", "Ressurreição", "+100 Armadura", "shield")
      ),
      skills = listOf(
        SkillInfo("Canto da Espada", "Passiva", "DESBLOQUEIO DO SUPREMO", "Acertar 4 ataques básicos em 3s desbloqueia o Supremo e concede dano extra.", "radar"),
        SkillInfo("Passo do Vento (H1)", "Habilidade 1", "DOIS AVANÇOS E RETORNO", "Avança 2 vezes atordoando alvos e pode reativar para voltar à sombra original.", "waves", isMaxPriority = true),
        SkillInfo("Círculo de Espadas (H2)", "Habilidade 2", "INVULNERABILIDADE E BORDA", "Desenha círculo invulnerável; inimigos que tocarem a borda perdem armadura.", "autorenew"),
        SkillInfo("Dança das Lâminas Fantasmas", "Supremo", "DANÇA INVULNERÁVEL", "Desfere 5 cortes celestiais enquanto Li Bai fica completamente invulnerável.", "thunderstorm")
      ),
      combos = listOf(
        ComboStep(1, "Carregar Passiva na Selva", "Dê 4 ataques básicos em um monstro próximo para destravar o Supremo."),
        ComboStep(2, "Avanço H1 + Supremo + Retorno", "Avance 2 vezes com H1 até a torre inimiga, lance o Supremo e volte à segurança com o terceiro toque da H1.")
      ),
      proTips = listOf(
        ProTip("Volta Segura", "O terceiro clique da H1 sempre o traz de volta ao ponto exato de onde você saiu, mesmo que esteja dentro da base inimiga.")
      ),
      shortTip = "Carregue os 4 golpes da passiva em monstros da selva antes de entrar na luta com os dois avanços da H1, soltar o Supremo invulnerável e retornar."
    ),

    // 6. HAN XIN
    ChampionFactory.makeHero(
      id = "hanxin",
      name = "Han Xin",
      title = "O General Lanceiro",
      heroClass = "Assassino",
      lane = Lane.SELVA,
      tier = HeroTier.S,
      gtimgId = 150,
      difficultyStars = 4,
      winRate = "52.0%",
      winRateChange = "+0.6%",
      pickRate = "16.8%",
      banRate = "14.2%",
      coreItemSummary = "Lâmina Sem Fim + Mestre Espada",
      buildSubtitle = "Mobilidade sem igual com 3 saltos consecutivos para roubar recursos e derrubar torres.",
      spellName = "Punição Gelada",
      spellDescription = "Permite invadir a selva inimiga e contestar bônus vermelhos e azuis com facilidade.",
      items = listOf(
        EquipmentItem("hx_1", "Faca de Caça", "Selva", "Farm", "+60 AD", "colorize"),
        EquipmentItem("hx_2", "Botas de Agilidade", "Movimento", "Velocidade", "+25% Vel. Atq", "snowshoeing"),
        EquipmentItem("hx_3", "Machado do Tormento", "Ataque", "Dano e CDR", "+85 Dano", "gavel"),
        EquipmentItem("hx_4", "Lâmina Sem Fim", "Ataque", "Crítico", "+110 Dano • +20% Crítico", "gavel"),
        EquipmentItem("hx_5", "Quebra-Estrelas", "Ataque", "Penetração", "+40% Pen. Física", "gavel"),
        EquipmentItem("hx_6", "Lâmina Sábia", "Defesa", "Ressurreição", "+100 Armadura", "shield")
      ),
      skills = listOf(
        SkillInfo("Lança Triunfante", "Passiva", "VELOCIDADE E ARREMESSO", "O quarto ataque básico consecutivo arremessa o alvo ao ar; habilidades concedem 50% de vel. ataque.", "radar"),
        SkillInfo("Salto Destemido (H1)", "Habilidade 1", "DOIS SALTOS", "Salta arremessando alvos e pode saltar novamente em 5s.", "waves", isMaxPriority = true),
        SkillInfo("Recuo Estratégico (H2)", "Habilidade 2", "DESLIZAMENTO REVERSO", "Desliza para trás e fortalece o próximo ataque com varredura horizontal.", "autorenew"),
        SkillInfo("Fúria da Lança Nacional", "Supremo", "QUATRO GOLPES E SUPER ARMADURA", "Desfere 4 golpes de lança ganhando super armadura imune a qualquer controle.", "thunderstorm")
      ),
      combos = listOf(
        ComboStep(1, "Aproximação Rápida (H2 + H1)", "Deslize com H2 e salte com H1 para arremessar o oponente ao ar."),
        ComboStep(2, "Supremo Imune", "Ative o Supremo para ignorar atordoamentos enquanto desfere dano maciço.")
      ),
      proTips = listOf(
        ProTip("Split-Push Imbatível", "Han Xin é o melhor herói do jogo para levar torres laterais e fugir quando inimigos responderem.")
      ),
      shortTip = "Aproveite os 3 saltos das habilidades para invadir a selva inimiga e empurrar rotas laterais, pois ninguém consegue alcançá-lo."
    ),

    // 7. LUNA
    ChampionFactory.makeHero(
      id = "luna",
      name = "Luna",
      title = "A Deusa do Luar",
      heroClass = "Maga / Assassina",
      lane = Lane.SELVA,
      tier = HeroTier.SS,
      gtimgId = 146,
      difficultyStars = 5,
      winRate = "54.1%",
      winRateChange = "+1.6%",
      pickRate = "11.3%",
      banRate = "49.5%",
      isBanPriority = true,
      coreItemSummary = "Cajado do Luar + Máscara da Agonia",
      buildSubtitle = "Supremo infinito com reinício instantâneo ao atravessar qualquer alvo marcado pelo luar.",
      spellName = "Punição Gelada",
      spellDescription = "Indispensável para o farm acelerado na selva e controle dos monstros azuis.",
      items = listOf(
        EquipmentItem("lun_1", "Faca de Caça Mágica", "Selva", "Dano Mágico Selva", "+80 AP", "colorize"),
        EquipmentItem("lun_2", "Botas de Resistência", "Movimento", "Tenacidade", "+110 Def. Mágica", "snowshoeing"),
        EquipmentItem("lun_3", "Cajado do Luar", "Mágica", "Dano e Vida", "+140 AP • +800 HP", "gavel"),
        EquipmentItem("lun_4", "Máscara da Agonia", "Mágica", "Dano percentual", "+120 AP • Queimadura", "gavel"),
        EquipmentItem("lun_5", "Manto Ardente", "Defesa", "Sobrevivência", "+1000 Vida • Queima", "shield"),
        EquipmentItem("lun_6", "Livro dos Santos", "Mágica", "Poder Máximo", "+400 Poder Mágico", "gavel")
      ),
      arcanas = ChampionFactory.buildMagicalPenArcanas(),
      arcanaStatsSummary = ChampionFactory.standardMagicalPenSummary,
      skills = listOf(
        SkillInfo("Dança do Luar", "Passiva", "MARCA DO LUAR", "O primeiro ataque aproxima do alvo; o terceiro marca os inimigos com luar.", "radar"),
        SkillInfo("Luz Crescente (H1)", "Habilidade 1", "ONDA DE LUAR", "Dispara feixe de luar marcando todos os inimigos no trajeto.", "waves", isMaxPriority = true),
        SkillInfo("Chamas Ardentes (H2)", "Habilidade 2", "PUXÃO E ESCUDO", "Puxa inimigos ao redor para perto de Luna, atordoa, gera escudo e marca os alvos.", "autorenew"),
        SkillInfo("Dança da Nova Lua", "Supremo", "SUPREMO INFINITO", "Avança através do alvo: se ele estiver marcado com luar, zera a recarga do Supremo.", "thunderstorm")
      ),
      combos = listOf(
        ComboStep(1, "Marca com H1 + Supremo", "Dispare a H1 e avance com o Supremo no alvo marcado."),
        ComboStep(2, "H2 Puxão + Novo Supremo", "Puxe com a H2 aplicando nova marca e cruze novamente com o Supremo sem parar.")
      ),
      proTips = listOf(
        ProTip("Nunca Erre o Supremo", "Se usar o Supremo em um alvo sem marca de luar, a habilidade entrará em recarga de mais de 15 segundos.")
      ),
      shortTip = "Nunca lance o Supremo sem ter certeza de atingir um alvo com a marca de luar ativa: o Supremo reinicia instantaneamente permitindo dano infinito."
    ),

    // 8. AUGRAN
    ChampionFactory.makeHero(
      id = "augran",
      name = "Augran",
      title = "O Guardião da Morte",
      heroClass = "Lutador / Assassino",
      lane = Lane.SELVA,
      tier = HeroTier.SS,
      gtimgId = 578,
      difficultyStars = 3,
      winRate = "53.6%",
      winRateChange = "+1.3%",
      pickRate = "19.5%",
      banRate = "44.8%",
      isBanPriority = true,
      coreItemSummary = "Machado Tormento + Clamor de Gelo",
      buildSubtitle = "Passagem espiritual através de paredes, colheita de almas e clonagem de heróis caídos.",
      spellName = "Punição Gelada",
      spellDescription = "Permite transitar pela selva com velocidade extrema e finalizar monstros instantaneamente.",
      items = listOf(
        EquipmentItem("aug_1", "Faca de Caça", "Selva", "Farm", "+60 AD", "colorize"),
        EquipmentItem("aug_2", "Botas de Resistência", "Movimento", "Tenacidade", "Tenacidade +35%", "snowshoeing"),
        EquipmentItem("aug_3", "Machado do Tormento", "Ataque", "Dano e CDR", "+85 Dano", "gavel"),
        EquipmentItem("aug_4", "Clamor de Gelo", "Defesa", "Vida e Armadura", "+360 Defesa", "shield"),
        EquipmentItem("aug_5", "Quebra-Estrelas", "Ataque", "Penetração", "+40% Pen. Física", "gavel"),
        EquipmentItem("aug_6", "Lâmina Sábia", "Defesa", "Ressurreição", "+100 Armadura", "shield")
      ),
      skills = listOf(
        SkillInfo("Ceifador de Almas", "Passiva", "EXECUÇÃO DE ALMAS", "Ataques conectam almas aos inimigos; alvos abaixo do limiar de vida são executados.", "radar"),
        SkillInfo("Caminho Espiritual (H1)", "Habilidade 1", "CRUZAR PAREDES", "Cria um rastro espectral que ignora paredes e acelera Augran dramaticamente.", "waves", isMaxPriority = true),
        SkillInfo("Garras da Morte (H2)", "Habilidade 2", "CORTE E MARCA", "Corta em semicírculo marcando múltiplos inimigos e reduzindo defesas.", "autorenew"),
        SkillInfo("Descida das Almas", "Supremo", "CLONES ESPECTRAIS", "Salta criando clones dos heróis inimigos atingidos que lutam ao seu lado.", "thunderstorm")
      ),
      combos = listOf(
        ComboStep(1, "Aproximação pela Parede com H1", "Lance o caminho da H1 através do muro para surpreender o inimigo desavisado."),
        ComboStep(2, "Supremo + H2 de Execução", "Ative o Supremo no meio dos inimigos para gerar clones espectrais e ceifar almas.")
      ),
      proTips = listOf(
        ProTip("Cruzar Terrenos", "A H1 permite a Augran andar por cima de quaisquer árvores e muros do mapa sem ser bloqueado.")
      ),
      shortTip = "Use o Caminho Espiritual da H1 para andar sobre qualquer parede do mapa, emboscando as rotas por ângulos impossíveis de serem previstos."
    )
  )
}
