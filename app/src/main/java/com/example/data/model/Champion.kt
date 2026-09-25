package com.example.data.model

import androidx.annotation.DrawableRes

enum class Lane(val labelPt: String, val chipShort: String, val iconName: String) {
  TODAS("Todas as Rotas", "Todas", "stars"),
  TOP("Rota do Topo (Top)", "Top", "shield"),
  SELVA("Selva (Jungle)", "Selva", "forest"),
  MEIO("Rota do Meio (Mid)", "Mid", "auto_fix_high"),
  ADC("Rota do Atirador (ADC)", "ADC", "adjust"),
  SUPORTE("Suporte (Roaming)", "Suporte", "favorite")
}

enum class HeroTier(val badge: String, val titlePt: String, val descriptionPt: String) {
  SS("SS", "Dominantes", "Ban quase obrigatório ou prioridade total no draft"),
  S("S", "Muito Fortes no Meta", "Excelente consistência e impacto decisivo"),
  A("A", "Escolhas Sólidas e Balanceadas", "Eficientes em composições padronizadas"),
  B("B", "Situacionais / Requer Sinergia", "Dependem de counter-picks específicos ou comps dedicadas"),
  C("C", "Fora do Meta", "Escolhas fracas no patch atual — jogue apenas com maestria")
}

data class EquipmentItem(
  val id: String,
  val name: String,
  val category: String,
  val description: String,
  val stats: String,
  val iconSymbol: String = "shield",
  val imageUrl: String? = null
)

data class SituationalItem(
  val name: String,
  val reason: String,
  val tag: String,
  val imageUrl: String? = null
)

data class ArcanaItem(
  val name: String,
  val count: Int,
  val colorType: String,
  val perItemStat: String,
  val totalHighlight: String,
  val imageUrl: String? = null
)

data class MatchupInfo(
  val name: String,
  val role: String,
  val effectiveness: Int
)

data class SkillInfo(
  val name: String,
  val subtitle: String,
  val tag: String,
  val description: String,
  val iconSymbol: String,
  val isMaxPriority: Boolean = false
)

data class ComboStep(
  val stepNumber: Int,
  val title: String,
  val description: String
)

data class ProTip(
  val title: String,
  val description: String
)

data class PatchHeroNotice(
  val heroName: String,
  val lane: String,
  val description: String,
  val isBuff: Boolean,
  /** BUFF | NERF | REAJUSTE (balanceamento de atributos/mecânica) */
  val kind: String = if (isBuff) "BUFF" else "NERF",
  /** Texto completo da mudança para o card de detalhes. */
  val details: String = ""
)

data class Champion(
  val id: String,
  val name: String,
  val title: String,
  val heroClass: String,
  val lane: Lane,
  val tier: HeroTier,
  val imageUrl: String,
  @DrawableRes val localDrawableRes: Int? = null,
  val difficultyStars: Int,
  val winRate: String,
  val winRateChange: String,
  val isWinRatePositive: Boolean,
  val pickRate: String,
  val banRate: String,
  val isBanPriority: Boolean = false,
  val coreItemSummary: String,
  val buildTitle: String,
  val buildSubtitle: String,
  val spellName: String,
  val spellSubtitle: String,
  val spellDescription: String,
  val spellImageUrl: String? = null,
  val counters: List<MatchupInfo> = emptyList(),
  val synergies: List<MatchupInfo> = emptyList(),
  val strongAgainst: List<MatchupInfo> = emptyList(),
  /** Pro player que validou a build (nome/time BR). */
  val proPlayerName: String = "",
  val proPlayerTeam: String = "",
  val items: List<EquipmentItem>,
  val situationalItems: List<SituationalItem>,
  val arcanas: List<ArcanaItem>,
  val arcanaStatsSummary: List<Pair<String, String>>,
  val skillPriority: String,
  val skills: List<SkillInfo>,
  val combos: List<ComboStep>,
  val proTips: List<ProTip>,
  val shortTip: String
)



