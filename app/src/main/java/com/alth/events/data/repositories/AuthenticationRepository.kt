package com.alth.events.data.repositories

import com.alth.events.models.domain.authentication.AuthenticationState
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    val authenticationState: Flow<AuthenticationState>
    fun signIn(email: String, password: String)
    fun signUp(email: String, password: String)
    fun signOut()
    fun reload()
    fun sendVerificationEmailForCurrentlySignedInUser()
    fun changeNameOfCurrentlySignedInUser(newName: String)
    fun getCurrentlySignedInUserIdOrThrow(
        errorMessage: String = "Can't get user id because user isn't signed in",
    ): String
}
