package com.alth.events.networking.sources

import com.alth.events.authentication.sources.AuthenticationDataSource
import com.alth.events.networking.apis.FriendsApi
import com.alth.events.networking.models.NetworkResult
import com.alth.events.networking.withIDAndTokenOrThrowNetworkExec
import javax.inject.Inject

class NetworkFriendshipDataSource @Inject constructor(
    private val authenticationDataSource: AuthenticationDataSource,
    private val friendsApi: FriendsApi,
) {
    suspend fun getMyConfirmedFriends(
        page: Int,
        pageSize: Int,
    ) = authenticationDataSource.withIDAndTokenOrThrowNetworkExec { id, token ->
        friendsApi.getConfirmedFriends(
            token = token,
            userId = id,
            page = page,
            pageSize = pageSize,
        )
    }

    suspend fun getFriendsIRequested(
        page: Int,
        pageSize: Int,
    ) = authenticationDataSource.withIDAndTokenOrThrowNetworkExec { id, token ->
        friendsApi.getFriendsIRequested(
            token = token,
            userId = id,
            page = page,
            pageSize = pageSize,
        )
    }

    suspend fun getFriendsRequestedToMe(
        page: Int,
        pageSize: Int
    ) = authenticationDataSource.withIDAndTokenOrThrowNetworkExec { id, token ->
        friendsApi.getFriendsRequestedToMe(
            token = token,
            userId = id,
            page = page,
            pageSize = pageSize,
        )
    }

    suspend fun postFriendship(recipientId: String): NetworkResult<Unit> =
        authenticationDataSource.withIDAndTokenOrThrowNetworkExec { id, token ->
            friendsApi.postFriendship(
                token = token,
                requesterId = id,
                recipientId = recipientId,
            )
        }

    suspend fun deleteFriendship(recipientId: String): NetworkResult<Unit> =
        authenticationDataSource.withIDAndTokenOrThrowNetworkExec { id, token ->
            friendsApi.deleteFriendship(
                token = token,
                requesterId = id,
                toDeleteId = recipientId,
            )
        }
}
