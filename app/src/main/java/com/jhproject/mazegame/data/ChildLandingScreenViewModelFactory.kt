package com.jhproject.mazegame.data

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jhproject.mazegame.ui.childlandingscreen.ChildLandingScreenViewModel
import androidx.lifecycle.SavedStateHandle

class ChildLandingScreenViewModelFactory(
    private val app: Application,
    private val savedStateHandle: SavedStateHandle
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChildLandingScreenViewModel::class.java)) {
            val database = AppDatabase.getInstance(app)
            val repo = AccountRepository(database.parentChildDao())
            return ChildLandingScreenViewModel(repo, savedStateHandle) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }
}