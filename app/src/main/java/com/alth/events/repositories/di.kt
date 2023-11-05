package com.alth.events.repositories

import com.alth.events.repositories.impl.FirebaseAuthenticationRepository
import com.alth.events.repositories.impl.InMemoryCachingEventsRepository
import com.alth.events.repositories.impl.InMemoryCachingFriendshipRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindFriendRepository(
        frp: InMemoryCachingFriendshipRepository,
    ): CachingFriendshipRepository

    @Binds
    abstract fun bindEventRepository(
        er: InMemoryCachingEventsRepository,
    ): CachingEventRepository

    @Binds
    abstract fun bindAuthenticationRepository(
        ar: FirebaseAuthenticationRepository,
    ): AuthenticationRepository
}