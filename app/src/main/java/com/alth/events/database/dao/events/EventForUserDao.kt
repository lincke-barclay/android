package com.alth.events.database.dao.events

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import com.alth.events.database.models.events.derived.AnonymousEvent

@Dao
interface EventForUserDao {
    @RewriteQueriesToDropUnusedColumns
    @Query("select * from event where event.ownerId = :userId")
    fun getEventsForUser(userId: String): PagingSource<Int, AnonymousEvent>

    @Query("delete from event where ownerId = :id")
    suspend fun clearEventsByUser(id: String)
}