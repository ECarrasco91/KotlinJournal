package com.ezequielc.kotlinjournal.ui.journal

import com.ezequielc.kotlinjournal.data.JournalItem

/**
 * Listener used to process when user clicks on item in recyclerview
 */
interface OnItemClickListener {

    fun onItemClick(journalItem: JournalItem)
}