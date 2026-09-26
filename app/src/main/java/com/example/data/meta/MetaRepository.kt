package com.example.data.meta

import android.content.Context
import com.example.data.local.AppDatabase
import com.example.data.local.MetaCacheDao
import com.example.data.local.MetaCacheEntity
import com.example.data.model.Champion
import com.example.data.model.HeroTier
import com.example.data.model.Lane
import com.example.data.model.MatchupInfo
import com.example.data.model.MockMetaDatabase
import com.example.data.model.PatchHeroNotice
import com.example.data.remote.RemoteModule
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale

/**
 * Camada Ãºnica de dados do meta. Ordem de fallback:
 *   1. Cache Room (Ãºltimo sync remoto bem-sucedido)
 *   2. Assets embarcados com dados reais (assets/meta â€” JSONs do HoK Camp global)
 *   3. MockMetaDatabase (guia detalhado escrito Ã  mÃ£o â€” nunca quebra offline)
 */
class MetaRepository private constructor(context: Context) {

    private val appContext = context.applicationContext
    private val moshi = Moshi.Builder().build()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val cacheDao: MetaCacheDao = AppDatabase.getDatabase(appContext).metaCacheDao()
    private val slugNormRegex = Regex("[^a-z0-9]")
    private fun slugNorm(s: String) = s.lowercase(java.util.Locale.ROOT).replace(slugNormRegex, "")

    private val _champions = MutableStateFlow<List<Champion>>(emptyList())
    val champions: StateFlow<List<Champion>> = _champions.asStateFlow()

    private val _patchNotices = MutableStateFlow<List<PatchHeroNotice>>(MockMetaDatabase.patchNotices)
    val patchNotices: StateFlow<List<PatchHeroNotice>> = _patchNotices.asStateFlow()

    private val _patchLabel = MutableStateFlow("Patch atual")
    val patchLabel: StateFlow<String> = _patchLabel.asStateFlow()

    private val _featuredHeroId = MutableStateFlow<String?>(null)
    val featuredHeroId: StateFlow<String?> = _featuredHeroId.asStateFlow()

    private val _bannerMessage = MutableStateFlow<String?>(null)
    val bannerMessage: StateFlow<String?> = _bannerMessage.asStateFlow()

    private val _allItems = MutableStateFlow<List<ItemJson>>(emptyList())
    val allItems: StateFlow<List<ItemJson>> = _allItems.asStateFlow()

    /** Overrides do painel admin carregados no momento. */
    private var adminOverrides: AdminOverridesJson? = null

    private val _lastSyncAt = MutableStateFlow<Long?>(null)
    val lastSyncAt: StateFlow<Long?> = _lastSyncAt.asStateFlow()

    /** Snapshot vindo do backend do Camp (opcional): slug -> (tier, rates). */
    private var campSnapshot: Map<String, SnapshotEntry> = emptyMap()

    /** Histórico de WR pré-carregado (nome normalizado -> datas × valores). Lido 1x no loadBase. */
    private var wrHistoryByName: Map<String, List<Pair<String, Double>>> = emptyMap()

    @com.squareup.moshi.JsonClass(generateAdapter = true)
    data class SnapshotEntry(val tier: String?, val winRate: Double, val pickRate: Double, val banRate: Double)

    init {
        // Mostra o catálogo mock instantaneamente; os dados reais substituem em seguida
        _champions.value = MockMetaDatabase.allChampions
        scope.launch { loadBase() }
    }

    // ---------------- Carregamento base (assets + mock) ----------------

