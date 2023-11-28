package com.alth.events.database.models.users

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "public_user")
data class PublicUserEntity(
    @PrimaryKey val id: String,
    val name: String,
    val profilePictureUrl: String?,
)