package com.alth.events.database.models.events.derived

import androidx.room.ColumnInfo
import com.alth.events.database.models.events.EventEntity
import com.alth.events.database.models.users.PublicUserEntity
import com.alth.events.models.domain.events.PublicEventQuery
import kotlinx.datetime.Instant

data class SearchEventResult(
    val id: String,
    @ColumnInfo(name = "name") val ownerName: String,
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
        fun from(eventEntity: EventEntity, owner: PublicUserEntity): SearchEventResult {
            return SearchEventResult(
                id = eventEntity.id,
                ownerName = owner.name,
                startDateTime = eventEntity.startDateTime,
                endDateTime = eventEntity.endDateTime,
                title = eventEntity.title,
                shortDescription = eventEntity.shortDescription,
                longDescription = eventEntity.longDescription,
            )
        }
    }
}
