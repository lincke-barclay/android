package com.alth.events.database.sources.events

import com.alth.events.database.dao.events.EventDao
import com.alth.events.networking.models.events.ingress.PrivateEventResponseDto
import com.alth.events.networking.models.events.ingress.PublicEventResponseDto
import com.alth.events.transforms.networkToDatabase.toEventEntity
import javax.inject.Inject

class LocalEventDataSource @Inject constructor(
    private val eventDao: EventDao,
) {
    suspend fun upsertAll(
        events: List<PublicEventResponseDto>,
    ) {
        eventDao.upsertAll(events.map { it.toEventEntity() })
    }

    suspend fun upsertAll(
        events: List<PrivateEventResponseDto>,
        myId: String,
    ) {
        eventDao.upsertAll(events.map { it.toEventEntity(myId) })
    }
}
