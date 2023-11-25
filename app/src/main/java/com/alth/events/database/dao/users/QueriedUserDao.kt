package com.alth.events.database.dao.users

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alth.events.database.models.friends.QueriedUserEntity
import com.alth.events.database.models.users.PublicUserEntity

@Dao
interface QueriedUserDao {
    @Query(
        "select * " +
                "from public_user u " +
                "where u.id in " +
                "(select q.userId " +
                "from query_user_result q " +
                "where q.serializedQuery = :query) "
    )
    fun getSearchPagerForQuery(query: String): PagingSource<Int, PublicUserEntity>

    @Query("delete from query_user_result where serializedQuery = :query")
    suspend fun clearUsersByQuery(query: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(elements: List<QueriedUserEntity>)

    @Query("select count(*) from query_user_result where serializedQuery = :query")
    suspend fun countAllByQuery(query: String): Int
}