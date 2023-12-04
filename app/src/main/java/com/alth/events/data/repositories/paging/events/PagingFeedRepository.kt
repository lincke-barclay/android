package com.alth.events.data.repositories.paging.events

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.alth.events.data.mediators.events.FeedRemoteMediator
import com.alth.events.database.models.events.derived.FeedEvent
import com.alth.events.database.sources.events.FeedLocalDataSource
import com.alth.events.models.events.FeedEventQuery
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PagingFeedRepository @Inject constructor(
    private val feedRemoteMediator: FeedRemoteMediator,
    private val feedLocalDataSource: FeedLocalDataSource,
) {
    private val pageSize = 20 // TODO - configure this in application properties

    fun getFeedEventFlow(
        feedEventQuery: FeedEventQuery,
    ): Pager<Int, FeedEvent> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
            ),
            remoteMediator = feedRemoteMediator,
        ) {
            feedLocalDataSource.getFeedPagingSource()
        }
    }
}
