package com.alth.events.data.authentication

import com.alth.events.data.authentication.models.AuthenticationState
import com.alth.events.exceptions.IllegalAuthenticationStateException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface FirebaseAuthenticationRepository {
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

class FirebaseAuthenticationRepositoryImpl @Inject constructor(
    private val firebaseAuthenticationDataSource: FirebaseAuthenticationDataSource,
) : FirebaseAuthenticationRepository {

    override val authenticationState = firebaseAuthenticationDataSource
        .currentlySignedInUser
        .map { state -> transformAuthState(state) }


    override fun signIn(email: String, password: String) {
        firebaseAuthenticationDataSource.signIn(email, password)
    }

    override fun signUp(email: String, password: String) {
        firebaseAuthenticationDataSource.signUp(email, password)
    }

    override fun signOut() {
        firebaseAuthenticationDataSource.signOut()
    }

    override fun sendVerificationEmailForCurrentlySignedInUser() {
        firebaseAuthenticationDataSource.sendVerificationEmailForCurrentlySignedInUser()
    }

    override fun changeNameOfCurrentlySignedInUser(newName: String) {
        firebaseAuthenticationDataSource.changeNameOfCurrentlySignedInUser(newName)
    }

    override fun reload() {
        firebaseAuthenticationDataSource.reload()
    }

    override fun getCurrentlySignedInUserIdOrThrow(errorMessage: String): String {
        return firebaseAuthenticationDataSource.getSignedInUserIdOrNull()
            ?: throw IllegalAuthenticationStateException(errorMessage)
    }
}

private fun transformAuthState(state: FirebaseAuthenticationState): AuthenticationState {
    state.let {
        return when (it) {
            is FirebaseAuthenticationState.SignedIn -> {
                if (!it.user.isEmailVerified) {
                    AuthenticationState.UserUnverified(null)
                } else {
                    it.user.displayName?.let { name ->
                        AuthenticationState.UserOk(name)
                    } ?: AuthenticationState.UserUninitialized(null)
                }
            }

            is FirebaseAuthenticationState.SignedOut -> {
                AuthenticationState.SignedOut
            }

            is FirebaseAuthenticationState.Unknown -> {
                AuthenticationState.Unknown
            }
        }
    }
}
