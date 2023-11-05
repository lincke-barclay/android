package com.alth.events.repositories

import com.alth.events.models.domain.events.FeedEvent
import com.alth.events.models.domain.events.NewEventRequest
import com.alth.events.models.domain.events.SuggestedEvent

interface CachingEventRepository {
    suspend fun addNewEvent(newEventRequest: NewEventRequest)
    suspend fun getSuggestedEvents(
        page: Int,
        pageSize: Int
    ): GenericCachingOperation<List<SuggestedEvent>>

    suspend fun getFeed(page: Int, pageSize: Int): GenericCachingOperation<List<FeedEvent>>
}
