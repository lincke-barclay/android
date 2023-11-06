package com.alth.events.models.network.events.ingress

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class PublicEventResponseDto(
    val id: String,
    val ownerId: String,
    val startDateTime: Instant,
    val endDateTime: Instant,
    val title: String,
    val shortDescription: String,
    val longDescription: String,
)
