package com.example.data.model

import com.example.R

object ClashLaneChampions {

  val list: List<Champion> = listOf(
    // 1. LU BU
    ChampionFactory.makeHero(
      id = "lubu",
      name = "Lu Bu",
      title = "O Guerreiro Invencível",
      heroClass = "Lutador / Tank",
      lane = Lane.TOP,
      tier = HeroTier.SS,
      gtimgId = 123,
      localDrawableRes = R.drawable.img_hero_lubu,
      difficultyStars = 3,
      winRate = "52.7%",
      winRateChange = "+1.1%",
      pickRate = "19.8%",
      banRate = "26.5%",
      isBanPriority = true,
      coreItemSummary = "Lâmina Pura + Machado Tormento",
      buildSubtitle = "Dano real devastador com 100% de penetração, escudo absorvente e roubo de vida massivo no Supremo.",
      spellName = "Golpe de Fagulha",
      spellSubtitle = "Teleporte / Flash",
      spellDescription = "Permite reposicionar a área do golpe da Habilidade 1 ou do Supremo durante o salto, garantindo o abate.",
      items = listOf(
        EquipmentItem("lb_1", "Botas de Resistência", "Movimento", "Reduz controles", "Tenacidade +35%", "snowshoeing"),
        EquipmentItem("lb_2", "Lâmina Pura", "Ataque", "Dano físico e crítico", "+100 Dano Físico • +10% CDR", "gavel"),
        EquipmentItem("lb_3", "Machado do Tormento", "Ataque", "Penetração e lentidão", "+85 Dano • +15% CDR", "gavel"),
        EquipmentItem("lb_4", "Quebra-Estrelas", "Ataque", "Ignora armadura", "+80 Dano • +40% Pen. Física", "gavel"),
        EquipmentItem("lb_5", "Olho da Fênix", "Defesa", "Aumenta cura e defesa mágica", "+240 Def. Mág. • +1200 Vida", "shield"),
        EquipmentItem("lb_6", "Lâmina Sábia", "Defesa", "Ressurreição imediata", "+100 Armadura", "shield")
      ),
      skills = listOf(
        SkillInfo("Desafio Feroz", "Passiva", "DANO REAL", "Acertar a H1 encanta a alabarda de Lu Bu: todos os ataques causam dano real e curam sua vida.", "radar"),
        SkillInfo("Corte Rompedor (H1)", "Habilidade 1", "DANO E SALTO", "Desfere um golpe frontal em arco; o acerto concede dano real pelos próximos 8s.", "waves", isMaxPriority = true),
        SkillInfo("Espírito Inquebrável (H2)", "Habilidade 2", "ESCUDO E LENTIDÃO", "Rouba almas inimigas ganhando escudo protetor e desacelerando adversários.", "autorenew"),
        SkillInfo("Fúria do Conquistador", "Supremo", "SALTO EM ÁREA", "Salta pelo ar caindo com impacto sísmico, arremessando todos os inimigos na área.", "thunderstorm")
      ),
      combos = listOf(
        ComboStep(1, "Iniciação Suprema", "Salte com o Supremo no meio da formação adversária para arremessar múltiplos alvos."),
        ComboStep(2, "H1 + Ataques Básicos", "Acerte a H1 para ativar o encanto de dano real e encadeie ataques básicos curativos.")
      ),
      proTips = listOf(
        ProTip("Encantamento Constante", "Nunca use o Supremo sem que a alabarda esteja encantada pela H1 para causar o dobro de dano.")
      ),
      shortTip = "Mantenha a alabarda sempre encantada com a H1 para converter todo seu dano em Dano Real que ignora escudos e armaduras."
    ),

    // 2. MAYENE
    ChampionFactory.makeHero(
      id = "mayene",
      name = "Mayene",
      title = "A Mestra do Punho",
      heroClass = "Lutadora / Assassina",
      lane = Lane.TOP,
      tier = HeroTier.SS,
      gtimgId = 544,
      localDrawableRes = R.drawable.img_hero_mayene,
      difficultyStars = 4,
      winRate = "54.2%",
      winRateChange = "+1.8%",
      pickRate = "17.4%",
      banRate = "41.9%",
      isBanPriority = true,
      coreItemSummary = "Machado Tormento + Mestre Espada",
      buildSubtitle = "Flexibilidade máxima com 4 combinações de chutes marciais, imunidade e dano percentual de vida máxima.",
      spellName = "Golpe de Fagulha",
      spellDescription = "Combina com o chute H2-H1 para empurrar os inimigos da torre inimiga para dentro da sua torre.",
      items = listOf(
        EquipmentItem("my_1", "Botas de Resistência", "Movimento", "Reduz atordoamentos", "Tenacidade +35%", "snowshoeing"),
        EquipmentItem("my_2", "Machado do Tormento", "Ataque", "Dano e aceleração", "+85 AD • CDR 15%", "gavel"),
        EquipmentItem("my_3", "Mestre da Espada", "Ataque", "Dano amplificado pós-golpe", "+80 AD • Efeito Golpe Forte", "gavel"),
        EquipmentItem("my_4", "Quebra-Estrelas", "Ataque", "Penetração pesada", "+40% Perfuração Física", "gavel"),
        EquipmentItem("my_5", "Presságio Ominoso", "Defesa", "Reduz ataque inimigo", "+270 Defesa • +1200 Vida", "shield"),
        EquipmentItem("my_6", "Lâmina Sábia", "Defesa", "Ressurreição", "+100 Armadura", "shield")
      ),
      skills = listOf(
        SkillInfo("Preguiça Esperta", "Passiva", "ENERGIA DUPLA", "Mayene armazena 2 cargas de H1 e H2 para combinar 4 sequências marciais diferentes.", "radar"),
        SkillInfo("Corrida Frontal (H1)", "Habilidade 1", "AVANÇO E PUXÃO", "Avança puxando o inimigo; combinada ativa agarrão e arremesso.", "waves", isMaxPriority = true),
        SkillInfo("Salto Acrobático (H2)", "Habilidade 2", "IMUNIDADE E ESQUIVA", "Salta ganhando imunidade a controles e curando parte da vida.", "autorenew"),
        SkillInfo("Despertar Preguiçoso", "Supremo", "RESTAURAÇÃO TOTAL", "Restaura imediatamente cargas de H1 e H2 e amplia a velocidade.", "thunderstorm")
      ),
      combos = listOf(
        ComboStep(1, "Combo Chute Mortal (H1 + H2)", "Use H1 para puxar e H2 para acertar o chute de calcanhar no ar causando dano em área."),
        ComboStep(2, "Combo Insec (H2 + H1)", "Pule por trás com H2 e use H1 para empurrar o alvo para dentro dos seus aliados.")
      ),
      proTips = listOf(
        ProTip("Uso da Imunidade", "A segunda ativação da H2 concede imunidade completa a dano durante o salto acrobático.")
      ),
      shortTip = "A sequência H1 + H2 é perfeita para abates rápidos no 1v1, enquanto H2 + H1 é ideal para trazer o atirador inimigo para sua equipe."
    ),

    // 3. ARTHUR
    ChampionFactory.makeHero(
      id = "arthur",
      name = "Arthur",
      title = "O Rei de Avalon",
      heroClass = "Tank / Lutador",
      lane = Lane.TOP,
      tier = HeroTier.S,
      gtimgId = 166,
      localDrawableRes = R.drawable.img_hero_arthur,
      difficultyStars = 1,
      winRate = "51.8%",
      winRateChange = "+0.4%",
      pickRate = "28.3%",
      banRate = "4.2%",
      coreItemSummary = "Clamor de Gelo + Olho da Fênix",
      buildSubtitle = "Iniciação agressiva com silêncio permanente, defesa impenetrável e dano percentual contínuo.",
      spellName = "Golpe de Fagulha",
      spellDescription = "Permite fechar distância instantaneamente e executar o silêncio da Habilidade 1 com precisão.",
      items = listOf(
        EquipmentItem("art_1", "Botas de Resistência", "Movimento", "Resistência a CC", "+110 Def. Mágica", "snowshoeing"),
        EquipmentItem("art_2", "Machado do Tormento", "Ataque", "Dano e CDR", "+85 Dano • CDR 15%", "gavel"),
        EquipmentItem("art_3", "Clamor de Gelo", "Defesa", "Lentidão em área", "+360 Defesa • +1000 HP", "shield"),
        EquipmentItem("art_4", "Presságio Ominoso", "Defesa", "Reduz velocidade do ADC", "+270 Defesa • +1200 HP", "shield"),
        EquipmentItem("art_5", "Olho da Fênix", "Defesa", "Regeneração amplificada", "+240 Def. Mágica", "shield"),
        EquipmentItem("art_6", "Lâmina Sábia", "Defesa", "Segunda vida", "+100 Defesa", "shield")
      ),
      skills = listOf(
        SkillInfo("Coração Sagrado", "Passiva", "REGENERAÇÃO", "Regenera 2% da vida máxima a cada 2 segundos passivamente.", "radar"),
        SkillInfo("Corte Valente (H1)", "Habilidade 1", "VELOCIDADE E SILÊNCIO", "Ganha 30% de velocidade; o próximo golpe silencia o inimigo por 1s e marca o alvo.", "waves", isMaxPriority = true),
        SkillInfo("Espadas Giratórias (H2)", "Habilidade 2", "DANO CONTÍNUO", "Cria 3 espadas de luz ao seu redor que causam dano mágico contínuo por 5s.", "autorenew"),
        SkillInfo("Salto do Julgamento", "Supremo", "EXECUÇÃO EM ÁREA", "Salta contra o herói inimigo causando dano baseado na vida perdida e arremessando-o ao ar.", "thunderstorm")
      ),
      combos = listOf(
        ComboStep(1, "Silenciamento Inicial (H1)", "Ative H1 para acelerar e silenciar a habilidade de fuga do alvo."),
        ComboStep(2, "Espadas e Supremo (H2 + Supremo)", "Ative H2 para dano contínuo e finalize com o impacto do Supremo.")
      ),
      proTips = listOf(
        ProTip("Silêncio nos Magos", "Use a H1 para cortar conjurações canalizadas de magos como Angela ou Princesa Gélida.")
      ),
      shortTip = "Use a Habilidade 1 constantemente para se movimentar pelo mapa com velocidade máxima e cancelar canalizações inimigas com o silêncio."
    ),

    // 4. BIRON
    ChampionFactory.makeHero(
      id = "biron",
      name = "Biron",
      title = "O Conquistador das Tempestades",
      heroClass = "Lutador / Tank",
      lane = Lane.TOP,
      tier = HeroTier.S,
      gtimgId = 503,
      difficultyStars = 2,
      winRate = "52.4%",
      winRateChange = "+0.9%",
      pickRate = "15.6%",
      banRate = "11.2%",
      coreItemSummary = "Machado Tormento + Clamor de Gelo",
      buildSubtitle = "Escudo gigantesco no Supremo e cura monstruosa na Habilidade 1 com carga elétrica máxima.",
      spellName = "Golpe de Fagulha",
      spellDescription = "Surpreende o atirador inimigo com avanço relâmpago e atordoamento instantâneo.",
      items = listOf(
        EquipmentItem("bir_1", "Botas de Resistência", "Movimento", "Reduz atordoamentos", "Tenacidade +35%", "snowshoeing"),
        EquipmentItem("bir_2", "Machado do Tormento", "Ataque", "Dano e penetração", "+85 Dano • 15% CDR", "gavel"),
        EquipmentItem("bir_3", "Clamor de Gelo", "Defesa", "Lentidão e armadura", "+360 Defesa • +1000 Vida", "shield"),
        EquipmentItem("bir_4", "Armadura Espinhosa", "Defesa", "Reflete dano físico", "+400 Armadura", "shield"),
        EquipmentItem("bir_5", "Olho da Fênix", "Defesa", "Amplifica curas", "+240 Def. Mágica", "shield"),
        EquipmentItem("bir_6", "Lâmina Sábia", "Defesa", "Ressurreição", "+100 Armadura", "shield")
      ),
      skills = listOf(
        SkillInfo("Carga Elétrica", "Passiva", "ENERGIA", "Golpes geram energia que fortalecem todas as habilidades com cura e dano extra.", "radar"),
        SkillInfo("Golpe Trovejante (H1)", "Habilidade 1", "DANO E CURA", "Gira o martelo duas vezes curando vida baseada no dano causado.", "waves", isMaxPriority = true),
        SkillInfo("Salto Eletrizante (H2)", "Habilidade 2", "AVANÇO E ATORDOAMENTO", "Avança e fortalece o próximo golpe com arremesso aéreo.", "autorenew"),
        SkillInfo("Campo Protetor", "Supremo", "ESCUDO MASSIVO", "Bate no chão gerando um escudo colossal e causando lentidão.", "thunderstorm")
      ),
      combos = listOf(
        ComboStep(1, "Carregar Energia nas Tropas", "Bata nas tropas para atingir mais de 30 pontos de energia."),
        ComboStep(2, "Avanço H2 + H1 + Supremo", "Use H2 para atordoar, H1 para curar e o Supremo para absorver retaliações.")
      ),
      proTips = listOf(
        ProTip("Lute com Energia Alta", "Nunca inicie um combate no topo sem ter pelo menos uma barra de energia amarela carregada.")
      ),
      shortTip = "Carregue sua barra de energia nas tropas antes de trocar dano para triplicar a cura da H1 e o escudo do Supremo."
    ),

    // 5. MULAN
    ChampionFactory.makeHero(
      id = "mulan",
      name = "Mulan",
      title = "A Heroína de Duas Lâminas",
      heroClass = "Lutadora / Assassina",
      lane = Lane.TOP,
      tier = HeroTier.A,
      gtimgId = 154,
      difficultyStars = 5,
      winRate = "51.1%",
      winRateChange = "+0.2%",
      pickRate = "12.8%",
      banRate = "7.3%",
      coreItemSummary = "Machado Tormento + Quebra-Estrelas",
      buildSubtitle = "Alternância entre forma leve ágil e forma pesada com super armadura imune a controles.",
      spellName = "Golpe de Fagulha",
      spellDescription = "Fundamental para redirecionar o empurrão da forma pesada ou acertar a H1 carregada.",
      items = listOf(
        EquipmentItem("mul_1", "Botas de Resistência", "Movimento", "Reduz CC", "Tenacidade +35%", "snowshoeing"),
        EquipmentItem("mul_2", "Machado do Tormento", "Ataque", "Dano e CDR", "+85 Dano", "gavel"),
        EquipmentItem("mul_3", "Quebra-Estrelas", "Ataque", "Penetração", "+40% Pen. Física", "gavel"),
        EquipmentItem("mul_4", "Presságio Ominoso", "Defesa", "Vida e Armadura", "+270 Defesa", "shield"),
        EquipmentItem("mul_5", "Olho da Fênix", "Defesa", "Defesa Mágica", "+240 Def. Mágica", "shield"),
        EquipmentItem("mul_6", "Lâmina Sábia", "Defesa", "Ressurreição", "+100 Defesa", "shield")
      ),
      skills = listOf(
        SkillInfo("Lâminas Duplas", "Passiva", "SILÊNCIO E IMUNIDADE", "A forma leve silencia com 5 marcas; a pesada concede 40% de redução de dano.", "radar"),
        SkillInfo("Passo de Vento (H1)", "Habilidade 1", "AVANÇO / CORTE PESADO", "Forma leve avança duas vezes; forma pesada carrega golpe devastador.", "waves", isMaxPriority = true),
        SkillInfo("Arremesso de Adaga (H2)", "Habilidade 2", "LENTIDÃO / EMPURRÃO", "Atira adaga giratória ou empurra continuamente os oponentes.", "autorenew"),
        SkillInfo("Troca de Postura", "Supremo", "FORMA PESADA / LEVE", "Alterna entre lâminas leves e espada pesada, reiniciando tempos de recarga.", "thunderstorm")
      ),
      combos = listOf(
        ComboStep(1, "Silêncio na Forma Leve", "Use adaga e dois avanços para acumular 5 marcas e silenciar o oponente."),
        ComboStep(2, "Supremo + Empurrão Pesado", "Mude para a espada pesada e use H2 para empurrar o alvo sem chance de reação.")
      ),
      proTips = listOf(
        ProTip("Redução de Dano", "Ao carregar a H1 da espada pesada, Mulan reduz todo o dano sofrido em 40%.")
      ),
      shortTip = "Aplique o silêncio na postura rápida de duas lâminas antes de mudar para a espada pesada para garantir o combo completo."
    ),

    // 6. CHARLOTTE
    ChampionFactory.makeHero(
      id = "charlotte",
      name = "Charlotte",
      title = "A Rosa Espadachim",
      heroClass = "Lutadora",
      lane = Lane.TOP,
      tier = HeroTier.S,
      gtimgId = 536,
      difficultyStars = 3,
      winRate = "52.9%",
      winRateChange = "+1.3%",
      pickRate = "14.2%",
      banRate = "13.6%",
      coreItemSummary = "Machado Tormento + Clamor de Gelo",
      buildSubtitle = "Sequência de esgrima com redução brutal de velocidade de ataque do adversário.",
      spellName = "Golpe de Fagulha",
      spellDescription = "Garante reposicionamento para encaixar a estocada da Rosa de Sete Estrelas.",
      items = listOf(
        EquipmentItem("ch_1", "Botas de Resistência", "Movimento", "Tenacidade", "Tenacidade +35%", "snowshoeing"),
        EquipmentItem("ch_2", "Machado do Tormento", "Ataque", "Dano e CDR", "+85 Dano • 15% CDR", "gavel"),
        EquipmentItem("ch_3", "Clamor de Gelo", "Defesa", "Armadura e Lentidão", "+360 Defesa", "shield"),
        EquipmentItem("ch_4", "Armadura Espinhosa", "Defesa", "Anti-AD", "+400 Armadura", "shield"),
        EquipmentItem("ch_5", "Quebra-Estrelas", "Ataque", "Penetração", "+40% Pen. Física", "gavel"),
        EquipmentItem("ch_6", "Lâmina Sábia", "Defesa", "Ressurreição", "+100 Armadura", "shield")
      ),
      skills = listOf(
        SkillInfo("Rosa de Sete Estrelas", "Passiva", "ESTOCADA CRUCIAL", "Após 3 habilidades, o próximo ataque básico se transforma em estocadas velozes com dano percentual.", "radar"),
        SkillInfo("Lâmina Triangular (H1)", "Habilidade 1", "VELOCIDADE E PROJÉTIL", "Dispara projétil que acelera Charlotte ao atingir heróis.", "waves"),
        SkillInfo("Estocada Fulminante (H2)", "Habilidade 2", "AVANÇO E CURA", "Avança desferindo múltiplos golpes e curando sua vida.", "waves", isMaxPriority = true),
        SkillInfo("Glória da Rosa", "Supremo", "DESENHO NO CHÃO", "Desenha uma estrela no solo; a área arremessa todos os alvos e concede imunidade a controle.", "thunderstorm")
      ),
      combos = listOf(
        ComboStep(1, "H1 no Ar + H2 Avanço", "Acerte a H1 de longe para acelerar e avance com a H2 para acumular marcas."),
        ComboStep(2, "Supremo + Passiva", "Use o Supremo para arremessar e execute a Rosa de Sete Estrelas no alvo caído.")
      ),
      proTips = listOf(
        ProTip("Anti-Atiradores", "As estocadas da passiva reduzem a velocidade de ataque dos atiradores inimigos drasticamente.")
      ),
      shortTip = "Encadeie habilidades continuamente para manter a passiva das Sete Estrelas ativa, esmagando a velocidade de ataque de inimigos como Hou Yi e Marco Polo."
    ),

    // 7. ALLAIN
    ChampionFactory.makeHero(
      id = "allain",
      name = "Allain",
      title = "O Lâmina Negra",
      heroClass = "Lutador / Assassino",
      lane = Lane.TOP,
      tier = HeroTier.S,
      gtimgId = 563,
      difficultyStars = 3,
      winRate = "53.1%",
      winRateChange = "+0.7%",
      pickRate = "16.1%",
      banRate = "21.4%",
      coreItemSummary = "Lâmina Relâmpago + Sombra da Noite",
      buildSubtitle = "Dano triplo (Físico, Mágico e Dano Real) com invulnerabilidade absoluta no Supremo.",
      spellName = "Golpe de Fagulha",
      spellDescription = "Permite alcançar oponentes em retirada para trancar com o Supremo invulnerável.",
      items = listOf(
        EquipmentItem("al_1", "Botas de Resistência", "Movimento", "Tenacidade", "Tenacidade +35%", "snowshoeing"),
        EquipmentItem("al_2", "Lâmina Relâmpago", "Ataque", "Velocidade e Raio", "+35% Vel. Atq • +8% Vel. Mov.", "gavel"),
        EquipmentItem("al_3", "Lâmina Pura", "Ataque", "Dano e crítico", "+100 AD • +10% CDR", "gavel"),
        EquipmentItem("al_4", "Clamor de Gelo", "Defesa", "Vida e lentidão", "+360 Defesa", "shield"),
        EquipmentItem("al_5", "Quebra-Estrelas", "Ataque", "Penetração", "+40% Pen. Física", "gavel"),
        EquipmentItem("al_6", "Lâmina Sábia", "Defesa", "Ressurreição", "+100 Armadura", "shield")
      ),
      skills = listOf(
        SkillInfo("Espadas Cruzadas", "Passiva", "DANO HÍBRIDO", "Gera acúmulos que convertem seus golpes em dano físico, mágico e dano real puro.", "radar"),
        SkillInfo("Rajada de Cortes (H1)", "Habilidade 1", "CORTE IMÓVEL", "Desfere até 16 cortes rápidos e arremessa o inimigo no final.", "waves", isMaxPriority = true),
        SkillInfo("Avanço Relâmpago (H2)", "Habilidade 2", "DESLOQUE E LENTIDÃO", "Avança rapidamente até 2 vezes prendendo os inimigos com lentidão.", "autorenew"),
        SkillInfo("Queda do Julgamento", "Supremo", "INVULNERABILIDADE", "Tranca em um herói e salta para os céus ficando 100% invulnerável até despencar.", "thunderstorm")
      ),
      combos = listOf(
        ComboStep(1, "Trancar no ADC com Supremo", "Ative o Supremo no atirador inimigo ficando imune a todo dano."),
        ComboStep(2, "Queda + H2 + H1 Cheia", "Na queda, use H2 para travar e canalize a H1 com acúmulos máximos.")
      ),
      proTips = listOf(
        ProTip("Esquivar com o Supremo", "O Supremo de Allain o torna completamente invulnerável, podendo desviar de ultimates inimigos inteiros.")
      ),
      shortTip = "Use o Supremo para escapar de emboscadas ou trancar diretamente no atirador inimigo, pois você fica totalmente imune a dano durante o salto."
    ),

    // 8. DUN
    ChampionFactory.makeHero(
      id = "dun",
      name = "Dun",
      title = "O Olho Inflexível",
      heroClass = "Tank / Lutador",
      lane = Lane.TOP,
      tier = HeroTier.A,
      gtimgId = 126,
      difficultyStars = 2,
      winRate = "50.9%",
      winRateChange = "-0.1%",
      pickRate = "13.5%",
      banRate = "3.8%",
      coreItemSummary = "Manto Ardente + Clamor de Gelo",
      buildSubtitle = "Linha de frente de alta tenacidade com cura passiva extrema e garras de controle.",
      spellName = "Golpe de Fagulha",
      spellDescription = "Permite acertar a segunda parte do arremesso da H1 em alvos múltiplos.",
      items = listOf(
        EquipmentItem("dun_1", "Botas de Resistência", "Movimento", "Tenacidade", "Tenacidade +35%", "snowshoeing"),
        EquipmentItem("dun_2", "Manto Ardente", "Defesa", "Queima em área", "+1000 Vida • Dano de Fogo", "shield"),
        EquipmentItem("dun_3", "Clamor de Gelo", "Defesa", "Armadura e Lentidão", "+360 Defesa", "shield"),
        EquipmentItem("dun_4", "Olho da Fênix", "Defesa", "Amplifica cura passiva", "+240 Def. Mágica", "shield"),
        EquipmentItem("dun_5", "Presságio Ominoso", "Defesa", "Anti-AD", "+270 Defesa", "shield"),
        EquipmentItem("dun_6", "Lâmina Sábia", "Defesa", "Ressurreição", "+100 Armadura", "shield")
      ),
      skills = listOf(
        SkillInfo("Instinto de Batalha", "Passiva", "CURA SOBREVIVENTE", "Ao ficar com menos de 50% de vida, cada ataque restaura vida massivamente.", "radar"),
        SkillInfo("Lâmina de Vento (H1)", "Habilidade 1", "CORTE E ARREMESSO", "Corte frontal que libera uma segunda ativação de arremesso aéreo.", "waves", isMaxPriority = true),
        SkillInfo("Escudo Feroz (H2)", "Habilidade 2", "ESCUDO E DANO REAL", "Cria escudo baseado na vida máxima e concede dano real nos próximos 3 ataques.", "autorenew"),
        SkillInfo("Salto da Lâmina", "Supremo", "LANÇAMENTO E ATORDOAMENTO", "Arremessa a lâmina atordoando o alvo e puxando Dun até ele.", "thunderstorm")
      ),
      combos = listOf(
        ComboStep(1, "Supremo de Iniciação", "Atire a lâmina com o Supremo para atordoar e fechar distância."),
        ComboStep(2, "H2 Escudo + H1 Dupla", "Ative H2 para escudo e bata com H1 duas vezes para arremessar.")
      ),
      proTips = listOf(
        ProTip("Cura da Passiva", "Quando a passiva ativar, não fuja: continue batendo para regenerar quase toda a sua barra de vida.")
      ),
      shortTip = "Quando sua vida cair para 50%, continue atacando agressivamente: a passiva cura centenas de pontos de vida a cada golpe desferido."
    ),

    // 9. SUN CE
    ChampionFactory.makeHero(
      id = "sun_ce",
      name = "Sun Ce",
      title = "O Navegador das Marés",
      heroClass = "Tank / Lutador",
      lane = Lane.TOP,
      tier = HeroTier.A,
      gtimgId = 510,
      difficultyStars = 2,
      winRate = "51.4%",
      winRateChange = "+0.5%",
      pickRate = "11.2%",
      banRate = "4.6%",
      coreItemSummary = "Machado Tormento + Clamor de Gelo",
      buildSubtitle = "Rotações em altíssima velocidade pelo mapa pilotando o barco com carona para aliados.",
      spellName = "Golpe de Fagulha",
      spellDescription = "Combina com a Habilidade 1 para garantir o arremesso aéreo surpresa.",
      items = listOf(
        EquipmentItem("sc_1", "Botas de Resistência", "Movimento", "Tenacidade", "Tenacidade +35%", "snowshoeing"),
        EquipmentItem("sc_2", "Machado do Tormento", "Ataque", "Dano e CDR", "+85 Dano", "gavel"),
        EquipmentItem("sc_3", "Clamor de Gelo", "Defesa", "Vida e Armadura", "+360 Defesa", "shield"),
        EquipmentItem("sc_4", "Quebra-Estrelas", "Ataque", "Penetração", "+40% Pen. Física", "gavel"),
        EquipmentItem("sc_5", "Presságio Ominoso", "Defesa", "Anti-AD", "+270 Defesa", "shield"),
        EquipmentItem("sc_6", "Lâmina Sábia", "Defesa", "Ressurreição", "+100 Armadura", "shield")
      ),
      skills = listOf(
        SkillInfo("Armadura da Maré", "Passiva", "ARMADURA ACUMULATIVA", "Sofrer ou causar dano concede armadura adicional que se acumula até 10 vezes.", "radar"),
        SkillInfo("Impacto das Ondas (H1)", "Habilidade 1", "SALTO E ARREMESSO", "Avança e cria um geiser de água que arremessa inimigos após 1s.", "waves"),
        SkillInfo("Cortes da Maré (H2)", "Habilidade 2", "TRÊS CORTES", "Desfere 3 cortes fluídos acumulando lentidão nos inimigos.", "waves", isMaxPriority = true),
        SkillInfo("Navegação Tempestuosa", "Supremo", "BARCO E ROTAÇÃO", "Invoca um navio em alta velocidade, podendo levar 1 aliado de carona e colidir arremessando.", "thunderstorm")
      ),
      combos = listOf(
        ComboStep(1, "Barco em Rotação", "Inicie o Supremo do Top em direção ao Meio ou Dragão levando um aliado."),
        ComboStep(2, "Colisão + H1 + H2", "Pule do barco antes da colisão e encadeie H1 com os 3 cortes da H2.")
      ),
      proTips = listOf(
        ProTip("Pular do Barco", "Você pode pular do barco antes do impacto para que o navio continue sozinho atingindo a equipe inimiga.")
      ),
      shortTip = "Use o barco do Supremo para emboscar a rota do meio ou contestar objetivos instantaneamente cruzando o mapa inteiro em poucos segundos."
    ),

    // 10. GUAN YU
    ChampionFactory.makeHero(
      id = "guanyu",
      name = "Guan Yu",
      title = "O Santo da Guerra",
      heroClass = "Lutador / Tank",
      lane = Lane.TOP,
      tier = HeroTier.S,
      gtimgId = 140,
      difficultyStars = 5,
      winRate = "52.3%",
      winRateChange = "+0.8%",
      pickRate = "9.4%",
      banRate = "18.2%",
      coreItemSummary = "Machado Tormento + Clamor de Gelo",
      buildSubtitle = "Postura de cavalgada contínua empurrando formações inteiras para desorganizar lutas de equipe.",
      spellName = "Purificação",
      spellDescription = "Remove atordoamentos e lentidões para não perder a postura de galope.",
      items = listOf(
        EquipmentItem("gy_1", "Botas de Resistência", "Movimento", "Tenacidade", "Tenacidade +35%", "snowshoeing"),
        EquipmentItem("gy_2", "Machado do Tormento", "Ataque", "Dano e CDR", "+85 Dano", "gavel"),
        EquipmentItem("gy_3", "Clamor de Gelo", "Defesa", "Vida e Armadura", "+360 Defesa", "shield"),
        EquipmentItem("gy_4", "Quebra-Estrelas", "Ataque", "Penetração", "+40% Pen. Física", "gavel"),
        EquipmentItem("gy_5", "Olho da Fênix", "Defesa", "Defesa Mágica", "+240 Def. Mágica", "shield"),
        EquipmentItem("gy_6", "Lâmina Sábia", "Defesa", "Ressurreição", "+100 Armadura", "shield")
      ),
      skills = listOf(
        SkillInfo("Cavalgada Veloz", "Passiva", "POSTURA DE GALOPE", "Ao se mover continuamente por 100m, entra em modo de galope fortalecendo todas as habilidades.", "radar"),
        SkillInfo("Corte da Lâmina Crescente (H1)", "Habilidade 1", "CORTE DEVASTADOR", "No galope, desfere um golpe frontal cavalar de dano absurdo baseado na vida máxima.", "waves", isMaxPriority = true),
        SkillInfo("Passo do Corcel (H2)", "Habilidade 2", "PURIFICAÇÃO / SALTO", "No galope, salta empurrando alvos; no modo a pé, remove todos os efeitos de lentidão.", "autorenew"),
        SkillInfo("Exército de Ferro", "Supremo", "EMPURRÃO COLETIVO", "Invoca fileira de cavalos fantasmas e reduz a distância para entrar em galope em 50%.", "thunderstorm")
      ),
      combos = listOf(
        ComboStep(1, "Flanqueamento em Galope", "Venha por trás da equipe inimiga no galope para empurrá-los em direção ao seu time."),
        ComboStep(2, "Supremo + Galopes Sucessivos", "Ative o Supremo para galopar a cada 2 passos e empurrar continuamente os inimigos.")
      ),
      proTips = listOf(
        ProTip("Nunca Pare de Andar", "Mantenha o analógico sempre em movimento circular para não perder a barra de carga do cavalo.")
      ),
      shortTip = "Flanqueie os adversários pelas costas para empurrar o atirador e o mago inimigos diretamente para a linha de fogo da sua equipe."
    )
  )
}
