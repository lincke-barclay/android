package com.alth.events.database.dao

import com.alth.events.database.AppDatabase
import com.alth.events.database.dao.users.PublicUserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DaoModule {
    @Provides
    fun provideLastUpdateDao(
        appDatabase: AppDatabase,
    ): LastUpdateDao {
        return appDatabase.lastUpdatedDao()
    }

}