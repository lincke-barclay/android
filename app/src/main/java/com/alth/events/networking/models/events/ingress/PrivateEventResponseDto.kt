package com.alth.events.networking.models.events.ingress

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class PrivateEventResponseDto(
    val id: String,
    val createdTs: Instant,
    val lastUpdatedTs: Instant,
    val startDateTime: Instant,
    val endDateTime: Instant,
    val title: String,
    val shortDescription: String,
    val longDescription: String,
)
