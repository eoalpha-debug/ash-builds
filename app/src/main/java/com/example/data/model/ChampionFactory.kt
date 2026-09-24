package com.example.data.model

import com.example.R

object ChampionFactory {

  fun buildPhysicalPenArcanas(): List<ArcanaItem> = listOf(
    ArcanaItem("Mutação", 10, "RED", "+2 Dano / +3.6 Perfuração", "+20 AD • +36 Perfuração"),
    ArcanaItem("Caçada", 10, "BLUE", "+1% Vel. Mov. / +1% Vel. Atq.", "+10% Vel. Mov. • +10% Vel. Atq."),
    ArcanaItem("Olho de Águia", 10, "GREEN", "+0.9 Dano / +6.4 Perfuração", "+9 AD • +64 Perfuração")
  )

  fun buildCritArcanas(): List<ArcanaItem> = listOf(
    ArcanaItem("Fúria", 10, "RED", "+1.6% Taxa Crítica / +3.6% Dano Crítico", "+16% Taxa Crítica • +36% Dano Crítico"),
    ArcanaItem("Caçada", 10, "BLUE", "+1% Vel. Mov. / +1% Vel. Atq.", "+10% Vel. Mov. • +10% Vel. Atq."),
    ArcanaItem("Olho de Águia", 10, "GREEN", "+0.9 Dano / +6.4 Perfuração", "+9 AD • +64 Perfuração")
  )

  fun buildMagicalPenArcanas(): List<ArcanaItem> = listOf(
    ArcanaItem("Pesadelo", 10, "RED", "+4.2 Poder Mágico / +2.4 Perfuração", "+42 AP • +24 Perf. Mágica"),
    ArcanaItem("Tributo", 10, "BLUE", "+2.4 Poder Mágico / +0.7% CDR", "+24 AP • +7% Redução Recarga"),
    ArcanaItem("Olho da Mente", 10, "GREEN", "+6.4 Perfuração Mágica", "+64 Perfuração Mágica")
  )

  fun buildTankArcanas(): List<ArcanaItem> = listOf(
    ArcanaItem("Destino", 10, "RED", "+33.7 Vida / +2.3 Defesa", "+337 Vida • +23 Defesa Física"),
    ArcanaItem("Harmonia", 10, "BLUE", "+45 Vida / +5.2 Regen Vida", "+450 Vida • +52 Regen / 5s"),
    ArcanaItem("Vazio", 10, "GREEN", "+37.5 Vida / +0.6% CDR", "+375 Vida • +6% Redução Recarga")
  )

  val standardPhysicalPenSummary = listOf(
    "Dano Físico" to "+29 AD",
    "Perfuração de Armadura" to "+100",
    "Velocidade de Movimento" to "+10%",
    "Velocidade de Ataque" to "+10%"
  )

  val standardCritSummary = listOf(
    "Taxa de Crítico" to "+16%",
    "Dano Crítico" to "+36%",
    "Perfuração de Armadura" to "+64",
    "Velocidade de Movimento" to "+10%"
  )

  val standardMagicalPenSummary = listOf(
    "Poder Mágico" to "+66 AP",
    "Perfuração Mágica" to "+88",
    "Redução de Recarga" to "+7%"
  )

  val standardTankSummary = listOf(
    "Vida Máxima" to "+1162 HP",
    "Defesa Física" to "+23",
    "Regeneração de Vida" to "+52/5s",
    "Redução de Recarga" to "+6%"
  )

  fun makeHero(
    id: String,
    name: String,
    title: String,
    heroClass: String,
    lane: Lane,
    tier: HeroTier,
    gtimgId: Int,
    localDrawableRes: Int? = null,
    difficultyStars: Int = 3,
    winRate: String,
    winRateChange: String,
    isWinRatePositive: Boolean = true,
    pickRate: String,
    banRate: String,
    isBanPriority: Boolean = false,
    coreItemSummary: String,
    buildTitle: String = "Build Competitiva (Meta Global)",
    buildSubtitle: String,
    spellName: String,
    spellSubtitle: String = "Recomendado",
    spellDescription: String,
    items: List<EquipmentItem>,
    situationalItems: List<SituationalItem> = listOf(
      SituationalItem("Lâmina Sábia", "Ressurreição em lutas decisivas", "Sobrevivência", "https://hokstats.gg/items/1337.png"),
      SituationalItem("Presságio Ominoso", "Contra excesso de dano físico", "Anti-AD", "https://hokstats.gg/items/1333.png")
    ),
    arcanas: List<ArcanaItem> = buildPhysicalPenArcanas(),
    arcanaStatsSummary: List<Pair<String, String>> = standardPhysicalPenSummary,
    skillPriority: String = "Habilidade 1 > Habilidade 2 > Supremo",
    skills: List<SkillInfo>,
    combos: List<ComboStep>,
    proTips: List<ProTip>,
    shortTip: String
  ): Champion {
    val officialImageUrl = "https://game.gtimg.cn/images/yxzj/img201606/heroimg/$gtimgId/$gtimgId.jpg"
    return Champion(
      id = id,
      name = name,
      title = title,
      heroClass = heroClass,
      lane = lane,
      tier = tier,
      imageUrl = officialImageUrl,
      localDrawableRes = localDrawableRes,
      difficultyStars = difficultyStars,
      winRate = winRate,
      winRateChange = winRateChange,
      isWinRatePositive = isWinRatePositive,
      pickRate = pickRate,
      banRate = banRate,
      isBanPriority = isBanPriority,
      coreItemSummary = coreItemSummary,
      buildTitle = buildTitle,
      buildSubtitle = buildSubtitle,
      spellName = spellName,
      spellSubtitle = spellSubtitle,
      spellDescription = spellDescription,
      items = items,
      situationalItems = situationalItems,
      arcanas = arcanas,
      arcanaStatsSummary = arcanaStatsSummary,
      skillPriority = skillPriority,
      skills = skills,
      combos = combos,
      proTips = proTips,
      shortTip = shortTip
    )
  }
}
