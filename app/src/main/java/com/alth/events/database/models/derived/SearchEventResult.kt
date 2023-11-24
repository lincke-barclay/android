package com.alth.events.database.models.derived

import com.alth.events.database.models.Event
import com.alth.events.database.models.PublicUser
import com.alth.events.models.domain.events.PublicEventQuery
import kotlinx.datetime.Instant

data class SearchEventResult(
    val id: String,
    val ownerName: String,
    val startDateTime: Instant,
    val endDateTime: Instant,
    val title: String,
    val shortDescription: String,
    val longDescription: String,
) {

    fun matches(query: PublicEventQuery): Boolean {
        val startDateTimeLessThan = query.toStartDateTimeInclusive?.let {
            startDateTime < it
        } ?: true
        val startDateTimeGreaterThan = query.fromStartDateTimeInclusive?.let {
            startDateTime > it
        } ?: true
        val endDateTimeLessThan = query.toEndDateTimeInclusive?.let {
            endDateTime < it
        } ?: true
        val endDateTimeGreaterThan = query.fromEndDateTimeInclusive?.let {
            endDateTime > it
        } ?: true
        val titleMatches = query.titleContainsIC?.let {
            it.lowercase() in title.lowercase()
        } ?: true

        return startDateTimeLessThan &&
                startDateTimeGreaterThan &&
                endDateTimeGreaterThan &&
                endDateTimeLessThan &&
                titleMatches
    }

    companion object {
        fun from(event: Event, owner: PublicUser): SearchEventResult {
            return SearchEventResult(
                id = event.id,
                ownerName = owner.name,
                startDateTime = event.startDateTime,
                endDateTime = event.endDateTime,
                title = event.title,
                shortDescription = event.shortDescription,
                longDescription = event.longDescription,
            )
        }
    }
}
