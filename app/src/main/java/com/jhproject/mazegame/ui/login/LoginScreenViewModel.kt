package com.jhproject.mazegame.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhproject.mazegame.data.AccountRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel(private val repository: AccountRepository) : ViewModel() {
    private val childUsername = MutableStateFlow("")
    val childUsernameValue: StateFlow<String> = childUsername.asStateFlow()

    fun onChildUsernameValueChange(newValue: String) {
        childUsername.value = newValue
    }

    private val childPassword = MutableStateFlow("")
    val childPasswordValue: StateFlow<String> = childPassword.asStateFlow()

    fun onChildPasswordValueChange(newValue: String) {
        childPassword.value = newValue
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

    fun childLogin(childUsername: String, childPassword: String, onLoginSuccess: (Int) -> Unit, onLoginFailure: () -> Unit) {
        viewModelScope.launch {
            val child = repository.loginChild(childUsername, childPassword)
            if (child != null) {
                onLoginSuccess(child.childId)
            } else {
                onLoginFailure()
            }
        }
    }

    fun parentLogin(parentUsername: String, parentPassword: String, onLoginSuccess: (Int) -> Unit, onLoginFailure: () -> Unit) {
        viewModelScope.launch {
            val parent = repository.loginParent(parentUsername, parentPassword)
            if (parent != null) {
                onLoginSuccess(parent.parent.parentId)
            } else {
                onLoginFailure()
            }
        }
    }
}