    private suspend fun loadBase() {
        val heroes = readJsonList<HeroJson>("meta/heroes.json", HeroJson::class.java)
        val items = readJsonList<ItemJson>("meta/items.json", ItemJson::class.java)
        _allItems.value = items
        val arcanas = readJsonList<ArcanaJson>("meta/arcana.json", ArcanaJson::class.java)
        val patches = readJsonList<PatchJson>("meta/patches.json", PatchJson::class.java)
        val campFull = readJsonList<CampHeroJson>("meta/camp_full.json", CampHeroJson::class.java)
        val proBuilds = runCatching {
            val raw = appContext.assets.open("meta/hokpro_builds.json").bufferedReader().use { it.readText() }
            val type = Types.newParameterizedType(Map::class.java, String::class.java, HoKProBuildJson::class.java)
            moshi.adapter<Map<String, HoKProBuildJson>>(type).fromJson(raw) ?: emptyMap()
        }.getOrDefault(emptyMap())
        val proHeroes = runCatching {
            val raw = appContext.assets.open("meta/hokpro_heroes.json").bufferedReader().use { it.readText() }
            val type = Types.newParameterizedType(Map::class.java, String::class.java, HoKProHeroJson::class.java)
            moshi.adapter<Map<String, HoKProHeroJson>>(type).fromJson(raw) ?: emptyMap()
        }.getOrDefault(emptyMap())
        val difficultyMap = runCatching {
            val raw = appContext.assets.open("meta/dificuldade_reai.json").bufferedReader().use { it.readText() }
            val type = Types.newParameterizedType(Map::class.java, String::class.java, DifficultyJson::class.java)
            moshi.adapter<Map<String, DifficultyJson>>(type).fromJson(raw) ?: emptyMap()
        }.getOrDefault(emptyMap())
        val countersMap = runCatching {
            val raw = appContext.assets.open("meta/counters.json").bufferedReader().use { it.readText() }
            val type = Types.newParameterizedType(Map::class.java, String::class.java, CountersJson::class.java)
            moshi.adapter<Map<String, CountersJson>>(type).fromJson(raw) ?: emptyMap()
        }.getOrDefault(emptyMap())
        val patchDetails = runCatching {
            val raw = appContext.assets.open("meta/patch_details.json").bufferedReader().use { it.readText() }
            val type = Types.newParameterizedType(Map::class.java, String::class.java, String::class.java)
            moshi.adapter<Map<String, String>>(type).fromJson(raw) ?: emptyMap()
        }.getOrDefault(emptyMap())
        // Overrides do painel admin: Room cache tem prioridade sobre o embutido
        adminOverrides = runCatching {
            val cached = cacheDao.get(MetaCacheDao.KEY_ADMIN_OVERRIDES)?.json
                ?: appContext.assets.open("meta/admin_overrides.json").bufferedReader().use { it.readText() }
            moshi.adapter(AdminOverridesJson::class.java).fromJson(cached)
        }.getOrNull()
        // Reset explícito (banner/destaque removidos no painel devem sumir do app)
        _featuredHeroId.value = adminOverrides?.featuredHero?.takeIf { it.isNotBlank() }
        _bannerMessage.value = adminOverrides?.bannerMessage?.takeIf { it.isNotBlank() }
        val campPatch = runCatching {
            val raw = appContext.assets.open("meta/camp_patch.json").bufferedReader().use { it.readText() }
            moshi.adapter(CampPatchJson::class.java).fromJson(raw)
        }.getOrNull()

        // Temporada/patch atual (HoK Camp oficial, ex.: S16)
        if (!campPatch?.season.isNullOrBlank()) {
            val seasonLabel = campPatch!!.season.replaceFirst(Regex("^T"), "S")
            _patchLabel.value = "$seasonLabel • ${campPatch.date.ifBlank { "patch atual" }}"
        } else {
            patches.maxByOrNull { it.patchDate }?.let { latest ->
                _patchLabel.value = "${latest.season} • ${latest.patchDate}"
            }
        }

        // Notices reais do patch atual: prioriza o snapshot oficial do Camp (S16).
        // Filtra "ajustes universais" (rebalance geral), mostrando sÃ³ buffs/nerfs/reworks reais.
        if (campPatch != null && campPatch.changes.isNotEmpty()) {
            val genericAdjust = { c: CampPatchChange ->
                // Rebalance global da temporada não é nerf/buff individual
                c.summary.contains("Universal Adjustments", true) ||
                    c.summary.contains("Ajustes universais", true)
            }
            val seasonLabel = campPatch.season.replaceFirst(Regex("^T"), "S").ifBlank { "S16" }
            _patchNotices.value = campPatch.changes
                .filterNot(genericAdjust)
                .map { change ->
                    val tag = change.changeType.lowercase(java.util.Locale.ROOT)
                    val desc = change.summary.lowercase(java.util.Locale.ROOT)
                    val kind = when {
                        // BUFF: melhorias, bônus de atributos, upgrades de mecânica
                        tag.contains("melhorad") || tag.contains("bônus") || tag.contains("bonus") ||
                            tag.contains("buff") || tag.contains("upgrade") || tag.contains("improve") ||
                            desc.contains("aumentad") || desc.contains("melhorad") -> "BUFF"
                        // NERF: reduções de atributos/dano
                        tag.contains("redu") || tag.contains("nerf") ||
                            desc.contains("reduzid") || desc.contains("diminu") -> "NERF"
                        // REAJUSTE: atributos alterados/balanceados, sem corte claro
                        else -> "REAJUSTE"
                    }
                    // Resolve o nome oficial do app (ex.: patch PT "Ser do Fluxo (Mago)" → "Flowborn (Mage)")
                    val resolvedName = campFull.firstOrNull {
                        ChampionJsonMapper.matchesKey(it.name, change.heroName) ||
                            ChampionJsonMapper.matchesKey(it.slug, change.heroName)
                    }?.let { campEntry ->
                        val slug = CAMP_PT_SLUG_ALIASES[campEntry.slug] ?: campEntry.slug
                        heroes.firstOrNull { ChampionJsonMapper.matchesKey(it.slug, slug) }?.name
                    } ?: change.heroName
                    // Texto completo das notas oficiais (quando minerado do site oficial)
                    val officialDetail = patchDetails.entries.firstOrNull {
                        ChampionJsonMapper.matchesKey(it.key, resolvedName)
                    }?.value
                    PatchHeroNotice(
                        heroName = resolvedName,
                        lane = seasonLabel,
                        description = change.summary.ifBlank { change.changeType },
                        isBuff = kind == "BUFF",
                        kind = kind,
                        details = officialDetail ?: buildString {
                            append(change.changeType)
                            if (change.summary.isNotBlank() && change.summary != change.changeType) {
                                append(" — ")
                                append(change.summary)
                            }
                            if (change.changeType.contains("Alterados", true) || desc.contains("ajustad")) {
                                append("\n\nReajuste: os atributos e multiplicadores do herói foram recalibrados para o equilíbrio da temporada. Confira os valores exatos nas notas oficiais do patch.")
                            }
                        }
                    )
                }
            // Overrides do painel admin: ocultar avisos automáticos e adicionar manuais
            val hiddenNotices = adminOverrides?.patchHidden.orEmpty()
            _patchNotices.value = _patchNotices.value
                .filterNot { n -> hiddenNotices.any { ChampionJsonMapper.matchesKey(it, n.heroName) } } +
                adminOverrides?.patchExtras.orEmpty().map { extra ->
                    PatchHeroNotice(
                        heroName = extra.heroName,
                        lane = seasonLabel,
                        description = extra.summary.ifBlank { extra.kind },
                        isBuff = extra.kind.equals("BUFF", true),
                        kind = extra.kind.uppercase(java.util.Locale.ROOT).ifBlank { "REAJUSTE" },
                        details = extra.details
                    )
                }
        } else patches.maxByOrNull { it.patchDate }?.let { latest ->
            _patchNotices.value = latest.changes.map { change ->
                PatchHeroNotice(
                    heroName = change.heroName,
                    lane = laneLabelFor(latest.changes, change.heroSlug),
                    description = change.summary,
                    isBuff = change.changeType.contains("buff", true) ||
                        change.changeType.contains("new hero", true) ||
                        change.changeType.contains("upgrade", true)
                )
            }
        }

        // Overrides de tier/rates vindos do Ãºltimo sync remoto (cache Room)
        val tierOverrides = loadTierOverrides()
        campSnapshot = loadCampSnapshot()

        // Pool global de itens PT-BR (nome -> imagem) para itens situacionais por campeão
        val itemPoolPt: Map<String, String> = proBuilds.values
            .flatMap { it.itens }
            .filter { it.id.isNotBlank() && it.nome.isNotBlank() }
            .associate { it.nome to "https://hokpro.gg/api/image/${it.id}" }

        // Heróis que existem no Camp oficial mas não no heroes.json embutido (ex.: Yuan Ge, Flowborn extra)
        val existingKeys = heroes.flatMap { listOf(slugNorm(it.slug), slugNorm(it.name)) }.toSet()
        val syntheticHeroes = campFull.mapNotNull { c ->
            val slug = CAMP_PT_SLUG_ALIASES[c.slug] ?: c.slug
            if (existingKeys.contains(slugNorm(slug)) || existingKeys.contains(slugNorm(c.name))) return@mapNotNull null
            val enName = SYNTH_HERO_NAMES[slug] ?: c.name
            val rolesFromLane = when {
                c.lane.contains("superior", true) || c.lane.contains("Clash", true) -> listOf("Fighter")
                c.lane.contains("Caça", true) || c.lane.contains("Jungle", true) -> listOf("Assassin")
                c.lane.contains("meio", true) || c.lane.contains("Mid", true) -> listOf("Mage")
                c.lane.contains("inferior", true) || c.lane.contains("Farm", true) -> listOf("Marksman")
                else -> listOf("Support")
            }
            HeroJson(
                slug = slug,
                name = enName,
                tierKey = c.hot.lowercase(java.util.Locale.ROOT).ifBlank { "b" },
                roles = rolesFromLane,
                difficulty = 3,
                classType = rolesFromLane.first(),
                squareImage = c.icon,
                summary = "",
                skills = emptyList(),
                rates = emptyList()
            )
        }

        val hiddenSlugs = adminOverrides?.hiddenChampions.orEmpty()
        val isHidden = { s: String -> hiddenSlugs.any { slugNorm(it) == slugNorm(s) } }

        // ── Índices O(1) (antes: 7 varreduras O(n) com regex por herói = ~60 mil chamadas) ──
        fun <V> indexPairs(pairs: List<Pair<String, V>>): Map<String, V> {
            val m = HashMap<String, V>()
            pairs.forEach { (k, v) -> m.putIfAbsent(slugNorm(k), v) }
            return m
        }
        val mockById = MockMetaDatabase.allChampions.flatMap { listOf(it.id to it, it.name to it) }.let { pairs ->
            val m = HashMap<String, com.example.data.model.Champion>(); pairs.forEach { (k, v) -> m.putIfAbsent(slugNorm(k), v) }; m
        }
        val campFullById = campFull.flatMap { c ->
            val slug = CAMP_PT_SLUG_ALIASES[c.slug] ?: c.slug
            listOf(slug to c, c.slug to c, c.name to c)
        }.let { pairs -> val m = HashMap<String, CampHeroJson>(); pairs.forEach { (k, v) -> m.putIfAbsent(slugNorm(k), v) }; m }
        val proBuildsById = proBuilds.entries.associate { (k, v) -> slugNorm(k) to v }
        val countersById = countersMap.entries.associate { (k, v) -> slugNorm(k) to v }
        val difficultyById = difficultyMap.entries.associate { (k, v) -> slugNorm(k) to v }
        val tierOverridesById = tierOverrides.entries.associate { (k, v) -> slugNorm(k) to v }
        val adminTierById = adminOverrides?.tierOverrides?.entries?.associate { (k, v) -> slugNorm(k) to v }.orEmpty()
        val adminBuildById = adminOverrides?.customBuilds?.entries?.associate { (k, v) -> slugNorm(k) to v }.orEmpty()
        val campSnapById = campSnapshot.entries.associate { (k, v) -> slugNorm(k) to v }

        // Histórico de WR: carrega 1x (na thread IO) para consulta O(1) na UI
        wrHistoryByName = runCatching {
            val raw = appContext.assets.open("meta/rankings_history.json").bufferedReader().use { it.readText() }
            val hist = moshi.adapter(WrHistoryJson::class.java).fromJson(raw) ?: return@runCatching emptyMap<String, List<Pair<String, Double>>>()
            val out = HashMap<String, MutableList<Pair<String, Double>>>()
            hist.dates.forEach { date ->
                hist.data[date]?.forEach { (name, wr) ->
                    out.getOrPut(slugNorm(name)) { mutableListOf() }.add(date to wr)
                }
            }
            out
        }.getOrDefault(emptyMap())

        fun applyOverrides(base: com.example.data.model.Champion): com.example.data.model.Champion {
            var c = base
            adminOverrides?.statOverrides?.entries?.firstOrNull { ChampionJsonMapper.matchesKey(it.key, c.id) }?.value?.let { s ->
                if (s.winRate.isNotBlank()) c = c.copy(winRate = s.winRate)
                if (s.pickRate.isNotBlank()) c = c.copy(pickRate = s.pickRate)
                if (s.banRate.isNotBlank()) c = c.copy(banRate = s.banRate)
            }
            adminOverrides?.titleOverrides?.entries?.firstOrNull { ChampionJsonMapper.matchesKey(it.key, c.id) }?.value?.let { t ->
                c = c.copy(title = t)
            }
            return c
        }

        _champions.value = (heroes + syntheticHeroes).mapNotNull { hero ->
            if (isHidden(hero.slug)) return@mapNotNull null
            val heroKey = slugNorm(hero.slug)
            val heroNameKey = slugNorm(hero.name)
            val mock = mockById[heroKey] ?: mockById[heroNameKey]
            val snap = campSnapById[heroKey] ?: campSnapById[heroNameKey]
            val tierOverride = adminTierById[heroKey]
                ?: tierOverridesById[heroKey]?.name
                ?: tierOverridesById[heroNameKey]?.name
            val countersData = countersById[heroKey] ?: countersById[heroNameKey]
            val campHero = campFullById[heroKey] ?: campFullById[heroNameKey]
            val converted = ChampionJsonMapper.toChampion(
                hero = hero,
                items = items,
                arcanas = arcanas,
                tierOverride = tierOverride?.let { parseTier(it) },
                ratesOverride = snap?.let { HeroRateJson(winRate = it.winRate, pickRate = it.pickRate, banRate = it.banRate) },
                camp = campHero,
                proBuild = proBuildsById[heroKey],
                proHero = ChampionJsonMapper.findProHero(proHeroes, hero.slug, hero.name),
                itemPoolPt = itemPoolPt,
                difficultyReal = (difficultyById[heroKey] ?: difficultyById[heroNameKey])?.difficulty,
                countersData = countersData,
                customBuild = adminBuildById[heroKey],
                ptItemImage = { nome -> itemPoolPt.entries.firstOrNull { it.key.equals(nome.trim(), true) }?.value }
            )
            // Guia manual do Mock tem prioridade no texto, mas recebe WR/tier,
            // imagem real e (quando existe) a build/feitiço reais PT-BR
            val hasProBuild = converted.buildTitle.startsWith("Build Recomendada")
            val baseMerged = mock?.let {
                it.copy(
                    tier = converted.tier,
                    winRate = converted.winRate,
                    pickRate = converted.pickRate,
                    banRate = converted.banRate,
                    isWinRatePositive = converted.isWinRatePositive,
                    isBanPriority = converted.isBanPriority,
                    imageUrl = converted.imageUrl,
                    localDrawableRes = null,
                    items = if (hasProBuild) converted.items else it.items,
                    arcanas = if (hasProBuild) converted.arcanas else it.arcanas,
                    spellName = if (hasProBuild) converted.spellName else it.spellName,
                    spellSubtitle = if (hasProBuild) converted.spellSubtitle else it.spellSubtitle,
                    spellDescription = if (hasProBuild) converted.spellDescription else it.spellDescription,
                    spellImageUrl = if (hasProBuild) converted.spellImageUrl else it.spellImageUrl,
                    buildTitle = if (hasProBuild) converted.buildTitle else it.buildTitle,
                    counters = converted.counters,
                    synergies = converted.synergies,
                    strongAgainst = converted.strongAgainst,
                    proPlayerName = converted.proPlayerName,
                    proPlayerTeam = converted.proPlayerTeam
                )
            } ?: converted
            applyOverrides(baseMerged)
        }.sortedWith(compareBy({ it.tier.ordinal }, { -parseRate(it.winRate) }))

        // Enriquece counters: quem é "forte contra" este herói em outros cards vira
        // counter dele aqui (dados reais cruzados) — garante 4 counters por campeão.
        // Otimizado: chaves normalizadas pré-computadas 1x (antes: regex em loop O(n²)).
        val currentCounters = _champions.value
        val champKeyById = currentCounters.associate { it.id to ChampionJsonMapper.normalizeMatchupName(it.name) }
        val champByKey = currentCounters.associateBy { champKeyById[it.id] }

        // Índice reverso: nomeNormalizado do herói -> quem é forte contra ele
        val strongAgainstIndex = HashMap<String, MutableList<com.example.data.model.Champion>>()
        currentCounters.forEach { other ->
            other.strongAgainst.forEach { m ->
                val k = ChampionJsonMapper.normalizeMatchupName(m.name)
                strongAgainstIndex.getOrPut(k) { mutableListOf() }.add(other)
            }
        }

        // Resolve nomes PT da fonte BR para os nomes EN oficiais do app (1x, na IO)
        val allChampNames = currentCounters.map { it.name }
        val knownNormIndex = HashMap<String, String>()
        allChampNames.forEach { knownNormIndex[ChampionJsonMapper.normalizeMatchupName(it)] = it }
        fun fastResolve(raw: String): String {
            val clean = ChampionJsonMapper.normalizeMatchupName(raw)
            return knownNormIndex[clean] ?: raw
        }
        fun resolveList(list: List<MatchupInfo>): List<MatchupInfo> =
            if (list.isEmpty()) list else list.map { it.copy(name = fastResolve(it.name)) }

        // Frequência de counters por rota+classe (agregado real) para preencher heróis novos
        fun freqPool(champs: List<com.example.data.model.Champion>): List<String> =
            champs.flatMap { it.counters }
                .groupBy { ChampionJsonMapper.normalizeMatchupName(it.name) }
                .entries
                .sortedByDescending { it.value.size }
                .map { it.key }

        val withData = currentCounters.filter { it.counters.size >= 3 }
        val laneClassPools = withData
            .groupBy { it.lane to it.heroClass }
            .mapValues { (_, champs) -> freqPool(champs) }
        val laneOnlyPools = withData
            .groupBy { it.lane }
            .mapValues { (_, champs) -> freqPool(champs) }

        val enriched = currentCounters.map { champ ->
            val myKey = champKeyById[champ.id] ?: return@map champ
            val resolvedDirect = resolveList(champ.counters)
            val seen = resolvedDirect.map { ChampionJsonMapper.normalizeMatchupName(it.name) }.toMutableSet()

            // Camada 2: cruzamento reverso real (strongAgainst dos outros)
            val extra = strongAgainstIndex[myKey].orEmpty()
                .filter { it.id != champ.id }
                .map { MatchupInfo(it.name, it.lane.chipShort, 0) }
                .filter { ChampionJsonMapper.normalizeMatchupName(it.name) !in seen }

            var merged = (resolvedDirect + extra)
                .filter { ChampionJsonMapper.normalizeMatchupName(it.name) != myKey }
                .distinctBy { ChampionJsonMapper.normalizeMatchupName(it.name) }

            // Camada 3: agregado real da rota/classe (heróis novos sem dados próprios)
            if (merged.size < 4) {
                val pool = laneClassPools[champ.lane to champ.heroClass].orEmpty() +
                    laneOnlyPools[champ.lane].orEmpty()
                for (k in pool) {
                    if (merged.size >= 4) break
                    if (k == myKey || k in seen) continue
                    champByKey[k]?.let {
                        merged += MatchupInfo(it.name, it.lane.chipShort, 0)
                        seen += k
                    }
                }
            }
            champ.copy(
                counters = merged.take(4),
                synergies = resolveList(champ.synergies),
                strongAgainst = resolveList(champ.strongAgainst)
            )
        }
        _champions.value = enriched

        cacheDao.get(MetaCacheDao.KEY_HOKPRO_TIERLIST)?.let { _lastSyncAt.value = it.updatedAt }
    }

