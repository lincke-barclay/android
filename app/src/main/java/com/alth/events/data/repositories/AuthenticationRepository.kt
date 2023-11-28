package com.alth.events.data.repositories

import com.alth.events.authentication.sources.AuthenticationDataSource
import com.alth.events.logging.impl.loggerFactory
import com.alth.events.models.domain.authentication.AuthenticationState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(
    private val authenticationDataSource: AuthenticationDataSource,
) {
    private val logger = loggerFactory.getLogger(this)
    val authenticationState: Flow<AuthenticationState> =
        authenticationDataSource
            .currentlySignedInUser
            .onEach { logger.debug("Authentication State Changed - new state: $it") }
            .map { it.toDomain() }
            .onEach { logger.debug("Transformed to domain state: $it") }

    suspend fun signIn(email: String, password: String) =
        authenticationDataSource.signIn(email, password)

    suspend fun signUp(email: String, password: String) =
        authenticationDataSource.signUp(email, password)

    fun signOut() = authenticationDataSource.signOut()

    suspend fun sendVerificationEmailForCurrentlySignedInUser() =
        authenticationDataSource.sendVerificationEmailForCurrentlySignedInUser()

    suspend fun changeNameOfCurrentlySignedInUser(newName: String) =
        authenticationDataSource.changeNameOfCurrentlySignedInUser(newName)
}
