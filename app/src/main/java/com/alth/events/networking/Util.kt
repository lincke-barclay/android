package com.alth.events.networking

import com.alth.events.authentication.sources.AuthenticationDataSource
import com.alth.events.authentication.sources.withIDAndTokenOrThrow
import com.alth.events.models.network.NetworkResult
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