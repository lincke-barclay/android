package com.alth.events.database.sources.friendships

import com.alth.events.database.dao.users.FriendshipDao
import com.alth.events.database.models.friends.FriendshipEntity
import javax.inject.Inject

class FriendshipLocalDataSource @Inject constructor(
    private val friendshipDao: FriendshipDao,
) {
    fun getAccepted() = friendshipDao.getAccepted()
    suspend fun getNumberAccepted() = friendshipDao.getNumberAccepted()

    fun getPendingSentFromMe() = friendshipDao.getPendingSentFromMe()
    suspend fun getNumberPendingSentFromMe() = friendshipDao.getNumberPendingSentFromMe()

    fun getPendingSentToMe() = friendshipDao.getPendingSentToMe()
    suspend fun getNumberPendingSentToMe() = friendshipDao.getNumberPendingSentToMe()

    suspend fun upsertAll(friendships: List<FriendshipEntity>) =
        friendshipDao.upsertAll(friendships)

    suspend fun clearAccepted() = friendshipDao.clearAccepted()
    suspend fun clearPendingSentFromMe() = friendshipDao.clearPendingSentFromMe()
    suspend fun clearPendingSentToMe() = friendshipDao.clearPendingSentToMe()
}