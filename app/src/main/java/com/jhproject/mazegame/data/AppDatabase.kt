package com.jhproject.mazegame.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Parent::class, Child::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun parentChildDao(): ParentChildDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mazegame_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}