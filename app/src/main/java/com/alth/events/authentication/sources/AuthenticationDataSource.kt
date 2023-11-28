package com.alth.events.authentication.sources

import com.alth.events.authentication.models.FirebaseAuthenticationState
import com.alth.events.models.domain.authentication.results.ChangeNameResult
import com.alth.events.models.domain.authentication.results.ReloadResult
import com.alth.events.models.domain.authentication.results.SendVerificationEmailResult
import com.alth.events.models.domain.authentication.results.SignInResult
import com.alth.events.models.domain.authentication.results.SignUpResult
import kotlinx.coroutines.flow.Flow

interface AuthenticationDataSource {
    val currentlySignedInUser: Flow<FirebaseAuthenticationState>
    suspend fun signIn(email: String, password: String): SignInResult
    suspend fun signUp(email: String, password: String): SignUpResult
    fun signOut()
    suspend fun reload(): ReloadResult
    suspend fun sendVerificationEmailForCurrentlySignedInUser(): SendVerificationEmailResult
    suspend fun changeNameOfCurrentlySignedInUser(newName: String): ChangeNameResult
    fun getSignedInUserIdOrNull(): String?
    suspend fun getAuthenticationTokenOrNull(forceRefresh: Boolean = true): String?
}
