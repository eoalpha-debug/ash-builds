package com.example.data.local

import com.example.data.model.Champion
import kotlinx.coroutines.flow.Flow

class GuideRepository(private val dao: SavedGuideDao) {
  val allSavedGuides: Flow<List<SavedGuideEntity>> = dao.getAllSavedGuides()
  val savedGuidesCount: Flow<Int> = dao.getSavedGuidesCount()

  fun isGuideSaved(championId: String): Flow<Boolean> = dao.isGuideSaved(championId)

  suspend fun saveChampionGuide(champion: Champion, cacheSizeMb: String = "4.2 MB") {
    val entity = SavedGuideEntity(
      championId = champion.id,
      championName = champion.name,
      championTitle = champion.title,
      heroClass = champion.heroClass,
      lane = champion.lane.chipShort,
      tier = champion.tier.badge,
      imageUrl = champion.imageUrl,
      winRate = champion.winRate,
      pickRate = champion.pickRate,
      banRate = champion.banRate,
      buildSummary = "${champion.coreItemSummary} • Arcanas 150 & Combos",
      cacheSize = cacheSizeMb
    )
    dao.insertGuide(entity)
  }

  suspend fun removeGuide(championId: String) {
    dao.deleteGuideById(championId)
  }

  suspend fun clearAll() {
    dao.clearAllGuides()
  }
}
