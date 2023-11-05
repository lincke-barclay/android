package com.alth.events.repositories

import com.alth.events.models.domain.users.PublicUser

interface CachingFriendshipRepository {
    suspend fun getMyConfirmedFriends(
        page: Int,
        pageSize: Int
    ): GenericCachingOperation<List<PublicUser>>

    suspend fun getPendingFriendsThatISent(
        page: Int,
        pageSize: Int
    ): GenericCachingOperation<List<PublicUser>>

    suspend fun getPendingFriendsSentToMe(
        page: Int,
        pageSize: Int
    ): GenericCachingOperation<List<PublicUser>>

    suspend fun getSuggestedFriends(
        page: Int,
        pageSize: Int
    ): GenericCachingOperation<List<PublicUser>>

    suspend fun postFriend(publicUser: PublicUser)
    suspend fun deleteFriend(publicUser: PublicUser)
}
