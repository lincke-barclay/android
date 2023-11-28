package com.alth.events.database.models.users

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "query_user_result",
    foreignKeys = [ForeignKey(
        entity = PublicUserEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("userId"),
        onDelete = ForeignKey.CASCADE,
    )],
    primaryKeys = ["userId", "serializedQuery"]
)
data class QueriedUserEntity(
    val userId: String,
    val serializedQuery: String,
)