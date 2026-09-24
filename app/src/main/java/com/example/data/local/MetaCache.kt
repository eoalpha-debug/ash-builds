package com.example.data.local

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

/**
 * Cache de snapshots sincronizados das fontes remotas (HOK Camp global, HOK Pro).
 * Guarda o JSON bruto + timestamp para o modo offline funcionar de verdade.
 */
@Entity(tableName = "meta_cache")
data class MetaCacheEntity(
    @PrimaryKey val key: String,
    val json: String,
    val updatedAt: Long = System.currentTimeMillis()
)

@Dao
interface MetaCacheDao {
    @Query("SELECT * FROM meta_cache WHERE `key` = :key LIMIT 1")
    suspend fun get(key: String): MetaCacheEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun put(entry: MetaCacheEntity)

    @Query("DELETE FROM meta_cache")
    suspend fun clear()

    companion object {
        const val KEY_RANKINGS_SNAPSHOT = "camp_rankings_snapshot"
        const val KEY_HOKPRO_TIERLIST = "hokpro_tierlist"
        const val KEY_ADMIN_OVERRIDES = "admin_overrides"
    }
}
