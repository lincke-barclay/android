package com.alth.events.database.dao.events

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alth.events.database.models.events.QueriedEventEntity
import com.alth.events.database.models.events.derived.SearchEventResult

@Dao
interface SearchEventDao {
    @Query(
        "select e.id, u.name, e.startDateTime, e.endDateTime, e.title, e.shortDescription, e.longDescription" +
                " from event e, public_user u " +
                "where e.id in " +
                "(select q.eventId from query_results q where q.serializedQuery = :query) " +
                "and e.ownerId = u.id"
    )
    fun getSearchPagerForQuery(query: String): PagingSource<Int, SearchEventResult>

    @Query("delete from query_results where serializedQuery = :query")
    suspend fun clearEventsBySearchQuery(query: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(elements: List<QueriedEventEntity>)
}