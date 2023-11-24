package com.alth.events.data.caching.events

import com.alth.events.database.DatabaseTransactionUseCase
import com.alth.events.database.sources.LastUpdateLocalDataSource
import com.alth.events.database.sources.users.UserLocalDataSource
import com.alth.events.database.sources.events.FeedLocalDataSource
import com.alth.events.networking.models.NetworkResult
import com.alth.events.networking.models.events.ingress.MinimalEventListResponseDto
import com.alth.events.networking.sources.NetworkEventDataSource
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeedCachingManager @Inject constructor(
    private val networkEventDataSource: NetworkEventDataSource,
    private val feedLocalDataSource: FeedLocalDataSource,
    private val transactionUseCase: DatabaseTransactionUseCase,
    private val userLocalDataSource: UserLocalDataSource,
    private val lastUpdateLocalDataSource: LastUpdateLocalDataSource,
) {
    // TODO - make property
    private val cacheExpiry = TimeUnit.HOURS.convert(1, TimeUnit.MILLISECONDS)

    suspend fun doesFeedCacheNeedUpdating(): Boolean {
        val lastFeedUpdateMillis = lastUpdateLocalDataSource
            .getLastUpdatedFeedRefresh()
            .toEpochMilliseconds()
        return System.currentTimeMillis() - lastFeedUpdateMillis <= cacheExpiry
    }

    suspend fun updateLocalFeed(
        lastFeedItemId: String? = null,
        limit: Int,
        invalidateCache: Boolean,
    ): NetworkResult<MinimalEventListResponseDto> {
        val response = networkEventDataSource.getFeedForUser(
            lastFeedItemId,
            limit,
        )
        when (response) {
            is NetworkResult.Success -> {
                transactionUseCase {
                    if (invalidateCache) {
                        feedLocalDataSource.clearFeed()
                    }
                    feedLocalDataSource.upsertFeed(response.t.publicEvents)
                    userLocalDataSource.upsertAll(response.t.publicUsers.values.toList())
                }
            }

            is NetworkResult.IOFailure -> {}
        }

        return response
    }
}
