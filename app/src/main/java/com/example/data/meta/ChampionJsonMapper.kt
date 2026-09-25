package com.example.data.meta

import com.example.data.model.*
import java.util.Locale

/**
 * Converte os DTOs dos JSONs reais (servidor global) para o modelo de UI [Champion].
 * Heróis que já existem no MockMetaDatabase mantêm o guia detalhado escrito à mão;
 * aqui são geradas fichas completas (build/arcanas/combos) para os demais heróis.
 */
object ChampionJsonMapper {

    /** Normaliza slug/id para comparação (ex.: "mai-shiranui" == "mai_shiranui"). */
    private fun norm(key: String): String =
        key.lowercase(Locale.ROOT).replace(Regex("[^a-z0-9]"), "")

    fun matchesKey(a: String, b: String): Boolean = norm(a) == norm(b)

    /** Aliases EN (app) → chave PT usada nas imagens/builds da fonte PT-BR. */
    private val proAliases = mapOf(
        "annette" to "anette",
        "ukyo-tachibana" to "ukyo",
        "gao-changgong" to "gao",
        "mai-shiranui" to "mai",
        "luban-no-7" to "luban-n7",
        "mayene" to "mayenne",
        "lapulapu" to "lapu-lapu",
        "wang-zhaojun" to "zhaojun",
        "ao-yin" to "ao-yin",
        "shouyue" to "shoyue",
        "yango" to "yang-jian"
    )

    fun findProHero(
        proHeroes: Map<String, HoKProHeroJson>,
        slug: String,
        name: String
    ): HoKProHeroJson? {
        val alias = proAliases[slug] ?: slug
        proHeroes[alias]?.let { return it }
        proHeroes[slug]?.let { return it }
        val n = norm(name)
        return proHeroes.entries.firstOrNull { norm(it.key) == n || norm(it.value.name) == n }?.value
    }

    fun proHeroImageUrl(proHero: HoKProHeroJson?): String? =
        proHero?.file?.takeIf { it.isNotBlank() }
            ?.replace(Regex("\\.(jpe?g|png)$", RegexOption.IGNORE_CASE), ".webp")
            ?.let { "https://hokpro.gg/images/heroes/$it" }

    // --- Mapeamentos básicos ---

    /** Normaliza rótulos de rota/classe para o padrão do app (sem "Clash"/"Farm"). */
    private fun normalizeRolePt(raw: String): String {
        var s = raw
        val map = linkedMapOf(
            "Clash Lane" to "Top Lane",
            "Farm Lane" to "ADC Lane",
            "Clash" to "Top",
            "Farm" to "ADC",
            "Marksman" to "Atirador",
            "Roamer" to "Suporte",
            "Roam" to "Suporte",
            "Jungler" to "Caçador",
            "Jungle" to "Selva",
            "Mid Lane" to "Mid",
            "Mid" to "Mid",
            "Mage" to "Mago",
            "Fighter" to "Lutador",
            "Assassin" to "Assassino",
            "Support" to "Suporte",
            "Tank" to "Tanque"
        )
        map.forEach { (from, to) -> s = s.replace(from, to, ignoreCase = true) }
        return s
    }

    private fun laneFromCamp(laneName: String): Lane {
        val s = laneName.lowercase(Locale.ROOT)
        return when {
            // EN ou PT-BR (Camp global retorna PT quando language=pt-br)
            s.contains("clash") || s.contains("top") || s.contains("superior") || s.contains("confronto") -> Lane.TOP
            s.contains("jungle") || s.contains("caça") || s.contains("caca") || s.contains("selva") -> Lane.SELVA
            s.contains("mid") || s.contains("meio") -> Lane.MEIO
            s.contains("farm") || s.contains("adc") || s.contains("marksman") || s.contains("inferior") || s.contains("atirador") -> Lane.ADC
            else -> Lane.SUPORTE // roam/apoio/suporte
        }
    }

