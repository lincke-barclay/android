package com.alth.events.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

@Entity(tableName = "event")
data class Event(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "owner_id")
    val ownerId: String,
    @ColumnInfo(name = "start_date_time")
    val startDateTime: Instant,
    @ColumnInfo(name = "end_date_time")
    val endDateTime: Instant,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "short_description")
    val shortDescription: String,
    @ColumnInfo(name = "long_description")
    val longDescription: String,

    // Api Specific stuff to preserve
    @ColumnInfo(name = "from_search_query")
    val fromSearchQuery: String? = null,
    @ColumnInfo(name = "is_feed")
    val isFeed: Boolean = false,
)