package com.alth.events.database.models.events

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

@Entity(tableName = "event")
data class EventEntity(
    @PrimaryKey val id: String,
    val ownerId: String,
    val startDateTime: Instant,
    val endDateTime: Instant,
    val title: String,
    val shortDescription: String,
    val longDescription: String,
)