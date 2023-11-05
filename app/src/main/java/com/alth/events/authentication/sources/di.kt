package com.alth.events.authentication.sources

import com.alth.events.authentication.sources.impl.FirebaseAuthenticationDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthenticationDataSourceModule {
    @Binds
    abstract fun bindAuthenticationDataSource(
        firebaseAuthenticationDataSourceImpl: FirebaseAuthenticationDataSourceImpl,
    ): AuthenticationDataSource
}
