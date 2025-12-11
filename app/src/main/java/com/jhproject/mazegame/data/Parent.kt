package com.jhproject.mazegame.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "parents")
data class Parent(
    @PrimaryKey(autoGenerate = true)
    val parentId: Int = 0,
    val name: String,
    val username: String,
    val password: String
)