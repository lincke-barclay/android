package com.alth.events.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

enum class UpdateType {
    FeedRefresh,
    MyEventsRefresh,
}

@Entity(tableName = "last_update")
data class LastUpdateEntity(
    @PrimaryKey @ColumnInfo("update_type") val updateType: UpdateType,
    @ColumnInfo("last_updated") val lastUpdate: Instant,
)
