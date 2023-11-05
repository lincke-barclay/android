package com.alth.events.repositories.impl

import com.alth.events.di.ApplicationScope
import com.alth.events.models.domain.events.FeedEvent
import com.alth.events.models.domain.events.NewEventRequest
import com.alth.events.models.domain.events.SuggestedEvent
import com.alth.events.models.network.events.egress.toPOSTEventRequestDTO
import com.alth.events.networking.sources.NetworkEventDataSource
import com.alth.events.repositories.CachingEventRepository
import com.alth.events.repositories.GenericCachingOperation
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InMemoryCachingEventsRepository @Inject constructor(
    private val networkEventDataSource: NetworkEventDataSource,
    @ApplicationScope private val appScope: CoroutineScope,
) : CachingEventRepository {

    private var suggestedEvents: List<SuggestedEvent> = listOf()
    private var feedEvents: List<FeedEvent> = listOf()

    override suspend fun addNewEvent(newEventRequest: NewEventRequest) {
        networkEventDataSource.createEvent(newEventRequest.toPOSTEventRequestDTO())
    }

    override suspend fun getSuggestedEvents(page: Int, pageSize: Int) =
        object : GenericCachingOperation<List<SuggestedEvent>>(appScope, listOf()) {
            override suspend fun getFromSourceOfTruth(): InternalCacheResult<List<SuggestedEvent>> {
                return networkEventDataSource.getSuggestedEventsForUser(page, pageSize)
                    .toInternalCacheResult {
                        map { it.toSuggestedEvent() }
                    }
            }

            override suspend fun getFromCache(): InternalCacheResult<List<SuggestedEvent>> {
                return InternalCacheResult.Success(suggestedEvents)
            }

            override suspend fun saveToCache(t: List<SuggestedEvent>): InternalCacheResult<Unit> {
                suggestedEvents = t
                return InternalCacheResult.Success(Unit)
            }

        }

    override suspend fun getFeed(page: Int, pageSize: Int) =
        object : GenericCachingOperation<List<FeedEvent>>(appScope, listOf()) {
            override suspend fun getFromSourceOfTruth(): InternalCacheResult<List<FeedEvent>> {
                return networkEventDataSource.getFeedForUser(page, pageSize)
                    .toInternalCacheResult {
                        map { it.toFeedEvent() }
                    }
            }

            override suspend fun getFromCache(): InternalCacheResult<List<FeedEvent>> {
                return InternalCacheResult.Success(feedEvents)
            }

            override suspend fun saveToCache(t: List<FeedEvent>): InternalCacheResult<Unit> {
                feedEvents = t
                return InternalCacheResult.Success(Unit)
            }
        }
}
