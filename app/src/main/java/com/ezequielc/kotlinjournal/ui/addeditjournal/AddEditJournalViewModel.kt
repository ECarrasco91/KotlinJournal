package com.ezequielc.kotlinjournal.ui.addeditjournal

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.ezequielc.kotlinjournal.data.JournalDao
import com.ezequielc.kotlinjournal.data.JournalItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditJournalViewModel @Inject constructor(
    private val journalDao: JournalDao,
    private val state: SavedStateHandle
) : ViewModel() {

    // SavedStateHandle handles configuration changes, ex. rotations, etc.
    val journalItem = state.get<JournalItem>("journal_item")

    // If saved state of title is null, journalItem?.title is the saved state value
    // If journalItem?.title is null, saved state value is set to an empty string
    var journalTitle = state.get<String>("journal_title") ?: journalItem?.title ?: ""
        set(value) {
            field = value
            state.set("journal_title", value)
        }

    // If saved state of post is null, journalItem?.post is the saved state value
    // If journalItem?.post is null, saved state value is set to an empty string
    var journalPost = state.get<String>("journal_post") ?: journalItem?.post ?: ""
        set(value) {
            field = value
            state.set("journal_post", value)
        }

    // Function to save or update Journal Post
    fun saveJournalPost() {
        if (journalTitle.isBlank()) {
            journalTitle = "Untitled"
        }

        if (journalPost.isBlank()) {
            // TODO: Implement invalid input functionality
        }

        if (journalItem != null) {
            val updatedJournalItem = journalItem.copy(title = journalTitle, post = journalPost)
            updateJournalItem(updatedJournalItem)
        } else {
            val newJournalItem = JournalItem(title = journalTitle, post = journalPost)
            insertJournalItem(newJournalItem)
        }
    }

    private fun updateJournalItem(journalItem: JournalItem) = viewModelScope.launch {
        journalDao.update(journalItem)
    }

    private fun insertJournalItem(journalItem: JournalItem) = viewModelScope.launch {
        journalDao.insert(journalItem)
    }
}