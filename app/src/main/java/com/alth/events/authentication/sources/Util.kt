package com.alth.events.authentication.sources

import com.alth.events.exceptions.IllegalAuthenticationStateException

suspend fun <T> AuthenticationDataSource.withTokenOrThrow(body: suspend (String) -> T): T {
    return getAuthenticationTokenOrNull()?.let {
        body(it)
    } ?: throw IllegalAuthenticationStateException("User is not signed in!")
}

suspend fun <T> AuthenticationDataSource.withIDOrThrow(body: suspend (String) -> T): T {
    return getSignedInUserIdOrNull()?.let {
        body(it)
    } ?: throw IllegalAuthenticationStateException("User is not signed in!")
}

suspend fun <T> AuthenticationDataSource.withIDAndTokenOrThrow(body: suspend (id: String, token: String) -> T): T {
    return withTokenOrThrow { token ->
        withIDOrThrow { id ->
            body(id, token)
        }
    }
}


