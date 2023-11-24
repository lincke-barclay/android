package com.alth.events.database.dao.events

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.alth.events.database.models.derived.FeedEvent

@Dao
interface FeedDao {
    @Query(
        "select " +
                "public_user.name as ownerName, " +
                "public_user.profile_picture_url as ownerProfilePictureUrl," +
                "event.start_date_time as startDateTime," +
                "event.id as id," +
                "event.end_date_time as endDateTime," +
                "event.title as title," +
                "event.short_description as shortDescription," +
                "event.long_description as longDescription " +
                "from event, public_user " +
                "where event.is_feed = 1 " +
                "and event.owner_id = public_user.id"
    )
    fun getFeedPagingSource(): PagingSource<Int, FeedEvent>

    @Query("delete from event where is_feed = 1")
    suspend fun clearFeed()
}