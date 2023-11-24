package com.alth.events.data.repositories.impl

import com.alth.events.authentication.sources.AuthenticationDataSource
import com.alth.events.exceptions.IllegalAuthenticationStateException
import com.alth.events.data.repositories.AuthenticationRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAuthenticationRepository @Inject constructor(
    private val authenticationDataSource: AuthenticationDataSource,
) : AuthenticationRepository {

    override val authenticationState = authenticationDataSource
        .currentlySignedInUser
        .map { state -> state.toDomain() }


    override fun signIn(email: String, password: String) {
        authenticationDataSource.signIn(email, password)
    }

    override fun signUp(email: String, password: String) {
        authenticationDataSource.signUp(email, password)
    }

    override fun signOut() {
        authenticationDataSource.signOut()
    }

    override fun sendVerificationEmailForCurrentlySignedInUser() {
        authenticationDataSource.sendVerificationEmailForCurrentlySignedInUser()
    }

    override fun changeNameOfCurrentlySignedInUser(newName: String) {
        authenticationDataSource.changeNameOfCurrentlySignedInUser(newName)
    }

    override fun reload() {
        authenticationDataSource.reload()
    }

    override fun getCurrentlySignedInUserIdOrThrow(errorMessage: String): String {
        return authenticationDataSource.getSignedInUserIdOrNull()
            ?: throw IllegalAuthenticationStateException(errorMessage)
    }
}

