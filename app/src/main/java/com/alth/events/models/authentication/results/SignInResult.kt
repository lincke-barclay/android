package com.alth.events.models.authentication.results

sealed interface SignInResult {
    data object Success : SignInResult
    data object ThatUserDoesntExist : SignInResult
    data object PasswordIsIncorrect : SignInResult
}