package com.alth.events.networking.sources.impl

import com.alth.events.authentication.sources.AuthenticationDataSource
import com.alth.events.models.network.events.egress.POSTEventRequestDTO
import com.alth.events.networking.apis.EventsApi
import com.alth.events.networking.sources.NetworkEventDataSource
import com.alth.events.networking.withIDAndTokenOrThrowNetworkExec
import javax.inject.Inject

class RetrofitNetworkEventDataSource @Inject constructor(
    private val eventsApi: EventsApi,
    private val authenticationDataSource: AuthenticationDataSource,
) : NetworkEventDataSource {
    override suspend fun getFeedForUser(page: Int, pageSize: Int) =
        authenticationDataSource.withIDAndTokenOrThrowNetworkExec { id, token ->
            eventsApi.getFeedForUser(
                token = token,
                userId = id,
                page = page,
                pageSize = pageSize,
            )
        }

    override suspend fun getSuggestedEventsForUser(
        page: Int,
        pageSize: Int,
        queryStr: String,
    ) = authenticationDataSource.withIDAndTokenOrThrowNetworkExec { id, token ->
        eventsApi.getSuggestedEventsForUser(
            token = token,
            userId = id,
            page = page,
            pageSize = pageSize,
            queryStr = queryStr,
        )
    }

    override suspend fun getPrivateEvents(
        page: Int,
        pageSize: Int
    ) = authenticationDataSource.withIDAndTokenOrThrowNetworkExec { id, token ->
        eventsApi.getPrivateEvents(
            token = token,
            userId = id,
            page = page,
            pageSize = pageSize,
        )
    }

    override suspend fun createEvent(
        body: POSTEventRequestDTO
    ) = authenticationDataSource.withIDAndTokenOrThrowNetworkExec { id, token ->
        eventsApi.createEvent(
            token = token,
            userId = id,
            body = body,
        )
    }

    override suspend fun deleteEvent(
        eventId: String
    ) = authenticationDataSource.withIDAndTokenOrThrowNetworkExec { id, token ->
        eventsApi.deleteEvent(
            token = token,
            userId = id,
            eventId = eventId,
        )
    }

}
