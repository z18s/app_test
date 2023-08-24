package com.example.testapp.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testapp.model.entity.RoomMessage

@Database(entities = [RoomMessage::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private const val DB_NAME: String = "test.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                .build()
                .also { INSTANCE = it }
        }
    }
    abstract fun cacheDao(): CacheDao
}