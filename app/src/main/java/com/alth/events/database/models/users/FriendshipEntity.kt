package com.alth.events.database.models.users

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

enum class FriendType {
    Accepted,
    PendingSentFromMe,
    PendingSentToMe,
}

@Entity(
    tableName = "friendship",
    foreignKeys = [ForeignKey(
        entity = PublicUserEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("friendId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class FriendshipEntity(
    @PrimaryKey val friendId: String,
    val type: FriendType,
)
