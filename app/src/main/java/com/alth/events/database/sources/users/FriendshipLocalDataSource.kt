package com.alth.events.database.sources.users

import com.alth.events.database.dao.users.FriendshipDao
import com.alth.events.database.models.users.FriendshipEntity
import com.alth.events.database.models.users.PublicUserEntity
import javax.inject.Inject

class FriendshipLocalDataSource @Inject constructor(
    private val friendshipDao: FriendshipDao,
) {
    suspend fun getAccepted(): List<PublicUserEntity> {
        return friendshipDao.getAccepted()
    }

    suspend fun getPendingSentFromMe(): List<PublicUserEntity> {
        return friendshipDao.getPendingSentFromMe()
    }

    suspend fun getPendingSentToMe(): List<PublicUserEntity> {
        return friendshipDao.getPendingSentToMe()
    }

    suspend fun upsertAll(friendships: List<FriendshipEntity>) {
        friendshipDao.upsertAll(friendships)
    }
}