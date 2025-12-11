package com.jhproject.mazegame.ui.childlandingscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhproject.mazegame.data.AccountRepository
import com.jhproject.mazegame.data.Child
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChildLandingScreenViewModel(
    private val repository: AccountRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _child = MutableStateFlow<Child?>(null)
    val child: StateFlow<Child?> = _child.asStateFlow()

    private val childId: Int? = savedStateHandle["childId"]

    init {
        childId?.let {
            viewModelScope.launch {
                _child.value = repository.getChild(it)
            }
        }
    }
}