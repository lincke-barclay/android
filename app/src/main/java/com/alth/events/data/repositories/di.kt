package com.alth.events.data.repositories

import com.alth.events.data.repositories.impl.FirebaseAuthenticationRepository
import com.alth.events.data.repositories.AuthenticationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindAuthenticationRepository(
        ar: FirebaseAuthenticationRepository,
    ): AuthenticationRepository
}