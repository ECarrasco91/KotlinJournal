package com.ezequielc.kotlinjournal.ui.journal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ezequielc.kotlinjournal.data.JournalDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class JournalViewModel @Inject constructor(
    private val journalDao: JournalDao
) : ViewModel() {

    val posts = journalDao.getJournalItems().asLiveData()
}