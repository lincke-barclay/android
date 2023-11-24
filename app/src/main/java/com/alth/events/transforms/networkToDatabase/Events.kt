package com.alth.events.transforms.networkToDatabase

import com.alth.events.database.models.events.EventEntity
import com.alth.events.database.models.events.FeedEventEntity
import com.alth.events.database.models.events.QueriedEventEntity
import com.alth.events.models.domain.events.PublicEventQuery
import com.alth.events.networking.models.events.ingress.PrivateEventResponseDto
import com.alth.events.networking.models.events.ingress.PublicEventResponseDto

fun PublicEventResponseDto.toEventEntity(): EventEntity {
    return EventEntity(
        id = id,
        ownerId = ownerId,
        startDateTime = startDateTime,
        endDateTime = endDateTime,
        title = title,
        shortDescription = shortDescription,
        longDescription = longDescription,
    )
}

fun PublicEventResponseDto.toFeedEventEntity(): FeedEventEntity {
    return FeedEventEntity(eventId = id)
}

fun PublicEventResponseDto.toQueriedEvent(query: PublicEventQuery): QueriedEventEntity {
    return QueriedEventEntity(
        eventId = id,
        serializedQuery = query.toUniqueId(),
    )
}

fun PrivateEventResponseDto.toEventEntity(myId: String): EventEntity {
    return EventEntity(
        id = id,
        ownerId = myId,
        startDateTime = startDateTime,
        endDateTime = endDateTime,
        title = title,
        shortDescription = shortDescription,
        longDescription = longDescription,
    )
}