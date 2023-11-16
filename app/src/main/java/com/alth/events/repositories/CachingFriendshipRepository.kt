package com.alth.events.repositories

import com.alth.events.models.network.users.ingress.PublicUserResponseDto

interface CachingFriendshipRepository {
    suspend fun getMyConfirmedFriends(
        page: Int,
        pageSize: Int
    ): GenericCachingOperation<List<PublicUserResponseDto>>

    suspend fun getPendingFriendsThatISent(
        page: Int,
        pageSize: Int
    ): GenericCachingOperation<List<PublicUserResponseDto>>

    suspend fun getPendingFriendsSentToMe(
        page: Int,
        pageSize: Int
    ): GenericCachingOperation<List<PublicUserResponseDto>>

    suspend fun getSuggestedFriends(
        page: Int,
        pageSize: Int,
        queryStr: String,
    ): GenericCachingOperation<List<PublicUserResponseDto>>

    suspend fun sendFriendRequest(recipientId: String)
    suspend fun deleteFriend(recipientId: String)
}