    private fun laneLabelFor(changes: List<PatchChangeJson>, slug: String): String {
        // Rotas desconhecidas no patches.json â€” usa nome da rota aproximado pelo tipo
        return when {
            changes.any { ChampionJsonMapper.matchesKey(it.heroSlug, slug) } -> "META"
            else -> "GLOBAL"
        }
    }

    private fun parseRate(s: String): Double =
        s.removeSuffix("%").replace(",", ".").toDoubleOrNull() ?: 0.0

    /**
     * HOK Pro usa S/A/B/C/D → app usa SS/S/A/B/C:
     * S→SS, A→S, B→A, C→B, D→C. (SS e SS+ do site também viram SS direto.)
     */
    private fun hoKProTier(s: String): HeroTier? = when (s.uppercase(Locale.ROOT)) {
        "SS+" -> HeroTier.SS
        "SS" -> HeroTier.SS
        "S" -> HeroTier.SS
        "A" -> HeroTier.S
        "B" -> HeroTier.A
        "C" -> HeroTier.B
        "D" -> HeroTier.C
        else -> null
    }

    /** Converte tier (string do Camp/admin) para nosso enum SS/S/A/B/C. */
    private fun parseTier(s: String): HeroTier = when (s.uppercase(Locale.ROOT)) {
        "SS" -> HeroTier.SS
        "S" -> HeroTier.S
        "A" -> HeroTier.A
        "C" -> HeroTier.C
        else -> HeroTier.B
    }

