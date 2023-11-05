package com.alth.events.repositories.impl

import com.alth.events.di.ApplicationScope
import com.alth.events.models.domain.users.PublicUser
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

    private var cachedConfirmedFriends: List<PublicUser> = emptyList()
    private var cachedPendingFriendsISent: List<PublicUser> = emptyList()
    private var cachedPendingFriendsSentToMe: List<PublicUser> = emptyList()
    private var cachedSuggestedFriends: List<PublicUser> = emptyList()

    override suspend fun getMyConfirmedFriends(
        page: Int,
        pageSize: Int
    ): GenericCachingOperation<List<PublicUser>> {
        return object : GenericCachingOperation<List<PublicUser>>(
            appScope,
            listOf(),
        ) {
            override suspend fun getFromSourceOfTruth(): InternalCacheResult<List<PublicUser>> {
                return networkFriendshipDataSource.getMyConfirmedFriends(page, pageSize)
                    .toInternalCacheResult {
                        map { it.toPublicUser() }
                    }
            }

            override suspend fun getFromCache(): InternalCacheResult<List<PublicUser>> {
                return InternalCacheResult.Success(cachedConfirmedFriends)
            }

            override suspend fun saveToCache(t: List<PublicUser>): InternalCacheResult<Unit> {
                cachedConfirmedFriends = t
                return InternalCacheResult.Success(Unit)
            }
        }
    }

    override suspend fun getPendingFriendsThatISent(
        page: Int,
        pageSize: Int
    ) = object : GenericCachingOperation<List<PublicUser>>(appScope, listOf()) {
        override suspend fun getFromSourceOfTruth(): InternalCacheResult<List<PublicUser>> {
            return networkFriendshipDataSource.getFriendsIRequested(page, pageSize)
                .toInternalCacheResult {
                    map { it.toPublicUser() }
                }
        }

        override suspend fun getFromCache(): InternalCacheResult<List<PublicUser>> {
            return InternalCacheResult.Success(cachedPendingFriendsISent)
        }

        override suspend fun saveToCache(t: List<PublicUser>): InternalCacheResult<Unit> {
            cachedPendingFriendsISent = t
            return InternalCacheResult.Success(Unit)
        }
    }

    override suspend fun getPendingFriendsSentToMe(
        page: Int,
        pageSize: Int
    ) = object : GenericCachingOperation<List<PublicUser>>(appScope, listOf()) {
        override suspend fun getFromSourceOfTruth(): InternalCacheResult<List<PublicUser>> {
            return networkFriendshipDataSource.getFriendsRequestedToMe(page, pageSize)
                .toInternalCacheResult {
                    map { it.toPublicUser() }
                }
        }

        override suspend fun getFromCache(): InternalCacheResult<List<PublicUser>> {
            return InternalCacheResult.Success(cachedPendingFriendsSentToMe)
        }

        override suspend fun saveToCache(t: List<PublicUser>): InternalCacheResult<Unit> {
            cachedPendingFriendsSentToMe = t
            return InternalCacheResult.Success(Unit)
        }
    }

    override suspend fun getSuggestedFriends(
        page: Int,
        pageSize: Int
    ) = object : GenericCachingOperation<List<PublicUser>>(appScope, listOf()) {
        override suspend fun getFromSourceOfTruth(): InternalCacheResult<List<PublicUser>> {
            return networkFriendshipDataSource.getSuggestedFriends(page, pageSize)
                .toInternalCacheResult {
                    map { it.toPublicUser() }
                }
        }

        override suspend fun getFromCache(): InternalCacheResult<List<PublicUser>> {
            return InternalCacheResult.Success(cachedSuggestedFriends)
        }

        override suspend fun saveToCache(t: List<PublicUser>): InternalCacheResult<Unit> {
            cachedSuggestedFriends = t
            return InternalCacheResult.Success(Unit)
        }
    }

    override suspend fun postFriend(publicUser: PublicUser) {
        networkFriendshipDataSource.postFriendship(publicUser)
    }

    override suspend fun deleteFriend(publicUser: PublicUser) {
        networkFriendshipDataSource.deleteFriendship(publicUser)
    }
}
