package com.ezequielc.kotlinjournal.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
@Entity(tableName = "journal_table")
data class JournalItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val post: String,
    val timestamp: Long = System.currentTimeMillis()
) : Parcelable {
    val timestampFormatted: String
        get() = formatTimestamp(timestamp)

    private fun formatTimestamp(timestamp: Long): String {
        val pattern = "MMM dd, yyyy h:mm:ss a"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val date = Date(timestamp)
        return simpleDateFormat.format(date)
    }
}
