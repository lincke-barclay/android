package com.alth.events.models.network.events.egress

import com.alth.events.models.domain.events.NewEventRequest
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class POSTEventRequestDTO(
    val title: String,
    val shortDescription: String,
    val longDescription: String,
    val startingDateTime: Instant,
    val endingDateTime: Instant
)

fun NewEventRequest.toPOSTEventRequestDTO() = POSTEventRequestDTO(
    title = title,
    shortDescription = shortDescription,
    longDescription = longDescription,
    startingDateTime = startDateTime,
    endingDateTime = endDateTime,
)

