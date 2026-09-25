package com.example.data.local

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Builds customizadas criadas pelo usuário no Item Builder.
 * itemIds guarda os ids reais dos itens (imagem via hokstats).
 */
@Entity(tableName = "custom_builds")
data class CustomBuildEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    /** Slug do campeão escolhido (vazio = build livre sem campeão). */
    val championId: String = "",
    val championName: String = "",
    /** Ids dos 6 itens separados por | na ordem escolhida. */
    val itemIds: String,
    /** Nomes dos itens separados por | (para exibir sem consultar imagens). */
    val itemNames: String,
    val createdAt: Long = System.currentTimeMillis()
)

@Dao
interface CustomBuildDao {
    @Query("SELECT * FROM custom_builds ORDER BY createdAt DESC")
    fun getAll(): Flow<List<CustomBuildEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(build: CustomBuildEntity)

    @Query("DELETE FROM custom_builds WHERE id = :id")
    suspend fun deleteById(id: Int)
}
