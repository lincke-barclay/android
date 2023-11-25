package com.alth.events.database.models.friends

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.alth.events.database.models.users.PublicUserEntity
import com.alth.events.networking.models.users.ingress.PublicUserResponseDto

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
) {
    companion object {
        fun from(user: PublicUserResponseDto, type: FriendType) =
            FriendshipEntity(
                friendId = user.id,
                type = type,
            )
    }
}
