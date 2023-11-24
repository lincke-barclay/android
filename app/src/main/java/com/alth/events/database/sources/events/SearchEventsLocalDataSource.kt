package com.alth.events.database.sources.events

import androidx.paging.PagingSource
import androidx.room.Transaction
import com.alth.events.database.dao.events.SearchEventDao
import com.alth.events.database.models.events.derived.SearchEventResult
import com.alth.events.models.domain.events.PublicEventQuery
import com.alth.events.networking.models.events.ingress.PublicEventResponseDto
import com.alth.events.transforms.networkToDatabase.toEventEntity
import com.alth.events.transforms.networkToDatabase.toQueriedEvent
import javax.inject.Inject

class SearchEventsLocalDataSource @Inject constructor(
    private val searchEventDao: SearchEventDao,
    private val localEventDataSource: LocalEventDataSource,
) {
    @Transaction
    suspend fun upsertNewSearchEventsByQuery(events: List<PublicEventResponseDto>, query: PublicEventQuery) {
        localEventDataSource.upsertAll(events)
        searchEventDao.upsertAll(events.map { it.toQueriedEvent(query) })
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
