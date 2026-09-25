package com.example.data.meta

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * DTOs que espelham os JSONs embarcados em assets/meta/
 * (dados reais do servidor global, derivados do HoK Camp oficial).
 */

@JsonClass(generateAdapter = true)
data class HeroJson(
    val slug: String,
    val name: String,
    val tierKey: String? = null,
    val tier: String? = null,
    val roles: List<String> = emptyList(),
    val difficulty: Int = 3,
    val classType: String? = null,
    val subclasses: List<String> = emptyList(),
    val splashImage: String? = null,
    val heroImgId: String? = null,
    val squareImage: String? = null,
    val summary: String? = null,
    val skills: List<HeroSkillJson> = emptyList(),
    val rates: List<HeroRateJson> = emptyList()
)

@JsonClass(generateAdapter = true)
data class HeroSkillJson(
    val slot: String = "",
    val name: String = "",
    val description: String = "",
    @Json(name = "orderIdx") val orderIdx: Int = 0
)

@JsonClass(generateAdapter = true)
data class HeroRateJson(
    val role: String = "",
    val winRate: Double = 0.0,
    val pickRate: Double = 0.0,
    val banRate: Double = 0.0
)

@JsonClass(generateAdapter = true)
data class ItemJson(
    val itemId: String = "",
    val name: String = "",
    val image: String? = null,
    val price: Int = 0,
    val tier: Int = 3,
    val category: String = "",
    val tagline: String? = null,
    val isTopTier: Boolean = false,
    val description: String? = null,
    val usedBy: List<ItemUserJson> = emptyList()
)

@JsonClass(generateAdapter = true)
data class ItemUserJson(
    val slug: String = "",
    val name: String = "",
    val role: String? = null
)

@JsonClass(generateAdapter = true)
data class ArcanaJson(
    val arcanaId: String = "",
    val name: String = "",
    val level: Int = 5,
    val color: String = "",
    val stats: String = "",
    val image: String? = null
)

@JsonClass(generateAdapter = true)
data class PatchJson(
    val patchDate: String = "",
    val season: String = "",
    val heroCount: Int = 0,
    val title: String = "",
    val changes: List<PatchChangeJson> = emptyList()
)

@JsonClass(generateAdapter = true)
data class PatchChangeJson(
    val heroSlug: String = "",
    val heroName: String = "",
    val changeType: String = "",
    val summary: String = "",
    val details: String = ""
)

// ---- Snapshot oficial do HoK Camp global (gerado por tools/sync_camp_full.py) ----

@JsonClass(generateAdapter = true)
data class CampHeroJson(
    val heroId: Int = 0,
    val slug: String = "",
    val name: String = "",
    val icon: String = "",
    val roles: List<String> = emptyList(),
    val lane: String = "",
    val hot: String = "",
    val baseData: CampBaseData = CampBaseData(),
    val skills: List<CampSkillJson> = emptyList()
)

@JsonClass(generateAdapter = true)
data class CampBaseData(
    val winRate: String = "",
    val matchRate: String = "",
    val banRate: String = ""
)

@JsonClass(generateAdapter = true)
data class CampSkillJson(
    val name: String = "",
    val description: String = "",
    val icon: String = "",
    val isPassive: Boolean = false,
    val isUlt: Boolean = false,
    val tags: List<String> = emptyList()
)

@JsonClass(generateAdapter = true)
data class CampPatchJson(
    val season: String = "",
    val date: String = "",
    val changes: List<CampPatchChange> = emptyList()
)

@JsonClass(generateAdapter = true)
data class CampPatchChange(
    val heroName: String = "",
    val changeType: String = "",
    val summary: String = ""
)

// ---- Builds reais (PT-BR) do HOK Pro, embed como meta/hokpro_builds.json ----

@JsonClass(generateAdapter = true)
data class HoKProBuildJson(
    val lane: String = "",
    val itens: List<HoKProEntry> = emptyList(),
    val arcanas: List<HoKProArcana> = emptyList(),
    val feitico: HoKProEntry = HoKProEntry()
)

