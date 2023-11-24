package com.alth.events.networking.models


enum class PrivateEventSortBy {
    StartDateTime,
    EndDateTime,
    CreatedDateTime,
    LastUpdatedDateTime,
}

enum class PublicEventSortBy {
    StartDateTime,
    EndDateTime;

    fun toPrivateEventSortBy() = when (this) {
        StartDateTime -> PrivateEventSortBy.StartDateTime
        EndDateTime -> PrivateEventSortBy.EndDateTime
    }
}

enum class SortDirection {
    ASC,
    DESC,
}