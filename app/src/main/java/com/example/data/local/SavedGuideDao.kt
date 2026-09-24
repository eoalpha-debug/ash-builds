package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedGuideDao {
  @Query("SELECT * FROM saved_guides ORDER BY savedAt DESC")
  fun getAllSavedGuides(): Flow<List<SavedGuideEntity>>

  @Query("SELECT * FROM saved_guides WHERE championId = :championId LIMIT 1")
  fun getSavedGuide(championId: String): Flow<SavedGuideEntity?>

  @Query("SELECT EXISTS(SELECT 1 FROM saved_guides WHERE championId = :championId)")
  fun isGuideSaved(championId: String): Flow<Boolean>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertGuide(guide: SavedGuideEntity)

  @Query("DELETE FROM saved_guides WHERE championId = :championId")
  suspend fun deleteGuideById(championId: String)

  @Query("DELETE FROM saved_guides")
  suspend fun clearAllGuides()

  @Query("SELECT COUNT(*) FROM saved_guides")
  fun getSavedGuidesCount(): Flow<Int>
}
