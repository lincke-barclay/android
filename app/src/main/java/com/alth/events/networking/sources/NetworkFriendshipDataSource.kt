package com.alth.events.networking.sources

import com.alth.events.models.domain.users.PublicUser
import com.alth.events.models.network.NetworkResult
import com.alth.events.models.network.users.ingress.GETPublicUserResponseDTO

interface NetworkFriendshipDataSource {
    suspend fun getMyConfirmedFriends(
        page: Int,
        pageSize: Int
    ): NetworkResult<List<GETPublicUserResponseDTO>>

    suspend fun getFriendsIRequested(
        page: Int,
        pageSize: Int
    ): NetworkResult<List<GETPublicUserResponseDTO>>

    suspend fun getFriendsRequestedToMe(
        page: Int,
        pageSize: Int
    ): NetworkResult<List<GETPublicUserResponseDTO>>

    suspend fun getSuggestedFriends(
        page: Int,
        pageSize: Int
    ): NetworkResult<List<GETPublicUserResponseDTO>>

    suspend fun postFriendship(publicUser: PublicUser): NetworkResult<Unit>
    suspend fun deleteFriendship(publicUser: PublicUser): NetworkResult<Unit>
}