    // ---------------- Cache Room ----------------

    private suspend fun loadTierOverrides(): Map<String, HeroTier> {
        val entry = cacheDao.get(MetaCacheDao.KEY_HOKPRO_TIERLIST) ?: return emptyMap()
        return runCatching { parseHoKProTierList(entry.json).also { _lastSyncAt.value = entry.updatedAt } }
            .getOrDefault(emptyMap())
    }

    private suspend fun loadCampSnapshot(): Map<String, SnapshotEntry> {
        // Cache Room (sync remoto mais recente) tem prioridade; senÃ£o usa o snapshot embarcado
        val entry = cacheDao.get(MetaCacheDao.KEY_RANKINGS_SNAPSHOT)
        val json = entry?.json ?: runCatching {
            appContext.assets.open("meta/rankings.json").bufferedReader().use { it.readText() }
        }.getOrNull() ?: return emptyMap()
        return runCatching {
            val type = Types.newParameterizedType(Map::class.java, String::class.java, SnapshotEntry::class.java)
            moshi.adapter<Map<String, SnapshotEntry>>(type).fromJson(json) ?: emptyMap()
        }.getOrDefault(emptyMap())
    }

    // ---------------- Sync remoto ----------------

    sealed class SyncResult {
        data class Success(val sources: List<String>, val at: Long) : SyncResult()
        data class Failure(val message: String) : SyncResult()
    }

