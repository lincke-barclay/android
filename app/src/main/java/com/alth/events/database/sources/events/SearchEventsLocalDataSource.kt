package com.alth.events.database.sources.events

import androidx.paging.PagingSource
import com.alth.events.database.dao.events.SearchEventDao
import com.alth.events.database.models.derived.SearchEventResult
import com.alth.events.networking.models.events.ingress.PublicEventResponseDto
import javax.inject.Inject

class SearchEventsLocalDataSource @Inject constructor(
    private val searchEventDao: SearchEventDao,
    private val localEventDataSource: LocalEventDataSource,
) {
    suspend fun insertNewSearchEventsByQuery(events: List<PublicEventResponseDto>, query: String) {
        localEventDataSource.upsertAll(
            events,
            fromSearchQuery = query,
        )
    }

    suspend fun clearSearchEventsByQuery(query: String) {
        searchEventDao.clearEventsBySearchQuery(query)
    }

    fun getSearchEventsPagingSourceForQuery(
        query: String,
    ): PagingSource<Int, SearchEventResult> {
        return searchEventDao.getSearchPagerForQuery(query)
    }
}
