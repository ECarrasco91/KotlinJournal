package com.ezequielc.kotlinjournal.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [JournalItem::class], version = 1, exportSchema = false)
abstract class JournalDatabase : RoomDatabase() {
    abstract fun journalDao(): JournalDao
}