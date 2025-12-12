package com.jhproject.mazegame.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

//val MIGRATION_1_TO_2 = object : Migration(1, 2) {
//    override fun migrate(db: SupportSQLiteDatabase) {
//        db.execSQL("""
//            CREATE TABLE IF NOT EXISTS child_progress (
//                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
//                childId INTEGER NOT NULL,
//                progress TEXT NOT NULL,
//                dateTime TEXT NOT NULL
//            )
//        """.trimIndent())
//    }
//}

val MIGRATION_2_TO_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("""
            ALTER TABLE child_progress ADD COLUMN level TEXT NOT NULL DEFAULT ''
        """.trimIndent())
    }
}

@Database(entities = [Parent::class, Child::class, ProgressLogs::class], version = 3)
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
                ).addMigrations(MIGRATION_2_TO_3).build().also { INSTANCE = it }
            }
        }
    }
}