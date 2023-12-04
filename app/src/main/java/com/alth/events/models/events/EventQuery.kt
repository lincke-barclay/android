package com.alth.events.models.events

import com.alth.events.models.SortDirection
import kotlinx.datetime.Instant

data class EventQuery(
    /**
     * Date Time Ranges
     */
    val fromStartDateTimeInclusive: Instant? = null,
    val toStartDateTimeInclusive: Instant? = null,
    val fromEndDateTimeInclusive: Instant? = null,
    val toEndDateTimeInclusive: Instant? = null,

    /**
     * Query string
     */
    val titleContainsIgnoreCase: String? = null,

    /**
     * Sort direction
     */
    val sortBy: EventSortBy = EventSortBy.StartDateTime,
    val sortDirection: SortDirection = SortDirection.ASC,

    /**
     * Include types of events
     */
    val includeMyEvents: Boolean = true,
    val includeMyFriendsEvents: Boolean = true,
    val includeEventsImInvitedTo: Boolean = true,
    val includePublicEvents: Boolean = true,
) {
    fun toUniqueId(): String {
        return hashCode().toString()
    }

    companion object {
        fun blank() = EventQuery()
    }
}
