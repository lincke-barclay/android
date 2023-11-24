package com.alth.events.database.dao.events

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.alth.events.database.models.derived.SearchEventResult

@Dao
interface SearchEventDao {
    @Query(
        "select " +
                "public_user.name as ownerName, " +
                "event.start_date_time as startDateTime," +
                "event.id as id," +
                "event.end_date_time as endDateTime," +
                "event.title as title," +
                "event.short_description as shortDescription," +
                "event.long_description as longDescription " +
                "from event, public_user " +
                "where event.from_search_query = :query " +
                "and event.owner_id = public_user.id"
    )
    fun getSearchPagerForQuery(query: String): PagingSource<Int, SearchEventResult>

    @Query("delete from event where from_search_query = :query")
    suspend fun clearEventsBySearchQuery(query: String)
}