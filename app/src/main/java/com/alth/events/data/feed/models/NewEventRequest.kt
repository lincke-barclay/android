package com.alth.events.data.feed.models

import kotlinx.datetime.Instant

data class NewEventRequest(
    val title: String,
    val shortDescription: String,
    val longDescription: String,
    val startDateTime: Instant,
    val endDateTime: Instant
)