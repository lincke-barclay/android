package com.alth.events.repositories.impl

import com.alth.events.di.ApplicationScope
import com.alth.events.models.network.events.egress.POSTEventRequestDTO
import com.alth.events.models.network.events.ingress.MinimalEventListResponseDto
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

    private var suggestedEvents: MinimalEventListResponseDto = MinimalEventListResponseDto.empty()
    private var feedEvents = MinimalEventListResponseDto.empty()

    override suspend fun addNewEvent(newEventRequest: POSTEventRequestDTO) {
        networkEventDataSource.createEvent(newEventRequest)
    }

    override suspend fun getSuggestedEvents(
        page: Int,
        pageSize: Int,
        queryStr: String,
    ) = object : GenericCachingOperation<MinimalEventListResponseDto>(
        appScope,
        MinimalEventListResponseDto.empty()
    ) {
        override suspend fun getFromSourceOfTruth() =
            networkEventDataSource.getSuggestedEventsForUser(page, pageSize, queryStr)
                .toInternalCacheResult()

        override suspend fun getFromCache() = InternalCacheResult.Success(suggestedEvents)

        override suspend fun saveToCache(t: MinimalEventListResponseDto): InternalCacheResult<Unit> {
            suggestedEvents = t
            return InternalCacheResult.Success(Unit)
        }

    }

    override suspend fun getFeed(page: Int, pageSize: Int) =
        object : GenericCachingOperation<MinimalEventListResponseDto>(
            appScope,
            MinimalEventListResponseDto.empty()
        ) {
            override suspend fun getFromSourceOfTruth() = run {
                networkEventDataSource.getFeedForUser(page, pageSize)
                    .toInternalCacheResult()
            }

            override suspend fun getFromCache() = InternalCacheResult.Success(feedEvents)

            override suspend fun saveToCache(t: MinimalEventListResponseDto): InternalCacheResult<Unit> {
                feedEvents = t
                return InternalCacheResult.Success(Unit)
            }

        }
}
