package com.alth.events.database.sources.events

import com.alth.events.database.dao.events.EventDao
import com.alth.events.networking.models.events.ingress.PrivateEventResponseDto
import com.alth.events.networking.models.events.ingress.PublicEventResponseDto
import com.alth.events.transforms.networkToDatabase.toDatabaseEvent
import javax.inject.Inject

class LocalEventDataSource @Inject constructor(
    private val eventDao: EventDao,
) {
    suspend fun upsertAll(
        events: List<PublicEventResponseDto>,
        fromSearchQuery: String? = null,
        isFeed: Boolean = false,
    ) {
        eventDao.upsertAll(events.map { it.toDatabaseEvent(fromSearchQuery, isFeed) })
    }

    suspend fun upsertAll(
        events: List<PrivateEventResponseDto>,
        myId: String,
        fromSearchQuery: String? = null,
        isFeed: Boolean = false,
    ) {
        eventDao.upsertAll(events.map { it.toDatabaseEvent(myId, fromSearchQuery, isFeed) })
    }
}
