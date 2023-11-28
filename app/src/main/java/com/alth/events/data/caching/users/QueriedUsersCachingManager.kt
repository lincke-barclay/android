package com.alth.events.data.caching.users

import com.alth.events.database.DatabaseTransactionUseCase
import com.alth.events.database.sources.users.QueriedUserLocalDataSource
import com.alth.events.database.sources.users.UserLocalDataSource
import com.alth.events.networking.models.NetworkResult
import com.alth.events.networking.models.users.ingress.PublicUserResponseDto
import com.alth.events.networking.sources.NetworkUserDataSource
import javax.inject.Inject

class QueriedUsersCachingManager @Inject constructor(
    private val queriedUserLocalDataSource: QueriedUserLocalDataSource,
    private val transaction: DatabaseTransactionUseCase,
    private val userLocalDataSource: UserLocalDataSource,
    private val networkUserDataSource: NetworkUserDataSource,
) {
    /**
     * Updates the local user database with the
     * server
     * @param query Send queryStr to user fetch
     * @param invalidateCacheForQuery If True - removes all users __only for the
     * given query string__ from the cache before executing save
     * @param invalidateCache If true - removes all users from cache
     */
    suspend fun updateLocalSearchResultsForQuery(
        query: String,
        page: Int,
        pageSize: Int,
        invalidateCacheForQuery: Boolean = false,
        invalidateCache: Boolean = false,
    ): NetworkResult<List<PublicUserResponseDto>> {
        // Find next query based on last event result

        val response = networkUserDataSource.getUsersByQuery(
            page = page,
            pageSize = pageSize,
            queryStr = query,
        )

        when (response) {
            is NetworkResult.Success -> {
                transaction {
                    if (invalidateCache) {
                        userLocalDataSource.clearAll()
                    } else if (invalidateCacheForQuery) {
                        queriedUserLocalDataSource.clearSearchResultsByQuery(query)
                    }
                    queriedUserLocalDataSource.upsertNewQueryUsersByQuery(
                        response.t,
                        query,
                    )
                }
            }

            is NetworkResult.IOFailure -> {}
        }

        return response
    }
}