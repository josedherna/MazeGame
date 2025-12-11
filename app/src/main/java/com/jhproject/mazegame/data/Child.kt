package com.jhproject.mazegame.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "children",
    foreignKeys = [
        ForeignKey(
            entity = Parent::class,
            parentColumns = ["parentId"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["parentId"])]
)
data class Child(
    @PrimaryKey(autoGenerate = true)
    val childId: Int = 0,
    val name: String,
    val username: String,
    val password: String,
    val parentId: Int
)