    private fun skillsFromCamp(camp: CampHeroJson): List<SkillInfo> {
        var slotIndex = 0
        return camp.skills.mapIndexed { i, s ->
            val subtitle = when {
                s.isPassive -> "Habilidade Passiva"
                s.isUlt -> "Supremo"
                else -> "Habilidade ${++slotIndex}"
            }
            SkillInfo(
                name = s.name,
                subtitle = subtitle,
                tag = s.tags.joinToString(" • ").ifEmpty { subtitle },
                description = s.description,
                iconSymbol = when {
                    s.isPassive -> "auto_awesome"
                    s.isUlt -> "star"
                    else -> "bolt"
                },
                isMaxPriority = s.isUlt
            )
        }
    }

    private fun laneFor(hero: HeroJson): Lane {
        val all = (hero.roles + listOfNotNull(hero.classType) + hero.subclasses)
            .joinToString(" ").lowercase(Locale.ROOT)
        return when {
            all.contains("clash") -> Lane.TOP
            all.contains("jungle") -> Lane.SELVA
            all.contains("mid") || all.contains("mage") && !all.contains("roam") -> Lane.MEIO
            all.contains("farm") || all.contains("marksman") -> Lane.ADC
            all.contains("roam") || all.contains("support") -> Lane.SUPORTE
            all.contains("assassin") -> Lane.SELVA
            all.contains("tank") -> Lane.TOP
            else -> Lane.TOP
        }
    }

    private fun tierFor(key: String?): HeroTier = when (key?.lowercase(Locale.ROOT)) {
        "ss" -> HeroTier.SS
        "s" -> HeroTier.S
        "a" -> HeroTier.A
        "c" -> HeroTier.C
        else -> HeroTier.B
    }

    private fun fmtRate(v: Double): String = String.format(Locale.US, "%.1f%%", v)

    private fun iconForCategory(category: String): String = when (category.lowercase(Locale.ROOT)) {
        "attack" -> "gavel"
        "magic" -> "auto_fix_high"
        "defense" -> "shield"
        "movement" -> "snowshoeing"
        "jungle" -> "bolt"
        "roam", "support" -> "favorite"
        else -> "shield"
    }

    private fun skillSubtitle(slot: String, order: Int): String = when (slot.lowercase(Locale.ROOT)) {
        "passive" -> "Habilidade Passiva"
        "ultimate" -> "Supremo"
        else -> "Habilidade ${order.coerceIn(1, 3)}"
    }

    private fun skillIcon(slot: String): String = when (slot.lowercase(Locale.ROOT)) {
        "passive" -> "auto_awesome"
        "ultimate" -> "star"
        else -> "bolt"
    }

    // --- Conteúdo gerado para heróis sem guia manual ---

    private fun itemsFor(hero: HeroJson, items: List<ItemJson>): List<EquipmentItem> {
        val heroSlug = norm(hero.slug)
        val recommended = items.filter { item ->
            item.usedBy.any { norm(it.slug) == heroSlug }
        }
        val isMage = (hero.classType ?: "").contains("mage", true) ||
            hero.roles.any { it.contains("mage", true) || hero.laneKeyIsMid() }
        val fallbackCategory = when {
            isMage -> "magic"
            (hero.classType ?: "").contains("tank", true) ||
                hero.roles.any { it.contains("tank", true) || it.contains("support", true) } -> "defense"
            else -> "attack"
        }
        val fallback = items.filter {
            it.category.equals(fallbackCategory, true) && it.isTopTier
        }
        return (recommended + fallback)
            .distinctBy { it.itemId }
            .take(6)
            .map { item ->
                EquipmentItem(
                    id = item.itemId,
                    name = item.name,
                    category = item.category,
                    description = item.description ?: item.tagline ?: "Item de ${item.category.lowercase(Locale.ROOT)}",
                    stats = "${item.price} Ouro",
                    iconSymbol = iconForCategory(item.category),
                    imageUrl = item.itemId.takeIf { it.isNotBlank() }
                        ?.let { "https://hokstats.gg/items/$it.png" }
                )
            }
    }

    private fun HeroJson.laneKeyIsMid(): Boolean =
        roles.any { it.equals("mid", true) }

