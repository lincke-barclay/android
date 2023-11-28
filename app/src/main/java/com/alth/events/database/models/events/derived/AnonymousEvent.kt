package com.alth.events.database.models.events.derived

import kotlinx.datetime.Instant

data class AnonymousEvent(
    val id: String,
    val startDateTime: Instant,
    val endDateTime: Instant,
    val title: String,
    val shortDescription: String,
    val longDescription: String,
)
