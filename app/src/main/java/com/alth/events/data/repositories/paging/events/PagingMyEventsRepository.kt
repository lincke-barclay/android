package com.alth.events.data.repositories.paging.events

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alth.events.authentication.sources.AuthenticationDataSource
import com.alth.events.authentication.sources.withIDOrThrow
import com.alth.events.data.caching.events.MyQueriedEventsCachingManager
import com.alth.events.data.mediators.events.MyEventsRemoteMediator
import com.alth.events.database.models.events.derived.AnonymousEvent
import com.alth.events.database.sources.events.MyEventsLocalDataSource
import com.alth.events.models.domain.events.PublicEventQuery
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PagingMyEventsRepository @Inject constructor(
    private val myEventLocalDataSource: MyEventsLocalDataSource,
    private val authenticationDataSource: AuthenticationDataSource,
    private val myQueriedEventsCachingManager: MyQueriedEventsCachingManager,
) {
    private val pageSize = 20 // TODO - configure this in application properties

    suspend fun myEventsFlow(query: PublicEventQuery): Flow<PagingData<AnonymousEvent>> {
        val myEventsRemoteMediator = MyEventsRemoteMediator(
            myQueriedEventsCachingManager = myQueriedEventsCachingManager,
            searchQuery = query,
        )
        return authenticationDataSource.withIDOrThrow {
            Pager(
                config = PagingConfig(
                    pageSize = pageSize,
                    prefetchDistance = 2,
                ),
                remoteMediator = myEventsRemoteMediator,
            ) {
                myEventLocalDataSource.getMyEventsPagingSource(it)
            }.flow
        }

    }
}