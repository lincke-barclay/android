package com.alth.events.data.caching.friends

import com.alth.events.database.DatabaseTransactionUseCase
import com.alth.events.database.models.friends.FriendType
import com.alth.events.database.models.friends.FriendshipEntity
import com.alth.events.database.sources.friendships.FriendshipLocalDataSource
import com.alth.events.database.sources.users.UserLocalDataSource
import com.alth.events.models.domain.friends.FriendsQuery
import com.alth.events.networking.models.NetworkResult
import com.alth.events.networking.models.users.ingress.PublicUserResponseDto
import com.alth.events.networking.sources.NetworkFriendshipDataSource
import javax.inject.Inject

class MyConfirmedFriendsCachingManager @Inject constructor(
    private val networkFriendshipDataSource: NetworkFriendshipDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val friendshipLocalDataSource: FriendshipLocalDataSource,
    private val transactionUseCase: DatabaseTransactionUseCase,
) {
    suspend fun updateLocalFriends(
        query: FriendsQuery,
        invalidateCache: Boolean,
    ): NetworkResult<List<PublicUserResponseDto>> {

        val response = networkFriendshipDataSource.getMyConfirmedFriends(
            page = query.page,
            pageSize = query.pageSize,
        )

        when (response) {
            is NetworkResult.Success -> {
                val friendships = response.t.map {
                    FriendshipEntity.from(it, FriendType.Accepted)
                }
                transactionUseCase {
                    if (invalidateCache) {
                        friendshipLocalDataSource.clearAccepted()
                    }
                    userLocalDataSource.upsertAll(response.t)
                    friendshipLocalDataSource.upsertAll(friendships)
                }
            }

            is NetworkResult.IOFailure -> {}
        }

        return response
    }
}