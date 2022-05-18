package com.ezequielc.kotlinjournal.ui.journal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ezequielc.kotlinjournal.data.JournalDao
import com.ezequielc.kotlinjournal.data.JournalItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JournalViewModel @Inject constructor(
    private val journalDao: JournalDao
) : ViewModel() {

    val posts = journalDao.getJournalItems().asLiveData()

    fun onJournalItemSwiped(journalItem: JournalItem) = viewModelScope.launch {
        journalDao.delete(journalItem)
    }

    fun onUndoDeleteClick(journalItem: JournalItem) = viewModelScope.launch {
        journalDao.insert(journalItem)
    }
}