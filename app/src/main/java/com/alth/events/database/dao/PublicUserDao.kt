package com.alth.events.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.alth.events.database.models.PublicUser
import com.alth.events.database.models.derived.PublicUserWithEvents

@Dao
interface PublicUserDao {
    @Query("select * from public_user")
    fun pagingSource(): PagingSource<Int, PublicUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<PublicUser>)

    @Query("delete from public_user")
    suspend fun clearAll()

    @Transaction
    @Query("select * from public_user")
    suspend fun getPublicUsersWithTheirEvents(): List<PublicUserWithEvents>
}
