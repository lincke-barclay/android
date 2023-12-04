package com.alth.events.models.authentication

sealed interface AuthenticationState {
    data class UserOk(
        val name: String,
    ) : AuthenticationState

    data class UserUnverified(
        val name: String?,
    ) : AuthenticationState

    data object UsernameNotSet : AuthenticationState

    data object Unknown : AuthenticationState

    data object SignedOut : AuthenticationState
}
