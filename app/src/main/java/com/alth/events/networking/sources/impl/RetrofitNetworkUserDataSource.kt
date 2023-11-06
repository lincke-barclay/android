package com.alth.events.networking.sources.impl

import com.alth.events.authentication.sources.AuthenticationDataSource
import com.alth.events.models.network.NetworkResult
import com.alth.events.models.network.users.ingress.PrivateUserResponseDto
import com.alth.events.networking.apis.UsersApi
import com.alth.events.networking.sources.NetworkUserDataSource
import com.alth.events.networking.withIDAndTokenOrThrowNetworkExec
import javax.inject.Inject

class RetrofitNetworkUserDataSource @Inject constructor(
    private val usersApi: UsersApi,
    private val authenticationDataSource: AuthenticationDataSource,
) : NetworkUserDataSource {
    override suspend fun getMe(): NetworkResult<PrivateUserResponseDto> =
        authenticationDataSource.withIDAndTokenOrThrowNetworkExec { id, token ->

            usersApi.getMe(
                token = token,
                userId = id,
            )
        }
}