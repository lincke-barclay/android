package com.alth.events.repositories.impl

import com.alth.events.di.ApplicationScope
import com.alth.events.models.network.users.ingress.PublicUserResponseDto
import com.alth.events.networking.sources.NetworkFriendshipDataSource
import com.alth.events.repositories.CachingFriendshipRepository
import com.alth.events.repositories.GenericCachingOperation
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InMemoryCachingFriendshipRepository @Inject constructor(
    private val networkFriendshipDataSource: NetworkFriendshipDataSource,
    @ApplicationScope private val appScope: CoroutineScope,
) : CachingFriendshipRepository {

    private var cachedConfirmedFriends: List<PublicUserResponseDto> = emptyList()
    private var cachedPendingFriendsISent: List<PublicUserResponseDto> = emptyList()
    private var cachedPendingFriendsSentToMe: List<PublicUserResponseDto> = emptyList()
    private var cachedSuggestedFriends: List<PublicUserResponseDto> = emptyList()

    override suspend fun getMyConfirmedFriends(
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

    override suspend fun getPendingFriendsThatISent(
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

    override suspend fun getPendingFriendsSentToMe(
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

    override suspend fun getSuggestedFriends(
        page: Int,
        pageSize: Int,
        queryStr: String,
    ) = object : GenericCachingOperation<List<PublicUserResponseDto>>(appScope, listOf()) {
        override suspend fun getFromSourceOfTruth(): InternalCacheResult<List<PublicUserResponseDto>> {
            return networkFriendshipDataSource.getSuggestedFriends(page, pageSize, queryStr)
                .toInternalCacheResult()
        }

        override suspend fun getFromCache(): InternalCacheResult<List<PublicUserResponseDto>> {
            return InternalCacheResult.Success(cachedSuggestedFriends)
        }

        override suspend fun saveToCache(t: List<PublicUserResponseDto>): InternalCacheResult<Unit> {
            cachedSuggestedFriends = t
            return InternalCacheResult.Success(Unit)
        }
    }

    override suspend fun sendFriendRequest(recipientId: String) {
        networkFriendshipDataSource.postFriendship(recipientId)
    }

    override suspend fun deleteFriend(recipientId: String) {
        networkFriendshipDataSource.deleteFriendship(recipientId)
    }
}
