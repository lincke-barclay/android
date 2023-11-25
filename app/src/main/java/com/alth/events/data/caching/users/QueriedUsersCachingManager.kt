package com.alth.events.data.caching.users

import com.alth.events.database.DatabaseTransactionUseCase
import com.alth.events.database.sources.users.QueriedUserLocalDataSource
import com.alth.events.database.sources.users.UserLocalDataSource
import com.alth.events.networking.models.NetworkResult
import com.alth.events.networking.models.users.ingress.PublicUserResponseDto
import com.alth.events.networking.sources.NetworkFriendshipDataSource
import javax.inject.Inject

class QueriedUsersCachingManager @Inject constructor(
    private val queriedUserLocalDataSource: QueriedUserLocalDataSource,
    private val networkFriendshipDataSource: NetworkFriendshipDataSource,
    private val transaction: DatabaseTransactionUseCase,
    private val userLocalDataSource: UserLocalDataSource,
) {
    suspend fun updateLocalSearchResultsForQuery(
        query: String,
        page: Int,
        pageSize: Int,
        invalidateCache: Boolean,
    ): NetworkResult<List<PublicUserResponseDto>> {
        // Find next query based on last event result

        val response = networkFriendshipDataSource.getSuggestedFriends(
            page = page,
            pageSize = pageSize,
            queryStr = query,
        )

        when (response) {
            is NetworkResult.Success -> {
                transaction {
                    if (invalidateCache) {
                        queriedUserLocalDataSource.clearSearchResultsByQuery(query)
                    }
                    queriedUserLocalDataSource.upsertNewQueryUsersByQuery(
                        response.t,
                        query,
                    )
                    userLocalDataSource.upsertAll(response.t)
                }
            }

            is NetworkResult.IOFailure -> {}
        }

        return response
    }
}