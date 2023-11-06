package com.alth.events.networking.sources

import com.alth.events.models.network.NetworkResult
import com.alth.events.models.network.events.egress.POSTEventRequestDTO
import com.alth.events.models.network.events.ingress.MinimalEventListResponseDto
import com.alth.events.models.network.events.ingress.PrivateEventResponseDto

interface NetworkEventDataSource {
    suspend fun getFeedForUser(
        page: Int,
        pageSize: Int,
    ): NetworkResult<MinimalEventListResponseDto>

    suspend fun getSuggestedEventsForUser(
        page: Int,
        pageSize: Int,
    ): NetworkResult<MinimalEventListResponseDto>

    suspend fun getPrivateEvents(
        page: Int,
        pageSize: Int,
    ): NetworkResult<MinimalEventListResponseDto>

    suspend fun createEvent(
        body: POSTEventRequestDTO,
    ): NetworkResult<PrivateEventResponseDto>

    suspend fun deleteEvent(
        eventId: String,
    ): NetworkResult<Unit>
}
