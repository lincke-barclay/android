package com.alth.events.data.repositories.paging.users

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alth.events.data.caching.users.QueriedUsersCachingManager
import com.alth.events.data.mediators.users.QueriedUserRemoteMediator
import com.alth.events.database.models.users.PublicUserEntity
import com.alth.events.database.sources.users.QueriedUserLocalDataSource
import com.alth.events.logging.impl.loggerFactory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class UserQueryPagerRepository @Inject constructor(
    private val queriedUsersCachingManager: QueriedUsersCachingManager,
    private val queriedUserLocalDataSource: QueriedUserLocalDataSource,
) {
    private val pageSize = 20 // TODO - configure this in application properties
    private val logger = loggerFactory.getLogger(this)

    fun pager(query: String): Pager<Int, PublicUserEntity> {
        logger.debug("Creating new search event mediator for query: $query")
        val mediator = QueriedUserRemoteMediator(
            queriedUsersCachingManager,
            searchQuery = query,
            queriedUserLocalDataSource = queriedUserLocalDataSource,
        )
        logger.debug("Successfully created new mediator")

        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
            ),
            remoteMediator = mediator,
        ) {
            queriedUserLocalDataSource.getQueryUsersForQuery(query)
        }
    }
}
