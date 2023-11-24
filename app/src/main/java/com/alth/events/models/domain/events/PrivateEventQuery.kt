package com.alth.events.models.domain.events

import com.alth.events.networking.models.PrivateEventSortBy
import com.alth.events.networking.models.SortDirection
import kotlinx.datetime.Instant

data class PrivateEventQuery(
    val fromStartDateTimeInclusive: Instant? = null,
    val toStartDateTimeInclusive: Instant? = null,
    val fromEndDateTimeInclusive: Instant? = null,
    val toEndDateTimeInclusive: Instant? = null,
    val fromCreatedTimeInclusive: Instant? = null,
    val toCreatedTimeInclusive: Instant? = null,
    val fromLastUpdatedTimeInclusive: Instant? = null,
    val toLastUpdatedTimeInclusive: Instant? = null,
    val titleContainsIC: String? = null,
    val sortBy: PrivateEventSortBy = PrivateEventSortBy.StartDateTime,
    val sortDirection: SortDirection = SortDirection.ASC,
) {
    fun toUniqueId(): Int {
        return hashCode()
    }
}
