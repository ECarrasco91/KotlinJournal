package com.ezequielc.kotlinjournal.ui.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezequielc.kotlinjournal.data.JournalDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteAllViewModel @Inject constructor(
    private val journalDao: JournalDao
) : ViewModel() {

    fun onConfirmDelete() = viewModelScope.launch {
        journalDao.deleteAllPosts()
    }
}