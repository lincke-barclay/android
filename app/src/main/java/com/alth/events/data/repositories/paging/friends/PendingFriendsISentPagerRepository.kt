package com.alth.events.data.repositories.paging.friends

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.alth.events.data.mediators.friends.FriendsISentRemoteMediator
import com.alth.events.database.models.users.PublicUserEntity
import com.alth.events.database.sources.friendships.FriendshipLocalDataSource
import com.alth.events.logging.impl.loggerFactory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PendingFriendsISentPagerRepository @Inject constructor(
    private val friendshipLocalDataSource: FriendshipLocalDataSource,
    private val friendsISentRemoteMediator: FriendsISentRemoteMediator,
) {
    private val pageSize = 20
    private val logger = loggerFactory.getLogger(this)

    fun pager(): Flow<PagingData<PublicUserEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                prefetchDistance = 2,
            ),
            remoteMediator = friendsISentRemoteMediator,
        ) {
            friendshipLocalDataSource.getPendingSentFromMe()
        }.flow
    }
}
