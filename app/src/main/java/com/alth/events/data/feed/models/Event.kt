package com.alth.events.data.feed.models

import com.alth.events.data.authentication.models.PublicUser
import kotlinx.datetime.Instant

data class Event(
    val title: String,
    val shortDescription: String,
    val longDescription: String,
    val createdDateTime: Instant,
    val startDateTime: Instant,
    val endDateTime: Instant,
    val organizer: PublicUser,
    val images: Set<FeedImage>
) {
    companion object {
        fun random() = Event(
            "foo",
            "Short description",
            "Long Description",
            Instant.fromEpochMilliseconds(0),
            Instant.fromEpochMilliseconds(1),
            Instant.fromEpochMilliseconds(2),
            PublicUser("Bobby"),
            (1..4).map { FeedImage.random() }.toSet(),
        )
    }
}

data class FeedImage(
    val url: String,
) {
    companion object {
        fun random() = FeedImage("https://picsum.photos/200/300")
    }
}
