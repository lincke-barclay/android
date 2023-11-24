package com.alth.events.database.sources.events

import androidx.paging.PagingSource
import androidx.room.Transaction
import com.alth.events.database.dao.events.FeedDao
import com.alth.events.database.models.events.derived.FeedEvent
import com.alth.events.database.sources.LastUpdateLocalDataSource
import com.alth.events.networking.models.events.ingress.PublicEventResponseDto
import com.alth.events.transforms.networkToDatabase.toFeedEventEntity
import javax.inject.Inject

class FeedLocalDataSource @Inject constructor(
    private val feedDao: FeedDao,
    private val localEventDataSource: LocalEventDataSource,
    private val lastUpdateLocalDataSource: LastUpdateLocalDataSource,
) {
    @Transaction
    suspend fun upsertFeed(elements: List<PublicEventResponseDto>) {
        lastUpdateLocalDataSource.doWithFeedUpdate {
            // upsert new events
            localEventDataSource.upsertAll(elements)

            // Update feed table notifying of changes
            feedDao.upsertAll(elements.map { it.toFeedEventEntity() })
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
