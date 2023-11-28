package com.alth.events.models.domain.authentication.results

sealed interface ReloadResult {
    data object Success : ReloadResult
    data object InvalidAccount : ReloadResult
}