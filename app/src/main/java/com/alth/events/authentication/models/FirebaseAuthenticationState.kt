package com.alth.events.authentication.models

import com.alth.events.logging.impl.loggerFactory
import com.alth.events.models.domain.authentication.AuthenticationState
import com.google.firebase.auth.FirebaseUser

sealed interface FirebaseAuthenticationState {
    data object Unknown : FirebaseAuthenticationState
    data object SignedOut : FirebaseAuthenticationState
    data class SignedIn(
        val user: FirebaseUser
    ) : FirebaseAuthenticationState

    fun toDomain(): AuthenticationState {
        val logger = loggerFactory.getLogger(tag = "FirebaseToDomainAccountState")
        return when (this) {
            is SignedIn -> {
                logger.debug("Signed in user state is $user")
                if (!user.isEmailVerified) {
                    logger.debug("No email is set - user is not initialized")
                    AuthenticationState.UserUnverified(null)
                } else {
                    if (user.displayName == null || user.displayName!!.isEmpty()) {
                        logger.debug("No name is set - user is not initialized")
                        AuthenticationState.UsernameNotSet
                    } else {
                        logger.debug(
                            "User is ok with name ${user.displayName} " +
                                    "and photo url: ${user.photoUrl}",
                        )
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
