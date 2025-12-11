package com.jhproject.mazegame.data

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
}