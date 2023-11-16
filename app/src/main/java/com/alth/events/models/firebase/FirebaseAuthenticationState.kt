package com.alth.events.models.firebase

import com.alth.events.logging.impl.loggerFactory
import com.alth.events.models.domain.authentication.AuthenticationState
import com.google.firebase.auth.FirebaseUser

sealed interface FirebaseAuthenticationState {
    data object Unknown : FirebaseAuthenticationState
    data object SignedOut : FirebaseAuthenticationState
    data class SignedIn(
        val user: FirebaseUser
    ) : FirebaseAuthenticationState

    fun toDomain() = run {
        when (this) {
            is SignedIn -> {
                loggerFactory.getLogger(this).debug("Signed in user state is $user")
                if (!user.isEmailVerified) {
                    loggerFactory.getLogger(this).debug("No email set - user is not initialized")
                    AuthenticationState.UserUnverified(null)
                } else {
                    if (user.displayName == null || user.displayName!!.isEmpty()) {
                        loggerFactory.getLogger(this).debug("No name set - user is not initialized")
                        AuthenticationState.UserUninitialized(null)
                    } else {
                        loggerFactory.getLogger(this)
                            .debug("User is ok with name ${user.displayName}")
                        AuthenticationState.UserOk(user.displayName!!)
                    }
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
}
