package com.alth.events.networking.sources.impl

import com.alth.events.authentication.sources.AuthenticationDataSource
import com.alth.events.models.domain.users.PublicUser
import com.alth.events.models.network.NetworkResult
import com.alth.events.models.network.users.ingress.GETPublicUserResponseDTO
import com.alth.events.networking.apis.FriendsApi
import com.alth.events.networking.sources.NetworkFriendshipDataSource
import com.alth.events.networking.withIDAndTokenOrThrowNetworkExec
import javax.inject.Inject

class RetrofitNetworkFriendshipDataSource @Inject constructor(
    private val authenticationDataSource: AuthenticationDataSource,
    private val friendsApi: FriendsApi,
) : NetworkFriendshipDataSource {
    override suspend fun getMyConfirmedFriends(
        page: Int,
        pageSize: Int,
    ): NetworkResult<List<GETPublicUserResponseDTO>> =
        authenticationDataSource.withIDAndTokenOrThrowNetworkExec { id, token ->
            friendsApi.getConfirmedFriends(
                token = token,
                userId = id,
                page = page,
                pageSize = pageSize,
            )
        }

    override suspend fun getFriendsIRequested(
        page: Int,
        pageSize: Int,
    ): NetworkResult<List<GETPublicUserResponseDTO>> =
        authenticationDataSource.withIDAndTokenOrThrowNetworkExec { id, token ->
            friendsApi.getFriendsIRequested(
                token = token,
                userId = id,
                page = page,
                pageSize = pageSize,
            )
        }

    override suspend fun getFriendsRequestedToMe(
        page: Int,
        pageSize: Int
    ): NetworkResult<List<GETPublicUserResponseDTO>> =
        authenticationDataSource.withIDAndTokenOrThrowNetworkExec { id, token ->
            friendsApi.getFriendsRequestedToMe(
                token = token,
                userId = id,
                page = page,
                pageSize = pageSize,
            )
        }

    override suspend fun getSuggestedFriends(
        page: Int,
        pageSize: Int
    ): NetworkResult<List<GETPublicUserResponseDTO>> =
        authenticationDataSource.withIDAndTokenOrThrowNetworkExec { id, token ->
            friendsApi.getSuggestedFriends(
                token = token,
                userId = id,
                page = page,
                pageSize = pageSize,
            )
        }

    override suspend fun postFriendship(publicUser: PublicUser): NetworkResult<Unit> =
        authenticationDataSource.withIDAndTokenOrThrowNetworkExec { id, token ->
            friendsApi.postFriendship(
                token = token,
                requesterId = id,
                recipientId = publicUser.id,
            )
        }

    override suspend fun deleteFriendship(publicUser: PublicUser): NetworkResult<Unit> =
        authenticationDataSource.withIDAndTokenOrThrowNetworkExec { id, token ->
            friendsApi.deleteFriendship(
                token = token,
                requesterId = id,
                toDeleteId = publicUser.id
            )
        }
}
