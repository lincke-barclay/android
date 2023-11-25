package com.alth.events.database.models.friends

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.alth.events.database.models.users.PublicUserEntity

@Entity(
    tableName = "query_user_result",
    foreignKeys = [ForeignKey(
        entity = PublicUserEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("userId"),
        onDelete = ForeignKey.CASCADE,
    )]
)
data class QueriedUserEntity(
    @PrimaryKey val userId: String,
    val serializedQuery: String,
)