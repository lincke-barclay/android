package com.alth.events.database.models.derived

import kotlinx.datetime.Instant

data class FeedEvent(
    val id: String,
    val ownerName: String,
    val ownerProfilePictureUrl: String,
    val startDateTime: Instant,
    val endDateTime: Instant,
    val title: String,
    val shortDescription: String,
    val longDescription: String,
)
