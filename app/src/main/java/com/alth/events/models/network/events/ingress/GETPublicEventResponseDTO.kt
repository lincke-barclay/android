package com.alth.events.models.network.events.ingress

import com.alth.events.models.domain.events.FeedEvent
import com.alth.events.models.domain.events.SuggestedEvent
import com.alth.events.models.network.users.ingress.GETPublicUserResponseDTO
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class GETPublicEventResponseDTO(
    val id: String,
    val owner: GETPublicUserResponseDTO,
    val startDateTime: Instant,
    val endDateTime: Instant,
    val title: String,
    val shortDescription: String,
    val longDescription: String,
) {
    fun toSuggestedEvent() = SuggestedEvent(
        id = id,
        title = title,
        shortDescription = shortDescription,
        organizerFirstName = owner.firstName,
        organizerLastName = owner.lastName,
    )

    fun toFeedEvent() = FeedEvent(
        title = title,
        shortDescription = shortDescription,
        longDescription = longDescription,
        startDateTime = startDateTime,
        endDateTime = endDateTime,
        organizer = owner.toPublicUser(),
        images = setOf(),
    )
}
