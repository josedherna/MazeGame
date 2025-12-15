package com.jhproject.mazegame.ui.easylevels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.SavedStateHandle
import com.jhproject.mazegame.data.AccountRepository
import com.jhproject.mazegame.data.AppDatabase

class EasyLevel1ViewModelFactory(
    private val app: Application,
    private val savedStateHandle: SavedStateHandle
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EasyLevel1ScreenViewModel::class.java)) {
            val database = AppDatabase.getInstance(app)
            val repo = AccountRepository(database.parentChildDao())
            return EasyLevel1ScreenViewModel(repo, savedStateHandle) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }
}