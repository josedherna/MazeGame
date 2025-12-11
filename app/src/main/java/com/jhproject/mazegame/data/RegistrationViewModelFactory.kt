package com.jhproject.mazegame.data

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jhproject.mazegame.ui.registration.RegistrationScreenViewModel

class RegistrationViewModelFactory(
    private val app: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val database = AppDatabase.getInstance(app)

        return when (modelClass) {

            RegistrationScreenViewModel::class.java -> {
                val repo = AccountRepository(database.parentChildDao())
                RegistrationScreenViewModel(repo) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
        }
    }
}