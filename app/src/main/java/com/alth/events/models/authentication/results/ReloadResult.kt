package com.alth.events.models.authentication.results

sealed interface ReloadResult {
    data object Success : ReloadResult
    data object InvalidAccount : ReloadResult
}