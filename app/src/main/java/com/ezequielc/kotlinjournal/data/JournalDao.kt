package com.ezequielc.kotlinjournal.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(journalItem: JournalItem)

    @Update
    suspend fun update(journalItem: JournalItem)

    @Delete
    suspend fun delete(journalItem: JournalItem)

    @Query("DELETE FROM journal_table")
    suspend fun deleteAllPosts()

    @Query("SELECT * FROM journal_table")
    fun getJournalItems(): Flow<List<JournalItem>>
}