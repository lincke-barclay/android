package com.alth.events.networking.apis

import com.alth.events.models.network.events.egress.POSTEventRequestDTO
import com.alth.events.models.network.events.ingress.GETPrivateEventResponseDTO
import com.alth.events.models.network.events.ingress.GETPublicEventResponseDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface EventsApi {
    @GET("/users/{userId}/events/feed")
    suspend fun getFeedForUser(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
    ): List<GETPublicEventResponseDTO>

    @GET("/users/{userId}/events/suggested")
    suspend fun getSuggestedEventsForUser(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
    ): List<GETPublicEventResponseDTO>

    @GET("/users/{userId}/events")
    suspend fun getPrivateEvents(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
    ): List<GETPrivateEventResponseDTO>

    @POST("/users/{userId}/events")
    suspend fun createEvent(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Body body: POSTEventRequestDTO,
    ): GETPrivateEventResponseDTO

    @DELETE("/users/{userId}/events/{eventId}")
    suspend fun deleteEvent(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Path("eventId") eventId: String,
    )
}