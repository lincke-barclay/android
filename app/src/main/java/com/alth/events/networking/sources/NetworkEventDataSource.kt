package com.alth.events.networking.sources

import com.alth.events.models.network.NetworkResult
import com.alth.events.models.network.events.egress.POSTEventRequestDTO
import com.alth.events.models.network.events.ingress.GETPrivateEventResponseDTO
import com.alth.events.models.network.events.ingress.GETPublicEventResponseDTO

interface NetworkEventDataSource {
    suspend fun getFeedForUser(
        page: Int,
        pageSize: Int,
    ): NetworkResult<List<GETPublicEventResponseDTO>>

    suspend fun getSuggestedEventsForUser(
        page: Int,
        pageSize: Int,
    ): NetworkResult<List<GETPublicEventResponseDTO>>

    suspend fun getPrivateEvents(
        page: Int,
        pageSize: Int,
    ): NetworkResult<List<GETPrivateEventResponseDTO>>

    suspend fun createEvent(
        body: POSTEventRequestDTO,
    ): NetworkResult<GETPrivateEventResponseDTO>

    suspend fun deleteEvent(
        eventId: String,
    ): NetworkResult<Unit>
}
