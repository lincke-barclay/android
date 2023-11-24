package com.alth.events.networking.models.events.egress

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

