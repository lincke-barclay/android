package com.alth.events.models.events

import kotlinx.datetime.Instant

data class FeedEventQuery(
    val fromDateTimeInclusive: Instant? = null,
    val toDateTimeInclusive: Instant? = null,

    val includeMyFriendsEvents: Boolean = true,
    val includeEventsImInvitedTo: Boolean = true,
    val includePublicEvents: Boolean = true,
)