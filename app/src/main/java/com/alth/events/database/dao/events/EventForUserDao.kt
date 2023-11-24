package com.alth.events.database.dao.events

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.alth.events.database.models.derived.MyEvent

@Dao
interface EventForUserDao {
    @Query(
        "select " +
                "event.id as id," +
                "event.start_date_time as startDateTime," +
                "event.end_date_time as endDateTime," +
                "event.title as title," +
                "event.short_description as shortDescription," +
                "event.long_description as longDescription " +
                "from event " +
                "where event.owner_id = :userId "
    )
    fun getPrivateEventsForUser(userId: String): PagingSource<Int, MyEvent>

    @Query("delete from event where owner_id = :id")
    suspend fun clearEventsByUser(id: String)
}