    private fun arcanaPresetFor(hero: HeroJson): Pair<List<ArcanaItem>, List<Pair<String, String>>> {
        val cls = (hero.classType ?: "").lowercase(Locale.ROOT)
        return when {
            cls.contains("mage") ->
                ChampionFactory.buildMagicalPenArcanas() to ChampionFactory.standardMagicalPenSummary
            cls.contains("tank") || cls.contains("support") ->
                ChampionFactory.buildTankArcanas() to ChampionFactory.standardTankSummary
            cls.contains("marksman") ->
                ChampionFactory.buildCritArcanas() to ChampionFactory.standardCritSummary
            else ->
                ChampionFactory.buildPhysicalPenArcanas() to ChampionFactory.standardPhysicalPenSummary
        }
    }

    private fun skillsFor(hero: HeroJson): List<SkillInfo> {
        if (hero.skills.isEmpty()) {
            return listOf(
                SkillInfo(
                    name = "Kit de ${hero.name}",
                    subtitle = "Habilidades",
                    tag = "Passiva / 1 / 2 / Supremo",
                    description = hero.summary ?: "Detalhes de habilidades em atualização.",
                    iconSymbol = "auto_awesome"
                )
            )
        }
        return hero.skills.map { s ->
            SkillInfo(
                name = s.name,
                subtitle = skillSubtitle(s.slot, s.orderIdx),
                tag = s.slot.replaceFirstChar { it.uppercase(Locale.ROOT) },
                description = s.description,
                iconSymbol = skillIcon(s.slot),
                isMaxPriority = s.slot.equals("ultimate", true)
            )
        }
    }

    private fun combosFor(hero: HeroJson, skills: List<SkillInfo>): List<ComboStep> {
        val dmgSkills = hero.skills.filter { !it.slot.equals("passive", true) }
        if (dmgSkills.isEmpty()) return listOf(
            ComboStep(1, "Combo básico", "Engaje com sua habilidade principal, finalize com o Supremo e recue com segurança.")
        )
        return dmgSkills.mapIndexed { i, s ->
            ComboStep(
                stepNumber = i + 1,
                title = s.name,
                description = if (i == dmgSkills.lastIndex && s.slot.equals("ultimate", true))
                    "Finalize com o Supremo para fechar o combo."
                else "Use ${s.name} para preparar o próximo passo do combo."
            )
        }
    }

