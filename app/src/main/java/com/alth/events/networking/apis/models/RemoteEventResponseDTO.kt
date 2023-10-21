package com.alth.events.networking.apis.models

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class RemoteEventResponseDTO (
    val id: Long,
    val title: String,
    val shortDescription: String,
    val longDescription: String,
    val firebaseOwnerId: String,
    val createdDateTime: Instant,
    val startingDateTime: Instant,
    val endingDateTime: Instant,
)
