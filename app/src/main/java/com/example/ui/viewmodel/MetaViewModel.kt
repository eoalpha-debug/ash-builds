package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.GuideRepository
import com.example.data.local.SavedGuideEntity
import com.example.data.meta.MetaRepository
import com.example.data.model.Champion
import com.example.data.model.HeroTier
import com.example.data.model.Lane
import com.example.data.model.PatchHeroNotice
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MetaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: GuideRepository
    private val metaRepository: MetaRepository
    private val database: AppDatabase

    init {
        val db = AppDatabase.getDatabase(application)
        this.database = db
        repository = GuideRepository(db.savedGuideDao())
        metaRepository = MetaRepository.getInstance(application)
    }

    /** Lista de campeões com dados reais do servidor global (116 heróis). */
    val allChampions: StateFlow<List<Champion>> = metaRepository.champions

    /** Notas reais do último patch (HoK Camp global). */
    val patchNotices: StateFlow<List<PatchHeroNotice>> = metaRepository.patchNotices

    /** Rótulo do patch/temporada atual (ex.: "S16 • 2026/09/23"). */
    val patchLabel: StateFlow<String> = metaRepository.patchLabel

    /** Herói em destaque fixado pelo painel admin (override). */
    val featuredHeroId: StateFlow<String?> = metaRepository.featuredHeroId

    /** Banner de aviso do painel admin (vazio = oculto). */
    val bannerMessage: StateFlow<String?> = metaRepository.bannerMessage

    /** Catálogo completo de itens reais (para o Builder livre). */
    val allItems = metaRepository.allItems

    /** Builds livres criadas pelo usuário (Item Builder). */
    val customBuilds: StateFlow<List<com.example.data.local.CustomBuildEntity>> =
        database.customBuildDao().getAll()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun saveCustomBuild(name: String, champion: Champion?, items: List<com.example.data.meta.ItemJson>) {
        if (name.isBlank() || items.isEmpty()) return
        viewModelScope.launch {
            database.customBuildDao().insert(
                com.example.data.local.CustomBuildEntity(
                    name = name.trim(),
                    championId = champion?.id ?: "",
                    championName = champion?.name ?: "",
                    itemIds = items.joinToString("|") { it.itemId },
                    itemNames = items.joinToString("|") { it.name }
                )
            )
        }
    }

    fun deleteCustomBuild(id: Int) {
        viewModelScope.launch { database.customBuildDao().deleteById(id) }
    }

    /** Histórico de WR de um herói (datas × valores) para o gráfico. */
    fun getWrHistory(heroName: String): List<Pair<String, Double>> =
        metaRepository.getWrHistory(heroName)

    val savedGuides: StateFlow<List<SavedGuideEntity>> = repository.allSavedGuides
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Selected Lane for Tier List
    private val _selectedLane = MutableStateFlow(Lane.TODAS)
    val selectedLane: StateFlow<Lane> = _selectedLane.asStateFlow()

    // Search query for Tier List & Champions
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Selected Champion for detail navigation
    private val _selectedChampionId = MutableStateFlow<String?>(null)
    val selectedChampionId: StateFlow<String?> = _selectedChampionId.asStateFlow()

    // Offline Mode banner toggle
    private val _isOfflineModeActive = MutableStateFlow(true)
    val isOfflineModeActive: StateFlow<Boolean> = _isOfflineModeActive.asStateFlow()

    // Auto-download setting
    private val _autoDownloadTopTiers = MutableStateFlow(true)
    val autoDownloadTopTiers: StateFlow<Boolean> = _autoDownloadTopTiers.asStateFlow()

    // Data saver mode
    private val _dataSaverMode = MutableStateFlow(false)
    val dataSaverMode: StateFlow<Boolean> = _dataSaverMode.asStateFlow()

    // Syncing state
    private val _isSyncing = MutableStateFlow(false)
    val isSyncing: StateFlow<Boolean> = _isSyncing.asStateFlow()

    private val _syncMessage = MutableStateFlow("Dados locais carregados")
    val syncMessage: StateFlow<String> = _syncMessage.asStateFlow()

    // Actions
    fun selectLane(lane: Lane) {
        _selectedLane.value = lane
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectChampion(championId: String) {
        _selectedChampionId.value = championId
    }

    fun getChampionById(championId: String): Champion? =
        metaRepository.getChampionById(championId)

    fun toggleOfflineMode() {
        _isOfflineModeActive.value = !_isOfflineModeActive.value
    }

    fun toggleAutoDownload() {
        _autoDownloadTopTiers.value = !_autoDownloadTopTiers.value
    }

    fun toggleDataSaver() {
        _dataSaverMode.value = !_dataSaverMode.value
    }

    fun toggleSaveChampion(champion: Champion) {
        viewModelScope.launch {
            val isSaved = savedGuides.value.any { it.championId == champion.id }
            if (isSaved) {
                repository.removeGuide(champion.id)
            } else {
                repository.saveChampionGuide(champion)
            }
        }
    }

    fun removeSavedGuide(championId: String) {
        viewModelScope.launch {
            repository.removeGuide(championId)
        }
    }

    fun clearOldCache(onComplete: () -> Unit) {
        viewModelScope.launch {
            repository.clearAll()
            onComplete()
        }
    }

    /** Sync real: HOK Pro (tier list BR) + snapshot do Camp oficial quando configurado. */
    fun syncMetaUpdates() {
        if (_isSyncing.value) return
        viewModelScope.launch {
            _isSyncing.value = true
            when (val result = metaRepository.syncNow()) {
                is MetaRepository.SyncResult.Success -> {
                    val time = SimpleDateFormat("HH:mm", Locale("pt", "BR")).format(Date(result.at))
                    _syncMessage.value = "Sincronizado às $time • ${result.sources.joinToString(" + ")}"
                }
                is MetaRepository.SyncResult.Failure -> {
                    _syncMessage.value = result.message
                }
            }
            _isSyncing.value = false
        }
    }

    // Filtered lists helper
    fun getFilteredTierHeroes(tier: HeroTier): List<Champion> {
        val lane = _selectedLane.value
        val query = _searchQuery.value.trim().lowercase()

        return allChampions.value.filter { champion ->
            val matchesTier = champion.tier == tier
            val matchesLane = lane == Lane.TODAS || champion.lane == lane
            val matchesQuery = query.isEmpty() ||
                champion.name.lowercase().contains(query) ||
                champion.heroClass.lowercase().contains(query) ||
                champion.lane.labelPt.lowercase().contains(query)
            matchesTier && matchesLane && matchesQuery
        }
    }

    fun getFilteredChampionsList(): List<Champion> {
        val lane = _selectedLane.value
        val query = _searchQuery.value.trim().lowercase()

        return allChampions.value.filter { champion ->
            val matchesLane = lane == Lane.TODAS || champion.lane == lane
            val matchesQuery = query.isEmpty() ||
                champion.name.lowercase().contains(query) ||
                champion.heroClass.lowercase().contains(query) ||
                champion.lane.labelPt.lowercase().contains(query)
            matchesLane && matchesQuery
        }
    }
}
