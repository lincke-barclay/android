package com.alth.events.transforms.domain

import com.alth.events.database.models.events.derived.PublicUserWithEvents
import com.alth.events.database.models.events.derived.SearchEventResult
import com.alth.events.networking.models.events.ingress.PrivateEventResponseDto
import com.alth.events.networking.models.events.ingress.PublicEventResponseDto

fun PublicUserWithEvents.toSearchEventResultList() = eventEntities.map { event ->
    SearchEventResult.from(event, owner)
}

fun PrivateEventResponseDto.toPublicEventResponseDto(myId: String) = PublicEventResponseDto(
    id = id,
    ownerId = myId,
    startDateTime = startDateTime,
    endDateTime = endDateTime,
    title = title,
    shortDescription = shortDescription,
    longDescription = longDescription,
)