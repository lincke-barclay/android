package com.alth.events.networking.sources.impl

import com.alth.events.authentication.sources.AuthenticationDataSource
import com.alth.events.models.network.NetworkResult
import com.alth.events.models.network.users.ingress.PrivateUserResponseDto
import com.alth.events.models.network.users.ingress.PublicUserResponseDto
import com.alth.events.networking.apis.UsersApi
import com.alth.events.networking.sources.NetworkUserDataSource
import com.alth.events.networking.withIDAndTokenOrThrowNetworkExec
import kotlinx.serialization.json.Json
import javax.inject.Inject

class RetrofitNetworkUserDataSource @Inject constructor(
    private val usersApi: UsersApi,
    private val authenticationDataSource: AuthenticationDataSource,
) : NetworkUserDataSource {
    override suspend fun getMe(): NetworkResult<PrivateUserResponseDto> =
        authenticationDataSource.withIDAndTokenOrThrowNetworkExec { id, token ->
            Json.decodeFromString(
                usersApi.getUser(
                    token = token,
                    userId = id,
                )
            )
        }

    override suspend fun getNotMe(id: String): NetworkResult<PublicUserResponseDto> =
        authenticationDataSource.withIDAndTokenOrThrowNetworkExec { _, token ->
            Json.decodeFromString(
                usersApi.getUser(
                    token = token,
                    userId = id,
                )
            )
        }
}