    suspend fun syncNow(): SyncResult = kotlinx.coroutines.withContext(Dispatchers.IO) {
        val sources = mutableListOf<String>()

        // Camada 3 â€” tier list BR (HOK Pro), acesso direto sem autenticaÃ§Ã£o
        runCatching {
            val response = RemoteModule.hoKProApi.getTierList()
            val json = moshi.adapter(com.example.data.remote.HoKProTierListResponse::class.java).toJson(response)
            cacheDao.put(MetaCacheEntity(MetaCacheDao.KEY_HOKPRO_TIERLIST, json))
            sources += "Tier list da comunidade BR"
        }

        // Camada 2 â€” snapshot oficial do Camp (servido pelo micro-backend do app, se configurado)
        val snapshotUrl = snapshotUrlOverride
        if (!snapshotUrl.isNullOrBlank()) {
            runCatching {
                val body = okhttp3.OkHttpClient().newCall(
                    okhttp3.Request.Builder().url(snapshotUrl).build()
                ).execute().use { if (it.isSuccessful) it.body?.string() else null }
                if (body != null) {
                    cacheDao.put(MetaCacheEntity(MetaCacheDao.KEY_RANKINGS_SNAPSHOT, body))
                    sources += "HoK Camp oficial"
                }
            }
        }

        // Overrides do painel admin (URL configurável)
        val adminUrl = adminOverridesUrl ?: defaultAdminOverridesUrl
        if (!adminUrl.isNullOrBlank()) {
            runCatching {
                val body = okhttp3.OkHttpClient().newCall(
                    okhttp3.Request.Builder().url(adminUrl).build()
                ).execute().use { if (it.isSuccessful) it.body?.string() else null }
                if (body != null) {
                    cacheDao.put(MetaCacheEntity(MetaCacheDao.KEY_ADMIN_OVERRIDES, body))
                    sources += "Painel Admin"
                }
            }
        }

        if (sources.isEmpty()) return@withContext SyncResult.Failure("Sem conexão — usando dados locais em cache")

        loadBase()
        _lastSyncAt.value = System.currentTimeMillis()
        SyncResult.Success(sources, System.currentTimeMillis())
    }

