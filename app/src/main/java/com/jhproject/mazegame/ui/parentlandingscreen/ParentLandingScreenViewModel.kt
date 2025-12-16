package com.jhproject.mazegame.ui.parentlandingscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhproject.mazegame.data.AccountRepository
import com.jhproject.mazegame.data.Child
import com.jhproject.mazegame.data.ParentWithChildren
import com.jhproject.mazegame.data.ProgressLogs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ParentLandingScreenViewModel(
    private val repository: AccountRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _parent = MutableStateFlow<ParentWithChildren?>(null)
    val parent: StateFlow<ParentWithChildren?> = _parent.asStateFlow()

    private val userId: Int? = savedStateHandle["userId"]

    init {
        userId?.let {
            viewModelScope.launch {
                _parent.value = repository.getParentsWithChildren(it)
            }
        }
    }

    fun addChild(name: String, username: String, password: String) {
        viewModelScope.launch {
            val parentId = userId ?: return@launch
            val child = Child(name = name, username = username, password = password, parentId = parentId)
            repository.insertChild(child)
            _parent.value = repository.getParentsWithChildren(parentId)
        }
    }

    fun getProgressLogs(childID: Int): Flow<List<ProgressLogs>> {
        return repository.getProgressLogs(childID)
    }
}