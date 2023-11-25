package com.alth.events.database.sources.users

import com.alth.events.database.dao.users.PublicUserDao
import com.alth.events.database.models.events.derived.PublicUserWithEvents
import com.alth.events.database.models.users.PublicUserEntity
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

    suspend fun getPublicUsers(): List<PublicUserEntity> = userDao.getUsers()
}