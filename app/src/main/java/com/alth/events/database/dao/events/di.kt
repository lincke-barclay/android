package com.alth.events.database.dao.events

import com.alth.events.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class EventDaoModule {
    @Provides
    fun provideEventDao(
        appDatabase: AppDatabase,
    ): EventDao {
        return appDatabase.eventDao()
    }

    @Provides
    fun provideEventForUserDao(
        appDatabase: AppDatabase,
    ): EventForUserDao {
        return appDatabase.eventForUserDao()
    }

    @Provides
    fun provideFeedDao(
        appDatabase: AppDatabase,
    ): FeedDao {
        return appDatabase.feedDao()
    }

    @Provides
    fun provideSearchEventDao(
        appDatabase: AppDatabase,
    ): SearchEventDao {
        return appDatabase.searchEventDao()
    }
}