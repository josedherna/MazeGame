package com.jhproject.mazegame.ui.hardlevels

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jhproject.mazegame.data.AccountRepository
import com.jhproject.mazegame.data.AppDatabase

class HardLevel3ViewModelFactory(
    private val app: Application,
    private val savedStateHandle: SavedStateHandle
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HardLevel3ScreenViewModel::class.java)) {
            val database = AppDatabase.getInstance(app)
            val repo = AccountRepository(database.parentChildDao())
            return HardLevel3ScreenViewModel(repo, savedStateHandle) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }
}