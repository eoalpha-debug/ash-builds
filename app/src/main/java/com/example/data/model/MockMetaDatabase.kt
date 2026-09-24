package com.example.data.model

object MockMetaDatabase {

  val patchNotices = listOf(
    PatchHeroNotice(
      heroName = "Sun Bin",
      lane = "SUPORTE",
      description = "Velocidade de movimento da Hab. 2 ampliada em 15%",
      isBuff = true
    ),
    PatchHeroNotice(
      heroName = "Luban nº 7",
      lane = "ADC",
      description = "Dano percentual da passiva fortalecido no início do jogo",
      isBuff = true
    ),
    PatchHeroNotice(
      heroName = "Princesa Gélida",
      lane = "MID",
      description = "Tempo de recarga do congelamento aumentado em 2.5s",
      isBuff = false
    ),
    PatchHeroNotice(
      heroName = "Kaizer",
      lane = "TOP / SELVA",
      description = "Armadura bônus da transformação suprema reduzida",
      isBuff = false
    )
  )

  val allChampions: List<Champion> =
    ClashLaneChampions.list +
    JungleChampions.list +
    MidLaneChampions.list +
    FarmLaneChampions.list +
    RoamLaneChampions.list

  fun getChampionById(id: String): Champion? {
    return allChampions.find { it.id.equals(id, ignoreCase = true) }
  }

  fun getChampionsByLane(lane: Lane): List<Champion> {
    if (lane == Lane.TODAS) return allChampions
    return allChampions.filter { it.lane == lane }
  }

  fun getChampionsByTier(tier: HeroTier): List<Champion> {
    return allChampions.filter { it.tier == tier }
  }
}
