package com.ezequielc.kotlinjournal.ui.addeditjournal

import androidx.lifecycle.*
import com.ezequielc.kotlinjournal.Event
import com.ezequielc.kotlinjournal.R
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

    // Snackbar message to be displayed based on Event, whether
    // JournalItem is added, updated or to handle empty post field
    private val _snackbartext = MutableLiveData<Event<Int>>()
    val snackbarMessage: LiveData<Event<Int>>
        get() = _snackbartext

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
            showSnackbarMessage(R.string.empty_post_message)
            return
        }

        if (journalItem != null) {
            val updatedJournalItem = journalItem.copy(title = journalTitle, post = journalPost)
            updateJournalItem(updatedJournalItem)
            showSnackbarMessage(R.string.journal_post_updated)
        } else {
            val newJournalItem = JournalItem(title = journalTitle, post = journalPost)
            insertJournalItem(newJournalItem)
            showSnackbarMessage(R.string.journal_post_added)
        }
    }

    private fun updateJournalItem(journalItem: JournalItem) = viewModelScope.launch {
        journalDao.update(journalItem)
    }

    private fun insertJournalItem(journalItem: JournalItem) = viewModelScope.launch {
        journalDao.insert(journalItem)
    }

    private fun showSnackbarMessage(message: Int) {
        _snackbartext.value = Event(message)
    }
}