    /** URL do micro-backend com snapshot do Camp (configurÃ¡vel; vazio = desativado). */
    var snapshotUrlOverride: String? =
        "https://raw.githubusercontent.com/eoalpha-debug/ash-builds/main/app/src/main/assets/meta/rankings.json"

    /** URL do painel admin overrides.json (configurável; padrão no companion). */
    var adminOverridesUrl: String? = null

    // ---------------- Parsing HOK Pro ----------------

    private fun parseHoKProTierList(json: String): Map<String, HeroTier> {
        val adapter = moshi.adapter(com.example.data.remote.HoKProTierListResponse::class.java)
        val response = adapter.fromJson(json) ?: return emptyMap()
        val laneSuffixes = listOf("Superior", "Selva", "Meio", "Suporte", "Atirador")
        val result = mutableMapOf<String, HeroTier>()
        response.tierList.values.forEach { tiers ->
            tiers.forEach { (tierName, heroes) ->
                val tier = hoKProTier(tierName) ?: HeroTier.C
                heroes.forEach { rawName ->
                    val cleanName = laneSuffixes.fold(rawName) { acc, suffix ->
                        acc.removeSuffix(" $suffix").trim()
                    }
                    // MantÃ©m o melhor tier se o herÃ³i aparecer em mais de uma rota
                    val existing = result[cleanName]
                    if (existing == null || tier.ordinal < existing.ordinal) result[cleanName] = tier
                }
            }
        }
        return result
    }

