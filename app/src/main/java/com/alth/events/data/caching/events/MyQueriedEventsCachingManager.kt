package com.alth.events.data.caching.events

import com.alth.events.authentication.sources.AuthenticationDataSource
import com.alth.events.authentication.sources.withIDOrThrow
import com.alth.events.database.DatabaseTransactionUseCase
import com.alth.events.database.models.derived.MyEvent
import com.alth.events.database.sources.events.MyEventsLocalDataSource
import com.alth.events.models.domain.events.PublicEventQuery
import com.alth.events.networking.models.NetworkResult
import com.alth.events.networking.models.PublicEventSortBy
import com.alth.events.networking.models.SortDirection
import com.alth.events.networking.models.events.ingress.PrivateEventResponseDto
import com.alth.events.networking.sources.NetworkEventDataSource
import javax.inject.Inject

class MyQueriedEventsCachingManager @Inject constructor(
    private val networkEventDataSource: NetworkEventDataSource,
    private val myEventsLocalDataSource: MyEventsLocalDataSource,
    private val transaction: DatabaseTransactionUseCase,
    private val authenticationDataSource: AuthenticationDataSource,
) {
    suspend fun updateLocalSearchResultsForQuery(
        query: PublicEventQuery,
        limit: Int,
        invalidateCache: Boolean,
        lastEventConsumed: MyEvent? = null, // For paging
    ): NetworkResult<List<PrivateEventResponseDto>> {
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

        val response = networkEventDataSource.getEventsOfUserWithQueryParameters(
            fromStartDateTimeInclusive = nextQuery.fromStartDateTimeInclusive,
            fromEndDateTimeInclusive = nextQuery.fromEndDateTimeInclusive,
            toStartDateTimeInclusive = nextQuery.toStartDateTimeInclusive,
            toEndDateTimeInclusive = nextQuery.toEndDateTimeInclusive,
            titleContainsIC = nextQuery.titleContainsIC,
            sortBy = nextQuery.sortBy.toPrivateEventSortBy(),
            sortDirection = nextQuery.sortDirection,
            limit = limit,
        )

        val myId = authenticationDataSource.withIDOrThrow { it }

        when (response) {
            is NetworkResult.Success -> {
                transaction {
                    if (invalidateCache) {
                        myEventsLocalDataSource.clearMyEvents()
                    }
                    myEventsLocalDataSource.insertNewMyEvents(response.t)
                }
            }

            is NetworkResult.IOFailure -> {}
        }

        return response
    }
}