package com.alth.events.database.sources.events

import androidx.paging.PagingSource
import com.alth.events.authentication.sources.AuthenticationDataSource
import com.alth.events.authentication.sources.withIDOrThrow
import com.alth.events.database.dao.events.EventForUserDao
import com.alth.events.database.models.events.derived.AnonymousEvent
import com.alth.events.networking.models.events.ingress.PrivateEventResponseDto
import javax.inject.Inject

class MyEventsLocalDataSource @Inject constructor(
    private val eventForUserDao: EventForUserDao,
    private val authenticationDataSource: AuthenticationDataSource,
    private val localEventDataSource: LocalEventDataSource,
) {
    suspend fun insertNewMyEvents(elements: List<PrivateEventResponseDto>) {
        authenticationDataSource.withIDOrThrow { id ->
            localEventDataSource.upsertAll(elements, id)
        }
    }

    suspend fun clearMyEvents() {
        authenticationDataSource.withIDOrThrow { id ->
            eventForUserDao.clearEventsByUser(id)
        }
    }

    fun getMyEventsPagingSource(myId: String): PagingSource<Int, AnonymousEvent> {
        return eventForUserDao.getEventsForUser(myId)
    }
}
