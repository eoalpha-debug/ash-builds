package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_guides")
data class SavedGuideEntity(
  @PrimaryKey val championId: String,
  val championName: String,
  val championTitle: String,
  val heroClass: String,
  val lane: String,
  val tier: String,
  val imageUrl: String,
  val winRate: String,
  val pickRate: String,
  val banRate: String,
  val buildSummary: String,
  val cacheSize: String,
  val savedAt: Long = System.currentTimeMillis()
)
