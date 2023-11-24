package com.alth.events.data.mediators.events

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.alth.events.data.caching.events.QueriedEventsCachingManager
import com.alth.events.database.models.derived.SearchEventResult
import com.alth.events.logging.impl.loggerFactory
import com.alth.events.models.domain.events.PublicEventQuery
import com.alth.events.networking.models.NetworkResult

@OptIn(ExperimentalPagingApi::class)
class QueriedEventRemoteMediator(
    private val queriedEventsCachingManager: QueriedEventsCachingManager,
    private val searchQuery: PublicEventQuery,
) : RemoteMediator<Int, SearchEventResult>() {

    private val logger = loggerFactory.getLogger(this::class.simpleName + ": Query: $searchQuery")

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SearchEventResult>,
    ): MediatorResult {
        logger.debug("Loading events for search query")
        val lastLoaded = when (loadType) {
            LoadType.REFRESH -> null
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                state.lastItemOrNull()
            }
        }

        return when (val response = queriedEventsCachingManager.updateLocalSearchResultsForQuery(
            query = searchQuery,
            invalidateCache = loadType == LoadType.REFRESH,
            limit = state.config.pageSize,
            lastEventConsumed = lastLoaded,
        )) {
            is NetworkResult.Success -> {
                val endOfPagination = response.t.publicEvents.size < state.config.pageSize
                if (endOfPagination) {
                    logger.debug("End of pagination reached for feed for search pager")
                }
                MediatorResult.Success(endOfPaginationReached = endOfPagination)
            }

            is NetworkResult.IOFailure -> {
                MediatorResult.Error(response.e)
            }
        }
    }
}