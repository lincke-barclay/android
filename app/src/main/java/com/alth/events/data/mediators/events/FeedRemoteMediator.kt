package com.alth.events.data.mediators.events

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.alth.events.data.caching.events.FeedCachingManager
import com.alth.events.database.models.derived.FeedEvent
import com.alth.events.logging.impl.loggerFactory
import com.alth.events.networking.models.NetworkResult
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class FeedRemoteMediator @Inject constructor(
    private val feedCachingManager: FeedCachingManager,
) : RemoteMediator<Int, FeedEvent>() {
    private val logger = loggerFactory.getLogger(this)

    override suspend fun initialize(): InitializeAction {
        return if (feedCachingManager.doesFeedCacheNeedUpdating()) {
            logger.debug(
                "Remote Mediator Cache timeout is greater than current " +
                        "time - last update, doing nothing for feed event"
            )
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            logger.debug(
                "Remote Mediator Cache timeout is less than than current " +
                        "time - last update, refreshing cache for feed event"
            )
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FeedEvent>,
    ): MediatorResult {
        val lastLoaded = when (loadType) {
            LoadType.REFRESH -> {
                logger.debug("Refreshing events for feed")
                null
            }

            LoadType.PREPEND -> {
                logger.debug("Prepending events for feed")
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            LoadType.APPEND -> {
                val id = state.lastItemOrNull()?.id
                logger.debug("Appending events for feed. Last Feed element consumed was: $id")
                id
            }
        }

        return when (val response = feedCachingManager.updateLocalFeed(
            lastFeedItemId = lastLoaded,
            limit = state.config.pageSize,
            invalidateCache = loadType == LoadType.REFRESH
        )) {
            is NetworkResult.Success -> {
                logger.debug(
                    "Loaded ${response.t.publicEvents.size} events for feed. Feed " +
                            "pages on screen: ${state.pages.size}"
                )
                val endOfPagination = response.t.publicEvents.size < state.config.pageSize
                if (endOfPagination) {
                    logger.debug("End of pagination reached for feed")
                }
                MediatorResult.Success(endOfPaginationReached = endOfPagination)
            }

            is NetworkResult.IOFailure -> {
                MediatorResult.Error(response.e)
            }
        }
    }
}