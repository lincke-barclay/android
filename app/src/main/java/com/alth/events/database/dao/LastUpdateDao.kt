package com.alth.events.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alth.events.database.models.LastUpdate

@Dao
interface LastUpdateDao {
    @Query("select * from last_update where update_type == 'FeedRefresh'")
    suspend fun getLastUpdatedFeedRefresh(): List<LastUpdate>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(lastUpdate: LastUpdate)
}