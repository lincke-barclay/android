package com.alth.events.transforms.networkToDatabase

import com.alth.events.database.models.users.PublicUserEntity
import com.alth.events.networking.models.users.ingress.PublicUserResponseDto

fun PublicUserResponseDto.toDatabaseUser(): PublicUserEntity {
    return PublicUserEntity(
        id = id,
        name = name,
        profilePictureUrl = profilePictureUrl,
    )
}