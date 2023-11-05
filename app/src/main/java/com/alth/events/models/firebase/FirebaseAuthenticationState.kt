package com.alth.events.models.firebase

import com.alth.events.models.domain.authentication.AuthenticationState
import com.google.firebase.auth.FirebaseUser

sealed interface FirebaseAuthenticationState {
    data object Unknown : FirebaseAuthenticationState
    data object SignedOut : FirebaseAuthenticationState
    data class SignedIn(
        val user: FirebaseUser
    ) : FirebaseAuthenticationState

    fun toDomain() = when (this) {
        is SignedIn -> {
            if (!user.isEmailVerified) {
                AuthenticationState.UserUnverified(null)
            } else {
                user.displayName?.let { name ->
                    AuthenticationState.UserOk(name)
                } ?: AuthenticationState.UserUninitialized(null)
            }
        }

        is SignedOut -> {
            AuthenticationState.SignedOut
        }

        is Unknown -> {
            AuthenticationState.Unknown
        }
    }
}
