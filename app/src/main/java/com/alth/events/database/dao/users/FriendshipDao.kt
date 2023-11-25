package com.alth.events.database.dao.users

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alth.events.database.models.friends.FriendshipEntity
import com.alth.events.database.models.users.PublicUserEntity

@Dao
interface FriendshipDao {
    @Query("select * from public_user u where u.id in (select f.friendId from friendship f where f.type = 'Accepted')")
    fun getAccepted(): PagingSource<Int, PublicUserEntity>

    @Query("select count(*) from public_user u where u.id in (select f.friendId from friendship f where f.type = 'Accepted')")
    suspend fun getNumberAccepted(): Int

    @Query("delete from friendship where type = 'Accepted'")
    suspend fun clearAccepted()

    @Query("select * from public_user u where u.id in (select f.friendId from friendship f where f.type = 'PendingSentFromMe')")
    fun getPendingSentFromMe(): PagingSource<Int, PublicUserEntity>

    @Query("select count(*) from public_user u where u.id in (select f.friendId from friendship f where f.type = 'PendingSentFromMe')")
    suspend fun getNumberPendingSentFromMe(): Int

    @Query("delete from friendship where type = 'PendingSentFromMe'")
    suspend fun clearPendingSentFromMe()

    @Query("select * from public_user u where u.id in (select f.friendId from friendship f where f.type = 'PendingSentToMe')")
    fun getPendingSentToMe(): PagingSource<Int, PublicUserEntity>

    @Query("select count(*) from public_user u where u.id in (select f.friendId from friendship f where f.type = 'PendingSentToMe')")
    suspend fun getNumberPendingSentToMe(): Int

    @Query("delete from friendship where type = 'PendingSentToMe'")
    suspend fun clearPendingSentToMe()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(friendships: List<FriendshipEntity>)
}

