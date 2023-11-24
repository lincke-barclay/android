package com.alth.events.database.models.events.derived

import androidx.room.ColumnInfo
import kotlinx.datetime.Instant

data class FeedEvent(
    val id: String,
    @ColumnInfo(name = "name") val ownerName: String,
    @ColumnInfo(name = "profilePictureUrl") val ownerProfilePictureUrl: String,
    val startDateTime: Instant,
    val endDateTime: Instant,
    val title: String,
    val shortDescription: String,
    val longDescription: String,
)
