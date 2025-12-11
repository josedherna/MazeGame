package com.jhproject.mazegame.data

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jhproject.mazegame.ui.parentlandingscreen.ParentLandingScreenViewModel
import androidx.lifecycle.SavedStateHandle

class ParentLandingScreenViewModelFactory(
    private val app: Application,
    private val savedStateHandle: SavedStateHandle
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ParentLandingScreenViewModel::class.java)) {
            val database = AppDatabase.getInstance(app)
            val repo = AccountRepository(database.parentChildDao())
            return ParentLandingScreenViewModel(repo, savedStateHandle) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }
}