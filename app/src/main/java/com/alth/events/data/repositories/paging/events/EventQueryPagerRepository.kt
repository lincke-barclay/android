package com.alth.events.data.repositories.paging.events

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alth.events.data.caching.events.QueriedEventsCachingManager
import com.alth.events.data.mediators.events.QueriedEventRemoteMediator
import com.alth.events.database.models.events.derived.SearchEventResult
import com.alth.events.database.sources.events.SearchEventsLocalDataSource
import com.alth.events.logging.impl.loggerFactory
import com.alth.events.models.events.EventQuery
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class EventQueryPagerRepository @Inject constructor(
    private val queriedEventsCachingManager: QueriedEventsCachingManager,
    private val searchEventsLocalDataSource: SearchEventsLocalDataSource,
) {
    private val pageSize = 20 // TODO - configure this in application properties
    private val logger = loggerFactory.getLogger(this)

    fun searchPager(query: EventQuery): Flow<PagingData<SearchEventResult>> {
        logger.debug("Creating new search event mediator for query: $query")
        val mediator = QueriedEventRemoteMediator(
            queriedEventsCachingManager,
            searchQuery = query,
        )
        logger.debug("Successfully created new mediator")

        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                prefetchDistance = 2,
            ),
            remoteMediator = mediator,
        ) {
            searchEventsLocalDataSource.getSearchEventsPagingSourceForQuery(query.toUniqueId())
        }.flow
    }
}
