package com.alth.events.data.repositories

import com.alth.events.authentication.sources.AuthenticationDataSource
import com.alth.events.authentication.sources.withIDOrThrow
import com.alth.events.database.DatabaseTransactionUseCase
import com.alth.events.database.models.users.PublicUserEntity
import com.alth.events.database.sources.users.QueriedUserLocalDataSource
import com.alth.events.database.sources.users.UserLocalDataSource
import com.alth.events.logging.impl.loggerFactory
import com.alth.events.networking.models.NetworkResult
import com.alth.events.networking.sources.NetworkFriendshipDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class QuickSearchUserRepository @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource,
    private val authenticationDataSource: AuthenticationDataSource,
    private val friendshipDataSource: NetworkFriendshipDataSource,
    private val queriedUserLocalDataSource: QueriedUserLocalDataSource,
    private val transactionUseCase: DatabaseTransactionUseCase,
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
        val num = queriedUserLocalDataSource.countAllByQuery(query)
        val nextPage = num / sizeLimit + 1
        val response = friendshipDataSource.getSuggestedFriends(
            page = nextPage,
            pageSize = sizeLimit,
            queryStr = query,
        )
        when (response) {
            is NetworkResult.Success -> {
                transactionUseCase {
                    queriedUserLocalDataSource.upsertNewQueryUsersByQuery(
                        users = response.t,
                        query = query,
                    )
                    userLocalDataSource.upsertAll(response.t)
                }
            }

            is NetworkResult.IOFailure -> {
                logger.warn("Couldn't populate cache")
            }
        }
    }
}