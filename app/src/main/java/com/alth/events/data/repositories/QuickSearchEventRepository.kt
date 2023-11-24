package com.alth.events.data.repositories

import com.alth.events.authentication.sources.AuthenticationDataSource
import com.alth.events.authentication.sources.withIDOrThrow
import com.alth.events.database.DatabaseTransactionUseCase
import com.alth.events.database.models.events.derived.SearchEventResult
import com.alth.events.database.sources.users.UserLocalDataSource
import com.alth.events.database.sources.events.SearchEventsLocalDataSource
import com.alth.events.logging.impl.loggerFactory
import com.alth.events.models.domain.events.PublicEventQuery
import com.alth.events.networking.models.NetworkResult
import com.alth.events.networking.sources.NetworkEventDataSource
import com.alth.events.transforms.domain.toSearchEventResultList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class QuickSearchEventRepository @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource,
    private val authenticationDataSource: AuthenticationDataSource,
    private val networkEventDataSource: NetworkEventDataSource,
    private val searchEventsLocalDataSource: SearchEventsLocalDataSource,
    private val transactionUseCase: DatabaseTransactionUseCase,
) {
    private val sizeLimit = 10 // number of results to display at once
    private val logger = loggerFactory.getLogger(this)

    private val _results: MutableStateFlow<List<SearchEventResult>> = MutableStateFlow(emptyList())
    val results = _results.asStateFlow()

    suspend fun onQueryChange(query: PublicEventQuery) {
        logger.debug("New query: $query")
        _results.value = authenticationDataSource.withIDOrThrow { myId ->
            userLocalDataSource.getPublicUsersWithTheirEvents()
                .filter { it.owner.id != myId }
                .also { logger.debug("Total number of users with events: ${it.size}") }
                .flatMap { it.toSearchEventResultList() }
                .also { logger.debug("Total number of events for all those users: ${it.size}") }
                .filter { it.matches(query) }
                .also { logger.debug("Total number of events after filter: ${it.size}") }
                .take(sizeLimit)
        }
    }

    suspend fun populateCache(query: PublicEventQuery) {
        logger.debug("Populating cache for query like: $query")
        val response = networkEventDataSource.getEventsByQueryParameters(
            fromStartDateTimeInclusive = query.fromStartDateTimeInclusive,
            fromEndDateTimeInclusive = query.fromEndDateTimeInclusive,
            toStartDateTimeInclusive = query.toStartDateTimeInclusive,
            toEndDateTimeInclusive = query.toEndDateTimeInclusive,
            titleContainsIC = query.titleContainsIC,
            sortBy = query.sortBy,
            sortDirection = query.sortDirection,
            limit = sizeLimit,
        )
        when (response) {
            is NetworkResult.Success -> {
                transactionUseCase {
                    searchEventsLocalDataSource.upsertNewSearchEventsByQuery(
                        events = response.t.publicEvents,
                        query = query,
                    )
                    userLocalDataSource.upsertAll(response.t.publicUsers.values.toList())
                }
            }

            is NetworkResult.IOFailure -> {
                logger.warn("Couldn't populate cache")
            }
        }
    }
}