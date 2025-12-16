package com.jhproject.mazegame.data

import kotlinx.coroutines.flow.Flow

class AccountRepository(private val parentChildDao: ParentChildDao) {
    suspend fun insertParent(parent: Parent) {
        parentChildDao.insertParent(parent)
    }

    suspend fun insertChild(child: Child) {
        parentChildDao.insertChild(child)
    }

    suspend fun loginParent(parentUsername: String, parentPassword: String): ParentWithChildren? {
        return parentChildDao.loginParent(parentUsername, parentPassword)
    }

    suspend fun loginChild(childUsername: String, childPassword: String): Child? {
        return parentChildDao.loginChild(childUsername, childPassword)
    }

    suspend fun getChild(id: Int): Child? {
        return parentChildDao.getChild(id)
    }

    suspend fun getParentsWithChildren(id: Int): ParentWithChildren? {
        return parentChildDao.getParentWithChildren(id)
    }

    suspend fun insertProgressLog(progressLog: ProgressLogs) {
        parentChildDao.insertProgressLog(progressLog)
    }

    fun getProgressLogs(childID: Int): Flow<List<ProgressLogs>> {
        return parentChildDao.getProgressLogs(childID)
    }
}