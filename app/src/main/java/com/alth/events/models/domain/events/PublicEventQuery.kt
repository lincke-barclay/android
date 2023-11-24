package com.alth.events.models.domain.events

import com.alth.events.networking.models.PublicEventSortBy
import com.alth.events.networking.models.SortDirection
import kotlinx.datetime.Instant

data class PublicEventQuery(
    val fromStartDateTimeInclusive: Instant? = null,
    val toStartDateTimeInclusive: Instant? = null,
    val fromEndDateTimeInclusive: Instant? = null,
    val toEndDateTimeInclusive: Instant? = null,
    val titleContainsIC: String? = null,
    val sortBy: PublicEventSortBy = PublicEventSortBy.StartDateTime,
    val sortDirection: SortDirection = SortDirection.ASC,
) {
    fun toUniqueId(): String {
        return hashCode().toString()
    }

    companion object {
        fun blank() = PublicEventQuery()
    }
}

