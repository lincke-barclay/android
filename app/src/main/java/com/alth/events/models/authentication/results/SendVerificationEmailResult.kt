package com.alth.events.models.authentication.results

sealed interface SendVerificationEmailResult {
    data object Success : SendVerificationEmailResult
}