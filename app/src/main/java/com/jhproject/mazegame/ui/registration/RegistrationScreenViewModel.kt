package com.jhproject.mazegame.ui.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhproject.mazegame.data.AccountRepository
import com.jhproject.mazegame.data.Parent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegistrationScreenViewModel(private val repository: AccountRepository) : ViewModel() {
    private val parentName = MutableStateFlow("")
    val parentNameValue: StateFlow<String> = parentName.asStateFlow()

    fun onParentNameValueChange(newValue: String) {
        parentName.value = newValue
    }
    private val parentUsername = MutableStateFlow("")
    val parentUsernameValue: StateFlow<String> = parentUsername.asStateFlow()

    fun onParentUsernameValueChange(newValue: String) {
        parentUsername.value = newValue
    }

    private val parentPassword = MutableStateFlow("")
    val parentPasswordValue: StateFlow<String> = parentPassword.asStateFlow()

    fun onParentPasswordValueChange(newValue: String) {
        parentPassword.value = newValue
    }

    fun createParent(newParent: Parent) {
        viewModelScope.launch {
            repository.insertParent(newParent)
        }
    }
}