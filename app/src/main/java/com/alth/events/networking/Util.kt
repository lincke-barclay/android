package com.alth.events.networking

import com.alth.events.authentication.sources.AuthenticationDataSource
import com.alth.events.authentication.sources.withIDAndTokenOrThrow
import com.alth.events.authentication.sources.withTokenOrThrow
import com.alth.events.networking.models.NetworkResult
import java.io.IOException

suspend fun <T> runNetworkCatching(block: suspend () -> T): NetworkResult<T> {
    return try {
        NetworkResult.Success(block())
    } catch (e: IOException) {
        NetworkResult.IOFailure(e)
    }
}

suspend fun <T> AuthenticationDataSource.withIDAndTokenOrThrowNetworkExec(
    block: suspend (id: String, token: String) -> T
): NetworkResult<T> {
    return withIDAndTokenOrThrow { id, token ->
        runNetworkCatching {
            block(id, "Bearer $token")
        }
    }
}

suspend fun <T> AuthenticationDataSource.withTokenOrThrowNetworkExec(
    block: suspend (token: String) -> T
): NetworkResult<T> {
    return withTokenOrThrow { token ->
        runNetworkCatching {
            block("Bearer $token")
        }
    }
}
