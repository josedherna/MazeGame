package com.jhproject.mazegame.ui.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginScreenViewModel : ViewModel() {
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
        childUsername.value = newValue
    }

    private val parentPassword = MutableStateFlow("")
    val parentPasswordValue: StateFlow<String> = parentPassword.asStateFlow()

    fun onParentPasswordValueChange(newValue: String) {
        childPassword.value = newValue
    }
}