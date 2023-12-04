package com.alth.events.networking.sources

import com.alth.events.authentication.sources.AuthenticationDataSource
import com.alth.events.logging.impl.loggerFactory
import com.alth.events.models.SortDirection
import com.alth.events.models.events.EventSortBy
import com.alth.events.networking.apis.EventsApi
import com.alth.events.networking.models.NetworkResult
import com.alth.events.networking.models.events.egress.POSTEventRequestDTO
import com.alth.events.networking.models.events.ingress.MinimalEventListResponseDto
import com.alth.events.networking.models.events.ingress.PrivateEventResponseDto
import com.alth.events.networking.withIDAndTokenOrThrowNetworkExec
import com.alth.events.networking.withTokenOrThrowNetworkExec
import kotlinx.datetime.Instant
import javax.inject.Inject

class NetworkEventDataSource @Inject constructor(
    private val eventsApi: EventsApi,
    private val authenticationDataSource: AuthenticationDataSource,
) {
    private val logger = loggerFactory.getLogger(this)

    suspend fun getEventsOfUserWithQueryParameters(
        fromStartDateTimeInclusive: Instant? = null,
        fromEndDateTimeInclusive: Instant? = null,
        toStartDateTimeInclusive: Instant? = null,
        toEndDateTimeInclusive: Instant? = null,
        titleContainsIC: String? = null,
        sortBy: EventSortBy? = null,
        sortDirection: SortDirection? = null,
        limit: Int,
    ): NetworkResult<List<PrivateEventResponseDto>> {
        return authenticationDataSource.withIDAndTokenOrThrowNetworkExec { userId, token ->
            eventsApi.getEventsOfUserWithQueryParameters(
                token,
                userId,
                fromStartDateTimeInclusive,
                fromEndDateTimeInclusive,
                toStartDateTimeInclusive,
                toEndDateTimeInclusive,
                titleContainsIC,
                sortBy,
                sortDirection,
                limit,
            )
        }
    }

    suspend fun createEvent(
        body: POSTEventRequestDTO,
    ): NetworkResult<PrivateEventResponseDto> {
        return authenticationDataSource.withIDAndTokenOrThrowNetworkExec { id, token ->
            eventsApi.createEvent(
                token,
                id,
                body
            )
        }
    }

    suspend fun getFeedForUser(
        lastEventId: String?,
        limit: Int,
    ): NetworkResult<MinimalEventListResponseDto> {
        return authenticationDataSource.withIDAndTokenOrThrowNetworkExec { id, token ->
            eventsApi.getFeedForUser(
                token,
                id,
                lastEventId,
                limit,
            )
        }
    }

    suspend fun getEventsByQueryParameters(
        fromStartDateTimeInclusive: Instant? = null,
        fromEndDateTimeInclusive: Instant? = null,
        toStartDateTimeInclusive: Instant? = null,
        toEndDateTimeInclusive: Instant? = null,
        titleContainsIC: String? = null,
        sortBy: EventSortBy? = null,
        sortDirection: SortDirection? = null,
        limit: Int,
    ): NetworkResult<MinimalEventListResponseDto> {
        return authenticationDataSource.withTokenOrThrowNetworkExec { token ->
            eventsApi.getEventsByQueryParameters(
                token,
                fromStartDateTimeInclusive,
                fromEndDateTimeInclusive,
                toStartDateTimeInclusive,
                toEndDateTimeInclusive,
                titleContainsIC,
                sortBy,
                sortDirection,
                limit,
            )
        }
    }

    suspend fun getSingleEvent(
        eventId: String,
    ): NetworkResult<Unit> {
        return authenticationDataSource.withIDAndTokenOrThrowNetworkExec { id, token ->
            eventsApi.getSingleEvent(
                token,
                id,
                eventId,
            )
        }
    }

    suspend fun deleteEvent(
        eventId: String,
    ): NetworkResult<Unit> {
        return authenticationDataSource.withIDAndTokenOrThrowNetworkExec { id, token ->
            eventsApi.deleteEvent(
                token,
                id,
                eventId,
            )
        }
    }
}
