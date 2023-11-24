package com.alth.events.database.sources

import com.alth.events.database.dao.PublicUserDao
import com.alth.events.database.models.derived.PublicUserWithEvents
import com.alth.events.networking.models.users.ingress.PublicUserResponseDto
import com.alth.events.transforms.networkToDatabase.toDatabaseUser
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(
    private val userDao: PublicUserDao,
) {
    suspend fun upsertAll(users: List<PublicUserResponseDto>) {
        return userDao.insertAll(users.map { it.toDatabaseUser() })
    }

    suspend fun getPublicUsersWithTheirEvents(): List<PublicUserWithEvents> {
        return userDao.getPublicUsersWithTheirEvents()
    }
}