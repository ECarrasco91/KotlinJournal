package com.ezequielc.kotlinjournal.di

import android.app.Application
import androidx.room.Room
import com.ezequielc.kotlinjournal.data.JournalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application) = Room.databaseBuilder(
        application, JournalDatabase::class.java, "journal_db"
    ).build()

    @Provides
    @Singleton
    fun provideJournalDao(db: JournalDatabase) = db.journalDao()
}