package com.alth.events.database.sources.users

import androidx.room.Transaction
import com.alth.events.database.dao.users.QueriedUserDao
import com.alth.events.networking.models.users.ingress.PublicUserResponseDto
import com.alth.events.transforms.networkToDatabase.toQueriedUser
import javax.inject.Inject

class QueriedUserLocalDataSource @Inject constructor(
    private val queriedUserDao: QueriedUserDao,
    private val userLocalDataSource: UserLocalDataSource,
) {
    @Transaction
    suspend fun upsertNewQueryUsersByQuery(users: List<PublicUserResponseDto>, query: String) {
        userLocalDataSource.upsertAll(users)
        queriedUserDao.upsertAll(users.map { it.toQueriedUser(query) })
    }

    suspend fun clearSearchResultsByQuery(query: String) {
        queriedUserDao.clearUsersByQuery(query)
    }

    fun getQueryUsersForQuery(
        query: String,
    ) = queriedUserDao.getSearchPagerForQuery(query)


    suspend fun countAllByQuery(query: String) = queriedUserDao.countAllByQuery(query)
}