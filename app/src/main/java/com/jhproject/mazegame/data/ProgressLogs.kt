package com.jhproject.mazegame.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "child_progress")
data class ProgressLogs(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val childId: Int,
    val progress: String,
    val dateTime: String
)