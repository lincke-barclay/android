package com.alth.events.authentication.sources

import com.alth.events.models.firebase.FirebaseAuthenticationState
import kotlinx.coroutines.flow.Flow

interface AuthenticationDataSource {
    val currentlySignedInUser: Flow<FirebaseAuthenticationState>
    fun signIn(email: String, password: String)
    fun signUp(email: String, password: String)
    fun signOut()
    fun reload()
    fun sendVerificationEmailForCurrentlySignedInUser()
    fun changeNameOfCurrentlySignedInUser(newName: String)
    fun getSignedInUserIdOrNull(): String?
    suspend fun getAuthenticationTokenOrNull(forceRefresh: Boolean = true): String?
}