    // ---------------- JSON helpers ----------------

    private inline fun <reified T> readJsonList(assetPath: String, clazz: Class<T>): List<T> {
        return runCatching {
            val raw = appContext.assets.open(assetPath).bufferedReader().use { it.readText() }
            val type = Types.newParameterizedType(List::class.java, clazz)
            moshi.adapter<List<T>>(type).fromJson(raw) ?: emptyList()
        }.getOrDefault(emptyList())
    }

    fun getChampionById(id: String): Champion? =
        _champions.value.firstOrNull {
            it.id.equals(id, true) || ChampionJsonMapper.matchesKey(it.id, id)
        }

    /** Histórico de WR do herói (datas × valores). O(1): lê do mapa pré-carregado no loadBase. */
    fun getWrHistory(heroName: String): List<Pair<String, Double>> {
        if (wrHistoryByName.isEmpty()) return emptyList()
        val k = heroName.lowercase(java.util.Locale.ROOT).replace(Regex("[^a-z0-9]"), "")
        return wrHistoryByName[k].orEmpty()
    }

    fun getChampionsByLane(lane: Lane): List<Champion> =
        if (lane == Lane.TODAS) _champions.value else _champions.value.filter { it.lane == lane }

    companion object {
        @Volatile
        private var INSTANCE: MetaRepository? = null

        /** URL padrão do painel admin (mude aqui ou configure em runtime). */
        const val defaultAdminOverridesUrl =
            "https://raw.githubusercontent.com/eoalpha-debug/ash-builds/main/admin/overrides.json"

        fun getInstance(context: Context): MetaRepository =
            INSTANCE ?: synchronized(this) {
                MetaRepository(context).also { INSTANCE = it }
            }
    }
}


