package com.alth.events.database.dao.users

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.alth.events.database.models.users.PublicUserEntity
import com.alth.events.database.models.events.derived.PublicUserWithEvents

@Dao
interface PublicUserDao {
    @Query("select * from public_user")
    fun pagingSource(): PagingSource<Int, PublicUserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<PublicUserEntity>)

    @Query("delete from public_user")
    suspend fun clearAll()

    @Transaction
    @Query("select * from public_user")
    suspend fun getPublicUsersWithTheirEvents(): List<PublicUserWithEvents>

    // TODO - remove this and implement a query
    @Query("select * from public_user")
    suspend fun getUsers(): List<PublicUserEntity>
}
