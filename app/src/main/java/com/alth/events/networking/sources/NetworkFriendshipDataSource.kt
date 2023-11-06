package com.alth.events.networking.sources

import com.alth.events.models.network.NetworkResult
import com.alth.events.models.network.users.ingress.PublicUserResponseDto

interface NetworkFriendshipDataSource {
    suspend fun getMyConfirmedFriends(
        page: Int,
        pageSize: Int
    ): NetworkResult<List<PublicUserResponseDto>>

    suspend fun getFriendsIRequested(
        page: Int,
        pageSize: Int
    ): NetworkResult<List<PublicUserResponseDto>>

    suspend fun getFriendsRequestedToMe(
        page: Int,
        pageSize: Int
    ): NetworkResult<List<PublicUserResponseDto>>

    suspend fun getSuggestedFriends(
        page: Int,
        pageSize: Int
    ): NetworkResult<List<PublicUserResponseDto>>

    suspend fun postFriendship(recipientId: String): NetworkResult<Unit>
    suspend fun deleteFriendship(recipientId: String): NetworkResult<Unit>
}

