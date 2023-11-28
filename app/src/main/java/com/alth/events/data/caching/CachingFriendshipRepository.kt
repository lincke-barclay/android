package com.alth.events.data.caching

import com.alth.events.di.ApplicationScope
import com.alth.events.networking.models.users.ingress.PublicUserResponseDto
import com.alth.events.networking.sources.NetworkFriendshipDataSource
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CachingFriendshipRepository @Inject constructor(
    private val networkFriendshipDataSource: NetworkFriendshipDataSource,
    @ApplicationScope private val appScope: CoroutineScope,
) {

    private var cachedConfirmedFriends: List<PublicUserResponseDto> = emptyList()
    private var cachedPendingFriendsISent: List<PublicUserResponseDto> = emptyList()
    private var cachedPendingFriendsSentToMe: List<PublicUserResponseDto> = emptyList()
    private var cachedSuggestedFriends: List<PublicUserResponseDto> = emptyList()

    suspend fun getMyConfirmedFriends(
        page: Int,
        pageSize: Int
    ): GenericCachingOperation<List<PublicUserResponseDto>> {
        return object : GenericCachingOperation<List<PublicUserResponseDto>>(
            appScope,
            listOf(),
        ) {
            override suspend fun getFromSourceOfTruth(): InternalCacheResult<List<PublicUserResponseDto>> {
                return networkFriendshipDataSource.getMyConfirmedFriends(page, pageSize)
                    .toInternalCacheResult()
            }

            override suspend fun getFromCache(): InternalCacheResult<List<PublicUserResponseDto>> {
                return InternalCacheResult.Success(cachedConfirmedFriends)
            }

            override suspend fun saveToCache(t: List<PublicUserResponseDto>): InternalCacheResult<Unit> {
                cachedConfirmedFriends = t
                return InternalCacheResult.Success(Unit)
            }
        }
    }

    suspend fun getPendingFriendsThatISent(
        page: Int,
        pageSize: Int
    ) = object : GenericCachingOperation<List<PublicUserResponseDto>>(appScope, listOf()) {
        override suspend fun getFromSourceOfTruth(): InternalCacheResult<List<PublicUserResponseDto>> {
            return networkFriendshipDataSource.getFriendsIRequested(page, pageSize)
                .toInternalCacheResult()
        }

        override suspend fun getFromCache(): InternalCacheResult<List<PublicUserResponseDto>> {
            return InternalCacheResult.Success(cachedPendingFriendsISent)
        }

        override suspend fun saveToCache(t: List<PublicUserResponseDto>): InternalCacheResult<Unit> {
            cachedPendingFriendsISent = t
            return InternalCacheResult.Success(Unit)
        }
    }

    suspend fun getPendingFriendsSentToMe(
        page: Int,
        pageSize: Int
    ) = object : GenericCachingOperation<List<PublicUserResponseDto>>(appScope, listOf()) {
        override suspend fun getFromSourceOfTruth(): InternalCacheResult<List<PublicUserResponseDto>> {
            return networkFriendshipDataSource.getFriendsRequestedToMe(page, pageSize)
                .toInternalCacheResult()
        }

        override suspend fun getFromCache(): InternalCacheResult<List<PublicUserResponseDto>> {
            return InternalCacheResult.Success(cachedPendingFriendsSentToMe)
        }

        override suspend fun saveToCache(t: List<PublicUserResponseDto>): InternalCacheResult<Unit> {
            cachedPendingFriendsSentToMe = t
            return InternalCacheResult.Success(Unit)
        }
    }

    suspend fun sendFriendRequest(recipientId: String) {
        networkFriendshipDataSource.postFriendship(recipientId)
    }

    suspend fun deleteFriend(recipientId: String) {
        networkFriendshipDataSource.deleteFriendship(recipientId)
    }
}
