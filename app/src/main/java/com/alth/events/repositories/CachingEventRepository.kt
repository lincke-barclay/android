package com.alth.events.repositories

import com.alth.events.models.network.events.egress.POSTEventRequestDTO
import com.alth.events.models.network.events.ingress.MinimalEventListResponseDto

interface CachingEventRepository {
    suspend fun addNewEvent(newEventRequest: POSTEventRequestDTO)
    suspend fun getSuggestedEvents(
        page: Int,
        pageSize: Int
    ): GenericCachingOperation<MinimalEventListResponseDto>

    suspend fun getFeed(
        page: Int,
        pageSize: Int
    ): GenericCachingOperation<MinimalEventListResponseDto>
}
