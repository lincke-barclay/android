package com.alth.events.data.authentication

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthenticationDataModule {
    @Binds
    abstract fun bindAuthenticationRepository(
        authenticationRepositoryImpl: FirebaseAuthenticationRepositoryImpl,
    ): FirebaseAuthenticationRepository

    @Binds
    abstract fun bindAuthenticationDataSource(
        authenticationDataSourceImpl: FirebaseAuthenticationDataSourceImpl,
    ): FirebaseAuthenticationDataSource
}
