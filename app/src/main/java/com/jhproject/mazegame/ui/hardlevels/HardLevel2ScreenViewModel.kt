package com.jhproject.mazegame.ui.hardlevels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhproject.mazegame.data.AccountRepository
import com.jhproject.mazegame.data.ProgressLogs
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HardLevel2ScreenViewModel(
    private val repository: AccountRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val childId: Int = checkNotNull(savedStateHandle["childId"])

    fun saveProgress(hasWon: Boolean) {
        viewModelScope.launch {
            val progress = if (hasWon) "passed" else "fail"
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val currentDateTime = sdf.format(Date())
            val progressLog = ProgressLogs(childId = childId, progress = progress, level = "Hard Level 2", dateTime = currentDateTime)
            repository.insertProgressLog(progressLog)
        }
    }
}