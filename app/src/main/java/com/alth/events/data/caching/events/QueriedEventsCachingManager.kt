package com.alth.events.data.caching.events

import com.alth.events.database.DatabaseTransactionUseCase
import com.alth.events.database.models.events.derived.SearchEventResult
import com.alth.events.database.sources.events.SearchEventsLocalDataSource
import com.alth.events.database.sources.users.UserLocalDataSource
import com.alth.events.models.domain.events.PublicEventQuery
import com.alth.events.networking.models.NetworkResult
import com.alth.events.networking.models.PublicEventSortBy
import com.alth.events.networking.models.SortDirection
import com.alth.events.networking.models.events.ingress.MinimalEventListResponseDto
import com.alth.events.networking.sources.NetworkEventDataSource
import javax.inject.Inject

class QueriedEventsCachingManager @Inject constructor(
    private val networkEventDataSource: NetworkEventDataSource,
    private val searchEventsLocalDataSource: SearchEventsLocalDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val transaction: DatabaseTransactionUseCase,
) {
    suspend fun updateLocalSearchResultsForQuery(
        query: PublicEventQuery,
        limit: Int,
        invalidateCache: Boolean,
        lastEventConsumed: SearchEventResult? = null, // For paging
    ): NetworkResult<MinimalEventListResponseDto> {
        // Find next query based on last event result
        val nextQuery = lastEventConsumed?.let {
            when (query.sortBy) {
                PublicEventSortBy.StartDateTime -> when (query.sortDirection) {
                    SortDirection.ASC -> query.copy(
                        fromStartDateTimeInclusive = it.startDateTime,
                    )

                    SortDirection.DESC -> query.copy(
                        toStartDateTimeInclusive = it.startDateTime,
                    )
                }

                PublicEventSortBy.EndDateTime -> when (query.sortDirection) {
                    SortDirection.ASC -> query.copy(
                        fromEndDateTimeInclusive = it.endDateTime,
                    )

                    SortDirection.DESC -> query.copy(
                        toEndDateTimeInclusive = it.endDateTime,
                    )
                }
            }
        } ?: query

        val response = networkEventDataSource.getEventsByQueryParameters(
            fromStartDateTimeInclusive = nextQuery.fromStartDateTimeInclusive,
            fromEndDateTimeInclusive = nextQuery.fromEndDateTimeInclusive,
            toStartDateTimeInclusive = nextQuery.toStartDateTimeInclusive,
            toEndDateTimeInclusive = nextQuery.toEndDateTimeInclusive,
            titleContainsIC = nextQuery.titleContainsIC,
            sortBy = nextQuery.sortBy,
            sortDirection = nextQuery.sortDirection,
            limit = limit,
        )

        when (response) {
            is NetworkResult.Success -> {
                transaction {
                    if (invalidateCache) {
                        searchEventsLocalDataSource.clearSearchEventsByQuery(query.toUniqueId())
                    }
                    searchEventsLocalDataSource.upsertNewSearchEventsByQuery(
                        response.t.publicEvents,
                        query,
                    )
                    userLocalDataSource.upsertAll(response.t.publicUsers.values.toList())
                }
            }

            is NetworkResult.IOFailure -> {}
        }

        return response
    }
}