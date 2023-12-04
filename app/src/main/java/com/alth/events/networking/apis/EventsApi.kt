package com.alth.events.networking.apis

import com.alth.events.models.SortDirection
import com.alth.events.models.events.EventSortBy
import com.alth.events.networking.models.events.egress.POSTEventRequestDTO
import com.alth.events.networking.models.events.ingress.MinimalEventListResponseDto
import com.alth.events.networking.models.events.ingress.PrivateEventResponseDto
import kotlinx.datetime.Instant
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface EventsApi {
    @GET("/users/{userId}/events")
    suspend fun getEventsOfUserWithQueryParameters(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Query("fromStartDateTimeINC") fromStartDateTimeInclusive: Instant? = null,
        @Query("fromEndDateTimeINC") fromEndDateTimeInclusive: Instant? = null,
        @Query("toStartDateTimeINC") toStartDateTimeInclusive: Instant? = null,
        @Query("toEndDateTimeINC") toEndDateTimeInclusive: Instant? = null,
        @Query("titleContainsIC") titleContainsIC: String? = null,
        @Query("sortBy") sortBy: EventSortBy? = null,
        @Query("sortDirection") sortDirection: SortDirection? = null,
        @Query("limit") limit: Int,
    ): List<PrivateEventResponseDto> // TODO - this will eventually be a string

    @POST("/users/{userId}/events")
    suspend fun createEvent(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Body body: POSTEventRequestDTO,
    ): PrivateEventResponseDto

    @GET("/users/{userId}/events/feed")
    suspend fun getFeedForUser(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Query("lastEventId") lastEventId: String?,
        @Query("limit") limit: Int,
    ): MinimalEventListResponseDto

    @GET("/events")
    suspend fun getEventsByQueryParameters(
        @Header("Authorization") token: String,
        @Query("fromStartDateTimeINC") fromStartDateTimeInclusive: Instant? = null,
        @Query("fromEndDateTimeINC") fromEndDateTimeInclusive: Instant? = null,
        @Query("toStartDateTimeINC") toStartDateTimeInclusive: Instant? = null,
        @Query("toEndDateTimeINC") toEndDateTimeInclusive: Instant? = null,
        @Query("titleContainsIC") titleContainsIC: String? = null,
        @Query("sortBy") sortBy: EventSortBy? = null,
        @Query("sortDirection") sortDirection: SortDirection? = null,
        @Query("limit") limit: Int,
    ): MinimalEventListResponseDto

    @GET("/users/{userId}/events/{eventId}")
    suspend fun getSingleEvent(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Path("eventId") eventId: String,
    )

    @DELETE("/users/{userId}/events/{eventId}")
    suspend fun deleteEvent(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Path("eventId") eventId: String,
    )
}