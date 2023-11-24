package com.alth.events.transforms.networkToDatabase

import com.alth.events.database.models.PublicUser
import com.alth.events.networking.models.users.ingress.PublicUserResponseDto

fun PublicUserResponseDto.toDatabaseUser(): PublicUser {
    return PublicUser(
        id = id,
        name = name,
        profilePictureUrl = profilePictureUrl,
    )
}