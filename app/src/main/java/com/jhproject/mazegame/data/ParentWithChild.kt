package com.jhproject.mazegame.data

import androidx.room.Embedded
import androidx.room.Relation

data class ParentWithChildren(
    @Embedded
    val parent: Parent,
    @Relation(
        parentColumn = "parentId",
        entityColumn = "parentId"
    )
    val children: List<Child>
)