package com.alth.events.database.sources.events

import androidx.paging.PagingSource
import com.alth.events.database.dao.events.FeedDao
import com.alth.events.database.models.derived.FeedEvent
import com.alth.events.database.sources.LastUpdateLocalDataSource
import com.alth.events.networking.models.events.ingress.PublicEventResponseDto
import javax.inject.Inject

class FeedLocalDataSource @Inject constructor(
    private val feedDao: FeedDao,
    private val localEventDataSource: LocalEventDataSource,
    private val lastUpdateLocalDataSource: LastUpdateLocalDataSource,
) {
    suspend fun upsertFeed(elements: List<PublicEventResponseDto>) {
        return lastUpdateLocalDataSource.doWithFeedUpdate {
            localEventDataSource.upsertAll(
                elements,
                isFeed = true,
            )
        }
    }

    suspend fun clearFeed() {
        return lastUpdateLocalDataSource.doWithFeedUpdate {
            feedDao.clearFeed()
        }
    }

    fun getFeedPagingSource(): PagingSource<Int, FeedEvent> {
        return feedDao.getFeedPagingSource()
    }
}