    fun toChampion(
        hero: HeroJson,
        items: List<ItemJson>,
        arcanas: List<ArcanaJson>,
        /** Override remoto de tier (ex.: tier list BR do HOK Pro). */
        tierOverride: HeroTier? = null,
        ratesOverride: HeroRateJson? = null,
        /** Dados oficiais do HoK Camp global (skills e WR ao vivo). */
        camp: CampHeroJson? = null,
        /** Build real PT-BR do HOK Pro (itens/arcanas/feitiço usados pelos pros). */
        proBuild: HoKProBuildJson? = null,
        proHero: HoKProHeroJson? = null,
        /** Pool global nome PT -> imagem, montado das builds reais, para situacionais. */
        itemPoolPt: Map<String, String> = emptyMap(),
        /** Dificuldade real do herói (1-5) extraída da base global. */
        difficultyReal: Int? = null,
        /** Counters/sinergias/forte-contra reais da fonte da comunidade BR. */
        countersData: CountersJson? = null,
        /** Build custom do painel admin (nomes de itens/arcanas em PT-BR). */
        customBuild: CustomBuildJson? = null,
        /** Imagem do item por nome PT (pool das builds reais). */
        ptItemImage: (String) -> String? = { null }
    ): Champion {
        val lane = camp?.lane?.takeIf { it.isNotBlank() }?.let { laneFromCamp(it) } ?: laneFor(hero)
        val campBase = camp?.baseData
        val rate = (ratesOverride?.let { listOf(it) + hero.rates } ?: hero.rates)
            .maxByOrNull { it.pickRate } ?: HeroRateJson()
        val winRateStr = campBase?.winRate?.takeIf { it.isNotBlank() } ?: fmtRate(rate.winRate)
        val pickRateStr = campBase?.matchRate?.takeIf { it.isNotBlank() } ?: fmtRate(rate.pickRate)
        val banRateStr = campBase?.banRate?.takeIf { it.isNotBlank() } ?: fmtRate(rate.banRate)
        val wrValue = winRateStr.removeSuffix("%").replace(",", ".").toDoubleOrNull() ?: rate.winRate
        val banValue = banRateStr.removeSuffix("%").replace(",", ".").toDoubleOrNull() ?: rate.banRate
        val (arcanasPreset, arcanasSummary) = arcanaPresetFor(hero)
        val skills = if (camp != null && camp.skills.isNotEmpty()) skillsFromCamp(camp) else skillsFor(hero)
        val campTier = camp?.hot?.takeIf { it.isNotBlank() }?.let { tierFor(it) }
        val isMage = (hero.classType ?: "").contains("mage", true)

        // Arcanas reais do banco quando disponíveis; senão preset genérico
        val realArcanas = if (arcanas.isNotEmpty()) {
            val by = { c: String -> arcanas.firstOrNull { it.color.equals(c, true) } }
            listOfNotNull(by("Red"), by("Blue"), by("Green")).map {
                ArcanaItem(it.name, 10, it.color.uppercase(Locale.ROOT), it.stats, it.stats)
            }.takeIf { it.size == 3 } ?: arcanasPreset
        } else arcanasPreset

        val roleLabel = normalizeRolePt(
            hero.roles.joinToString(" / ").ifEmpty { hero.classType ?: "Herói" }
        )

        // Itens situacionais: manual do painel admin tem prioridade; senão, base real usedBy
        fun situationalFor(): List<SituationalItem> {
            // 1) Manual do painel admin (nome PT-BR → imagem real)
            customBuild?.situational?.takeIf { it.isNotEmpty() }?.let { manual ->
                return manual.map { nome ->
                    SituationalItem(
                        name = nome.replaceFirstChar { it.uppercase(Locale.ROOT) },
                        reason = "Situacional definido pelo admin",
                        tag = "Situacional",
                        imageUrl = ptItemImage(nome)
                    )
                }
            }
            // 2) Fallback: itens defensivos universais reais (com imagem oficial)
            return listOf(
                SituationalItem("Lâmina Sábia", "Ressurreição em lutas decisivas", "Sobrevivência", "https://hokstats.gg/items/1337.png"),
                SituationalItem("Presságio Ominoso", "Contra excesso de dano físico", "Anti-AD", "https://hokstats.gg/items/1333.png")
            )
        }

        // Build/arcanas/feitiço REAIS do HOK Pro (PT-BR) ou custom do painel admin
        val rawProItems = customBuild?.items?.mapIndexed { idx, nome ->
            HoKProEntry(nome = nome, id = ptItemImage(nome)?.substringAfterLast("/api/image/") ?: "")
        } ?: proBuild?.itens ?: emptyList()
        val rawArcanas = customBuild?.arcanas?.map { nome ->
            val qtd = nome.substringBefore("x").trim().toIntOrNull() ?: 10
            HoKProArcana(nome = nome.substringAfter("x").trim(), qtd = qtd, id = ptItemImage(nome.substringAfter("x").trim())?.substringAfterLast("/api/image/") ?: "")
        } ?: proBuild?.arcanas ?: emptyList()
        val rawSpell = customBuild?.spell?.takeIf { it.isNotBlank() }?.let { HoKProEntry(nome = it, id = ptItemImage(it)?.substringAfterLast("/api/image/") ?: "") } ?: proBuild?.feitico

        val proItems = rawProItems.mapIndexed { idx, it ->
            EquipmentItem(
                id = it.id,
                name = it.nome.replaceFirstChar { c -> c.uppercase(Locale.ROOT) },
                category = if (customBuild != null) "Custom Admin" else "Recomendado",
                description = "Item ${idx + 1} da build recomendada",
                stats = "Build Recomendada",
                iconSymbol = iconForCategory("attack"),
                imageUrl = it.id.takeIf { i -> i.isNotBlank() }?.let { i -> "https://hokpro.gg/api/image/$i" }
                    ?: ptItemImage(it.nome)
            )
        }
        val itemList = proItems.ifEmpty { itemsFor(hero, items) }
        val hasCustomOrProBuild = proItems.isNotEmpty()

        val proArcanas = rawArcanas.map {
            ArcanaItem(
                name = it.nome.replaceFirstChar { c -> c.uppercase(Locale.ROOT) },
                count = it.qtd,
                colorType = "PRO",
                perItemStat = "${it.qtd}x na build",
                totalHighlight = "Build recomendada da temporada",
                imageUrl = it.id.takeIf { i -> i.isNotBlank() }?.let { i -> "https://hokpro.gg/api/image/$i" }
            )
        }

        return Champion(
            id = hero.slug,
            name = hero.name,
            title = proHero?.title?.takeIf { it.isNotBlank() } ?: roleLabel,
            heroClass = normalizeRolePt(hero.classType ?: roleLabel),
            lane = lane,
            tier = tierOverride ?: campTier ?: tierFor(hero.tierKey),
            imageUrl = proHeroImageUrl(proHero)
                ?: camp?.icon?.takeIf { it.isNotBlank() }
                ?: hero.squareImage
                ?: hero.heroImgId?.let { "https://game.gtimg.cn/images/yxzj/img201606/heroimg/$it/$it.jpg" }
                ?: "",
            localDrawableRes = null,
            difficultyStars = (difficultyReal ?: hero.difficulty).coerceIn(1, 5),
            winRate = winRateStr,
            winRateChange = "±0.0%",
            isWinRatePositive = wrValue >= 50.0,
            pickRate = pickRateStr,
            banRate = banRateStr,
            isBanPriority = banValue >= 20.0,
            coreItemSummary = itemList.firstOrNull()?.name ?: "Build recomendada da temporada",
            buildTitle = if (proItems.isNotEmpty()) "Build Recomendada" else "Build Competitiva (Meta Global)",
            buildSubtitle = proBuild?.lane?.let { normalizeRolePt(it) } ?: roleLabel,
            spellName = rawSpell?.nome?.replaceFirstChar { it.uppercase(Locale.ROOT) }
                ?: if (lane == Lane.SELVA) "Punir (Smite)" else "Flash",
            spellSubtitle = "Feitiço Recomendado",
            spellDescription = if (proItems.isNotEmpty())
                "Feitiço recomendado para este herói na temporada atual."
            else if (lane == Lane.SELVA)
                "Feitiço obrigatório para caçadores: garante objetivos e dano em monstros da selva."
            else
                "Reposicionamento instantâneo para engajar, escapar ou ajustar a ultimate.",
            spellImageUrl = rawSpell?.id?.takeIf { it.isNotBlank() }
                ?.let { "https://hokpro.gg/api/image/$it" },
            items = itemList,
            counters = countersData?.counters?.map { MatchupInfo(it.name, it.role, it.effect) } ?: emptyList(),
            synergies = countersData?.synergies?.map { MatchupInfo(it.name, it.role, it.effect) } ?: emptyList(),
            strongAgainst = countersData?.strongAgainst?.map { MatchupInfo(it.name, it.role, it.effect) } ?: emptyList(),
            situationalItems = situationalFor(),
            arcanas = proArcanas.ifEmpty { realArcanas },
            arcanaStatsSummary = arcanasSummary,
            skillPriority = if (isMage) "Habilidade 1 > Habilidade 2 > Supremo" else "Supremo > Habilidade 1 > Habilidade 2",
            skills = skills,
            combos = combosFor(hero, skills),
            proTips = listOf(
                ProTip(
                    "Leitura de jogo",
                    "Jogue em torno dos picos de poder da sua build recomendada e controle as rotações do seu mapa."
                ),
                ProTip(
                    "Dica da temporada",
                    "WR atual de $winRateStr no servidor global. Priorize objetivos de equipe nas lutas tardias."
                )
            ),
            shortTip = "WR atual de $winRateStr com pick rate de $pickRateStr na temporada. Siga a ordem de itens recomendada e jogue ao redor dos picos de poder da sua ultimate."
        )
    }
}
