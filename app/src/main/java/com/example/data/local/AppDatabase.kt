package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SavedGuideEntity::class, MetaCacheEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
  abstract fun savedGuideDao(): SavedGuideDao
  abstract fun metaCacheDao(): MetaCacheDao

  companion object {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          AppDatabase::class.java,
          "hok_metalab_db"
        ).fallbackToDestructiveMigration().build()
        INSTANCE = instance
        instance
      }
    }
  }
}
