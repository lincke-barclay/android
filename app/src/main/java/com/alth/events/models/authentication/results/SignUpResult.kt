package com.alth.events.models.authentication.results

sealed interface SignUpResult {
    data object Success : SignUpResult
    data object WeakPasswordException : SignUpResult
    data object EmailAddressMalformed : SignUpResult
    data object UserCollision : SignUpResult
}