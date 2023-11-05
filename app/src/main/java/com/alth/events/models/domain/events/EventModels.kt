package com.alth.events.models.domain.events

import com.alth.events.models.domain.users.PublicUser
import kotlinx.datetime.Instant

data class FeedEvent(
    val title: String,
    val shortDescription: String,
    val longDescription: String,
    val startDateTime: Instant,
    val endDateTime: Instant,
    val organizer: PublicUser,
    val images: Set<FeedImage>
)

data class FeedImage(
    val url: String,
)

data class NewEventRequest(
    val title: String,
    val shortDescription: String,
    val longDescription: String,
    val startDateTime: Instant,
    val endDateTime: Instant
)

data class SuggestedEvent(
    val id: String,
    val title: String,
    val shortDescription: String,
    val organizerFirstName: String,
    val organizerLastName: String,
)


