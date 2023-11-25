package com.alth.events.data.repositories.paging.events

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.alth.events.data.mediators.events.FeedRemoteMediator
import com.alth.events.database.sources.events.FeedLocalDataSource
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PagingFeedRepository @Inject constructor(
    feedRemoteMediator: FeedRemoteMediator,
    feedLocalDataSource: FeedLocalDataSource,
) {
    private val pageSize = 20 // TODO - configure this in application properties

    val feedEventFlow = Pager(
        config = PagingConfig(
            pageSize = pageSize,
        ),
        remoteMediator = feedRemoteMediator,
    ) {
        feedLocalDataSource.getFeedPagingSource()
    }.flow
}
