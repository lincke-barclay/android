package com.alth.events.data.repositories

import com.alth.events.authentication.sources.AuthenticationDataSource
import com.alth.events.authentication.sources.withIDOrThrow
import com.alth.events.data.caching.users.QueriedUsersCachingManager
import com.alth.events.database.models.users.PublicUserEntity
import com.alth.events.database.sources.users.QueriedUserLocalDataSource
import com.alth.events.database.sources.users.UserLocalDataSource
import com.alth.events.logging.impl.loggerFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class QuickSearchUserRepository @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource,
    private val authenticationDataSource: AuthenticationDataSource,
    private val queriedUserLocalDataSource: QueriedUserLocalDataSource,
    private val queriedUsersCachingManager: QueriedUsersCachingManager,
) {
    private val sizeLimit = 10 // number of results to display at once
    private val logger = loggerFactory.getLogger(this)

    private val _results: MutableStateFlow<List<PublicUserEntity>> = MutableStateFlow(emptyList())
    val results = _results.asStateFlow()

    // TODO - this is definitely faster as a sql query
    suspend fun onQueryChange(query: String) {
        logger.debug("New query: $query")
        _results.value = authenticationDataSource.withIDOrThrow { myId ->
            userLocalDataSource.getPublicUsers()
                .filter { it.id != myId }
                .also { logger.debug("Total number of users ${it.size}") }
                .filter { query.lowercase() in it.name.lowercase() }
                .also { logger.debug("Total number of users after filter: ${it.size}") }
                .take(sizeLimit)
        }
    }

    suspend fun populateCache(query: String) {
        logger.debug("Populating cache for query like: $query")
        val nextPage = queriedUserLocalDataSource.countAllByQuery(query) / sizeLimit
        queriedUsersCachingManager.updateLocalSearchResultsForQuery(
            query = query,
            page = nextPage,
            pageSize = sizeLimit,
        )
    }
}