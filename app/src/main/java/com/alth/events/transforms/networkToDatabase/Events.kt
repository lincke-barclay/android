package com.alth.events.transforms.networkToDatabase

import com.alth.events.database.models.Event
import com.alth.events.networking.models.events.ingress.PrivateEventResponseDto
import com.alth.events.networking.models.events.ingress.PublicEventResponseDto

fun PublicEventResponseDto.toDatabaseEvent(
    fromSearchQuery: String? = null,
    isFeed: Boolean = false,
): Event {
    return Event(
        id = id,
        ownerId = ownerId,
        startDateTime = startDateTime,
        endDateTime = endDateTime,
        title = title,
        shortDescription = shortDescription,
        longDescription = longDescription,
        fromSearchQuery = fromSearchQuery,
        isFeed = isFeed,
    )
}

fun PrivateEventResponseDto.toDatabaseEvent(
    myId: String,
    fromSearchQuery: String? = null,
    isFeed: Boolean = false,
): Event {
    return Event(
        id = id,
        ownerId = myId,
        startDateTime = startDateTime,
        endDateTime = endDateTime,
        title = title,
        shortDescription = shortDescription,
        longDescription = longDescription,
        fromSearchQuery = fromSearchQuery,
        isFeed = isFeed,
    )
}