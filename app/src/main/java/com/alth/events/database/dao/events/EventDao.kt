package com.alth.events.database.dao.events

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.alth.events.database.models.events.EventEntity

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(eventEntity: List<EventEntity>)
}