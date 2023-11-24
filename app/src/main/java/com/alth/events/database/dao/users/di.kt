package com.alth.events.database.dao.users

import com.alth.events.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UserDaoModule {
    @Provides
    fun providePublicUserDao(
        appDatabase: AppDatabase,
    ): PublicUserDao {
        return appDatabase.publicUserDao()
    }

    @Provides
    fun provideFriendshipDao(
        appDatabase: AppDatabase,
    ): FriendshipDao {
        return appDatabase.friendshipDao()
    }
}