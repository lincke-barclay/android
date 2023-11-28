package com.alth.events.models.domain.authentication.results

sealed interface SendVerificationEmailResult {
    data object Success : SendVerificationEmailResult
}