package com.alth.events.networking.sources

import com.alth.events.authentication.sources.AuthenticationDataSource
import com.alth.events.networking.models.NetworkResult
import com.alth.events.networking.models.users.ingress.PrivateUserResponseDto
import com.alth.events.networking.models.users.ingress.PublicUserResponseDto
import com.alth.events.networking.apis.UsersApi
import com.alth.events.networking.withIDAndTokenOrThrowNetworkExec
import kotlinx.serialization.json.Json
import javax.inject.Inject

class NetworkUserDataSource @Inject constructor(
    private val usersApi: UsersApi,
    private val authenticationDataSource: AuthenticationDataSource,
) {
    suspend fun getMe(): NetworkResult<PrivateUserResponseDto> =
        authenticationDataSource.withIDAndTokenOrThrowNetworkExec { id, token ->
            Json.decodeFromString(
                usersApi.getUser(
                    token = token,
                    userId = id,
                )
            )
        }

    suspend fun getNotMe(id: String): NetworkResult<PublicUserResponseDto> =
        authenticationDataSource.withIDAndTokenOrThrowNetworkExec { _, token ->
            Json.decodeFromString(
                usersApi.getUser(
                    token = token,
                    userId = id,
                )
            )
        }
}