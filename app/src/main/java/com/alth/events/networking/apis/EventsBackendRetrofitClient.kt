package com.alth.events.networking.apis

import com.alth.events.networking.apis.models.POSTEventRequestDTO
import com.alth.events.networking.apis.models.RemoteEventResponseDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface EventsBackendRetrofitClient {
    @GET("/feed")
    suspend fun getFeed(): List<RemoteEventResponseDTO>

    @POST("/events")
    suspend fun postEvent(
        @Body postEventRequestDTO: POSTEventRequestDTO,
    ): RemoteEventResponseDTO
}
