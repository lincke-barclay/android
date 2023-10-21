package com.alth.events.data.feed

import com.alth.events.networking.apis.EventsBackendRetrofitClient
import com.alth.events.networking.apis.models.POSTEventRequestDTO
import com.alth.events.networking.apis.models.RemoteEventResponseDTO
import javax.inject.Inject

interface RemoteFeedDataSource {
    suspend fun getFeed(): List<RemoteEventResponseDTO>
    suspend fun postEvent(postEventRequestDTO: POSTEventRequestDTO): RemoteEventResponseDTO
}

class RetrofitRemoteFeedDataSource @Inject constructor(
    private val eventsApi: EventsBackendRetrofitClient,
) : RemoteFeedDataSource {
    override suspend fun getFeed(): List<RemoteEventResponseDTO> {
        return eventsApi.getFeed()
    }

    override suspend fun postEvent(postEventRequestDTO: POSTEventRequestDTO): RemoteEventResponseDTO {
        return eventsApi.postEvent(postEventRequestDTO)
    }
}
