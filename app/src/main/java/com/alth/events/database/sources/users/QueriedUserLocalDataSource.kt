package com.alth.events.database.sources.users

import androidx.paging.PagingSource
import androidx.room.Transaction
import com.alth.events.database.dao.users.QueriedUserDao
import com.alth.events.database.models.users.PublicUserEntity
import com.alth.events.logging.impl.loggerFactory
import com.alth.events.networking.models.users.ingress.PublicUserResponseDto
import com.alth.events.transforms.networkToDatabase.toQueriedUser
import javax.inject.Inject

class QueriedUserLocalDataSource @Inject constructor(
    private val queriedUserDao: QueriedUserDao,
    private val userLocalDataSource: UserLocalDataSource,
) {
    private val logger = loggerFactory.getLogger(this)

    @Transaction
    suspend fun upsertNewQueryUsersByQuery(users: List<PublicUserResponseDto>, query: String) {
        userLocalDataSource.upsertAll(users)
        queriedUserDao.upsertAll(users.map { it.toQueriedUser(query) })
    }

    //suspend fun upsertAll(queried: List<QueriedUserEntity>) = queriedUserDao.upsertAll(queried)

    suspend fun clearSearchResultsByQuery(query: String) {
        queriedUserDao.clearUsersByQuery(query)
    }

    fun getQueryUsersForQuery(
        query: String,
    ): PagingSource<Int, PublicUserEntity> {
        return queriedUserDao.getSearchPagerForQuery(query)
    }

    // For debug purposes only
    suspend fun getAll() = queriedUserDao.getAll()


    suspend fun countAllByQuery(query: String) = queriedUserDao.countAllByQuery(query)
}