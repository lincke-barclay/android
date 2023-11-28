package com.alth.events.data.mediators.users

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.alth.events.data.caching.users.QueriedUsersCachingManager
import com.alth.events.database.models.users.PublicUserEntity
import com.alth.events.database.sources.users.QueriedUserLocalDataSource
import com.alth.events.logging.impl.loggerFactory
import com.alth.events.networking.models.NetworkResult

@OptIn(ExperimentalPagingApi::class)
class QueriedUserRemoteMediator(
    private val queriedUsersCachingManager: QueriedUsersCachingManager,
    private val searchQuery: String,
    private val queriedUserLocalDataSource: QueriedUserLocalDataSource,
) : RemoteMediator<Int, PublicUserEntity>() {

    private val logger = loggerFactory.getLogger(this::class.simpleName + ": Query: $searchQuery")

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PublicUserEntity>,
    ): MediatorResult {
        logger.debug("Loading users for search query")
        val page = when (loadType) {
            LoadType.REFRESH -> 0
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                queriedUserLocalDataSource.countAllByQuery(searchQuery) / state.config.pageSize
            }
        }

        return when (val response = queriedUsersCachingManager.updateLocalSearchResultsForQuery(
            query = searchQuery,
            invalidateCacheForQuery = loadType == LoadType.REFRESH,
            page = page,
            pageSize = state.config.pageSize,
        )) {
            is NetworkResult.Success -> {
                logger.debug("Recieved these users on query: $searchQuery: ${response.t}")
                val endOfPagination = response.t.size < state.config.pageSize
                if (endOfPagination) {
                    logger.debug("End of pagination reached for queried user")
                }
                MediatorResult.Success(endOfPaginationReached = endOfPagination)
            }

            is NetworkResult.IOFailure -> {
                MediatorResult.Error(response.e)
            }
        }
    }
}