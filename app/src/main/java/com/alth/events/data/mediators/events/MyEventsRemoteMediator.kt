package com.alth.events.data.mediators.events

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.alth.events.data.caching.events.MyQueriedEventsCachingManager
import com.alth.events.database.models.events.derived.AnonymousEvent
import com.alth.events.logging.impl.loggerFactory
import com.alth.events.models.domain.events.PublicEventQuery
import com.alth.events.networking.models.NetworkResult

@OptIn(ExperimentalPagingApi::class)
class MyEventsRemoteMediator(
    private val myQueriedEventsCachingManager: MyQueriedEventsCachingManager,
    private val searchQuery: PublicEventQuery,
) : RemoteMediator<Int, AnonymousEvent>() {
    private val logger = loggerFactory.getLogger(this)

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AnonymousEvent>,
    ): MediatorResult {
        logger.debug("Loading events for me")
        val lastLoaded = when (loadType) {
            LoadType.REFRESH -> null
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                state.lastItemOrNull()
            }
        }

        return when (val response = myQueriedEventsCachingManager.updateLocalSearchResultsForQuery(
            lastEventConsumed = lastLoaded,
            invalidateCache = loadType == LoadType.REFRESH,
            query = searchQuery,
            limit = state.config.pageSize,
        )) {
            is NetworkResult.Success -> {
                val endOfPagination = response.t.size < state.config.pageSize
                if (endOfPagination) {
                    logger.debug("End of pagination reached for my events")
                }
                MediatorResult.Success(endOfPaginationReached = endOfPagination)
            }

            is NetworkResult.IOFailure -> {
                MediatorResult.Error(response.e)
            }
        }
    }
}