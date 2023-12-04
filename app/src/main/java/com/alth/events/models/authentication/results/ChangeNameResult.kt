package com.alth.events.models.authentication.results

sealed interface ChangeNameResult {
    data object Success : ChangeNameResult
    data object InvalidUserException : ChangeNameResult
}