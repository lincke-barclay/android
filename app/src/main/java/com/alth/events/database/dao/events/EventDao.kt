package com.alth.events.database.dao.events

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.alth.events.database.models.Event

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(event: List<Event>)
}