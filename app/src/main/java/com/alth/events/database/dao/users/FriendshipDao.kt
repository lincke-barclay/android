package com.alth.events.database.dao.users

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alth.events.database.models.users.FriendshipEntity
import com.alth.events.database.models.users.PublicUserEntity

/**
 * One shots - TODO - paging
 */
@Dao
interface FriendshipDao {
    @Query("select * from public_user u where u.id in (select f.friendId from friendship f where f.type = 'Accepted')")
    suspend fun getAccepted(): List<PublicUserEntity>

    @Query("select * from public_user u where u.id in (select f.friendId from friendship f where f.type = 'PendingSentFromMe')")
    suspend fun getPendingSentFromMe(): List<PublicUserEntity>

    @Query("select * from public_user u where u.id in (select f.friendId from friendship f where f.type = 'PendingSentToMe')")
    suspend fun getPendingSentToMe(): List<PublicUserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(friendships: List<FriendshipEntity>)
}