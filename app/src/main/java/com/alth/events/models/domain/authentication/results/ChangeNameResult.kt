package com.alth.events.models.domain.authentication.results

sealed interface ChangeNameResult {
    data object Success : ChangeNameResult
    data object InvalidUserException : ChangeNameResult
}