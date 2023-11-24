package com.alth.events.database.models.events

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "feed_event",
    foreignKeys = [ForeignKey(
        entity = EventEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("eventId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class FeedEventEntity(
    @PrimaryKey val eventId: String,
)
