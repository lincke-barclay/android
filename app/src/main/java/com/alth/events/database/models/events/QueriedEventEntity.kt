package com.alth.events.database.models.events

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "query_event_result",
    foreignKeys = [ForeignKey(
        entity = EventEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("eventId"),
        onDelete = ForeignKey.CASCADE
    )],
    primaryKeys = ["eventId", "serializedQuery"]
)
data class QueriedEventEntity(
    val eventId: String,
    val serializedQuery: String,
)
