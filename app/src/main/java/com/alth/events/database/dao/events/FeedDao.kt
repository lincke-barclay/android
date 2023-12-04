package com.alth.events.database.dao.events

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alth.events.database.models.events.FeedEventEntity
import com.alth.events.database.models.events.derived.FeedEvent
import kotlinx.datetime.Instant

@Dao
interface FeedDao {
    @Query(
        "select e.id, u.name, u.profilePictureUrl, e.startDateTime, e.endDateTime, e.title, e.shortDescription, e.longDescription " +
                "from event e, public_user u " +
                "where e.id in (select f.eventId from feed_event f) " +
                "and e.ownerId = u.id"
    )
    fun getFeedPagingSource(): PagingSource<Int, FeedEvent>

    @Query("delete from feed_event")
    suspend fun clearFeed()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(elements: List<FeedEventEntity>)
}