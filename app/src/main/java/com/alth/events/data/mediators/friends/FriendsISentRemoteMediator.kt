package com.alth.events.data.mediators.friends

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.alth.events.data.caching.friends.PendingFriendsISentCachingManager
import com.alth.events.database.models.users.PublicUserEntity
import com.alth.events.database.sources.friendships.FriendshipLocalDataSource
import com.alth.events.logging.impl.loggerFactory
import com.alth.events.models.domain.friends.FriendsQuery
import com.alth.events.networking.models.NetworkResult
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class FriendsISentRemoteMediator @Inject constructor(
    private val pendingFriendsISentCachingManager: PendingFriendsISentCachingManager,
    private val friendshipLocalDataSource: FriendshipLocalDataSource,
) : RemoteMediator<Int, PublicUserEntity>() {

    private val logger = loggerFactory.getLogger(this)

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PublicUserEntity>
    ): MediatorResult {
        val nextPage = when (loadType) {
            LoadType.REFRESH -> {
                logger.debug("Refreshing my friends")
                0
            }

            LoadType.PREPEND -> {
                logger.debug("Prepending my friends")
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            LoadType.APPEND -> {
                logger.debug("Appending my friends.")
                val numLoaded = friendshipLocalDataSource.getNumberPendingSentFromMe()
                numLoaded / state.pages.size
            }
        }

        return when (val response = pendingFriendsISentCachingManager.updateLocalFriends(
            query = FriendsQuery(
                page = nextPage,
                pageSize = state.config.pageSize,
            ),
            invalidateCache = loadType == LoadType.REFRESH,
        )) {
            is NetworkResult.Success -> {
                logger.debug(
                    "Loaded ${response.t.size} confirmed friends. Confirmed friends " +
                            "pages on screen: ${state.pages.size}"
                )
                val endOfPagination = response.t.size < state.config.pageSize
                if (endOfPagination) {
                    logger.debug("End of pagination reached for confirmed friends")
                }
                MediatorResult.Success(endOfPaginationReached = endOfPagination)
            }

            is NetworkResult.IOFailure -> {
                MediatorResult.Error(response.e)
            }
        }
    }
}