@JsonClass(generateAdapter = true)
data class HoKProEntry(
    val nome: String = "",
    val id: String = ""
)

@JsonClass(generateAdapter = true)
data class HoKProArcana(
    val nome: String = "",
    val qtd: Int = 10,
    val id: String = ""
)

/** Imagens e títulos PT-BR dos heróis (meta/hokpro_heroes.json). */
@JsonClass(generateAdapter = true)
data class HoKProHeroJson(
    val file: String = "",
    val name: String = "",
    val title: String = ""
)

/** Dificuldade real (1-5) extraída da base global (meta/dificuldade_reai.json). */
@JsonClass(generateAdapter = true)
data class DifficultyJson(val difficulty: Int = 3)

/** Heróis novos que a API em inglês ainda não lista (nome PT → slug EN do app). */
val CAMP_PT_SLUG_ALIASES = mapOf(
    "ser-do-fluxo-mago" to "flowborn-mage",
    "ser-do-fluxo-tanque" to "flowborn-tank",
    "ser-do-fluxo-atirador" to "flowborn-marksman",
    "ser-do-fluxo-assassino" to "flowborn-assassin",
    "ser-do-fluxo-apoio" to "flowborn-roamer",
    "agu" to "agudo",
    "zhaojun" to "wang-zhaojun",
    "consorte-yu" to "consort-yu",
    "changgong" to "gao-changgong"
)

/** Nomes em EN para heróis que só existem em PT no Camp (quando ausentes do heroes.json). */
val SYNTH_HERO_NAMES = mapOf(
    "yuan-ge" to "Yuan Ge",
    "flowborn-assassin" to "Flowborn (Assassin)",
    "flowborn-roamer" to "Flowborn (Roamer)"
)

/** Counters/sinergias reais (meta/counters.json). */
@JsonClass(generateAdapter = true)
data class CountersJson(
    val counters: List<MatchupJson> = emptyList(),
    val synergies: List<MatchupJson> = emptyList(),
    val strongAgainst: List<MatchupJson> = emptyList()
)

@JsonClass(generateAdapter = true)
data class MatchupJson(
    val name: String = "",
    val role: String = "",
    val effect: Int = 0
)

/** Overrides manuais do painel admin (remoto). */
@JsonClass(generateAdapter = true)
data class AdminOverridesJson(
    val version: Int = 2,
    val featuredHero: String = "",
    val tierOverrides: Map<String, String> = emptyMap(),
    val customBuilds: Map<String, CustomBuildJson> = emptyMap(),
    val bannerMessage: String = "",
    /** Avisos/mudanças criados manualmente no painel. */
    val patchExtras: List<PatchNoticeJson> = emptyList(),
    /** Nomes de heróis ocultos dos avisos automáticos. */
    val patchHidden: List<String> = emptyList(),
    /** Slugs de campeões removidos do catálogo. */
    val hiddenChampions: List<String> = emptyList(),
    /** Stats manuais (WR/PR/BR) por slug. */
    val statOverrides: Map<String, StatOverrideJson> = emptyMap(),
    /** Título/subtítulo manual por slug (ex.: "A Rainha Mecânica"). */
    val titleOverrides: Map<String, String> = emptyMap(),
    /** URL de publicação salva pelo painel (ignorada no app). */
    val url: String = "",
    val updatedAt: String = ""
)

@JsonClass(generateAdapter = true)
data class CustomBuildJson(
    val items: List<String> = emptyList(),
    val arcanas: List<String> = emptyList(),
    val spell: String = "",
    /** Itens situacionais manuais (nomes PT-BR). */
    val situational: List<String> = emptyList()
)

@JsonClass(generateAdapter = true)
data class PatchNoticeJson(
    val heroName: String = "",
    val kind: String = "REAJUSTE",
    val summary: String = "",
    val details: String = ""
)

@JsonClass(generateAdapter = true)
data class StatOverrideJson(
    val winRate: String = "",
    val pickRate: String = "",
    val banRate: String = ""
)
