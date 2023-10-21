package com.alth.events.data.authentication.models

sealed interface AuthenticationState {
    data class UserOk(
        val name: String,
    ) : AuthenticationState

    data class UserUnverified(
        val name: String?,
    ) : AuthenticationState

    data class UserUninitialized(
        val name: String?,
    ) : AuthenticationState

    data object Unknown : AuthenticationState

    data object SignedOut : AuthenticationState
}

