package com.jhproject.mazegame.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface ParentChildDao {
    @Insert
    suspend fun insertParent(parent: Parent): Long

    @Insert
    suspend fun insertChild(child: Child)

    @Transaction
    @Query("SELECT * FROM parents WHERE username = :parentUsername AND password = :parentPassword")
    suspend fun loginParent(parentUsername: String, parentPassword: String): ParentWithChildren?

    @Query("SELECT * FROM children WHERE username = :childUsername AND password = :childPassword")
    suspend fun loginChild(childUsername: String, childPassword: String): Child?

    @Query("SELECT * FROM children WHERE childId = :id")
    suspend fun getChild(id: Int): Child?

    @Transaction
    @Query("SELECT * FROM parents WHERE parentId = :id")
    suspend fun getParentWithChildren(id: Int): ParentWithChildren?

    @Insert
    suspend fun insertProgressLog(progressLog: ProgressLogs)

    @Query("SELECT * FROM child_progress WHERE childId = :childId ORDER BY id DESC")
    fun getProgressLogs(childId: Int): Flow<List<ProgressLogs>>
}