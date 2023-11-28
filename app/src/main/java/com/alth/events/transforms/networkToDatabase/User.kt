package com.alth.events.transforms.networkToDatabase

import android.webkit.URLUtil
import com.alth.events.database.models.users.QueriedUserEntity
import com.alth.events.database.models.users.PublicUserEntity
import com.alth.events.networking.models.users.ingress.PublicUserResponseDto

fun PublicUserResponseDto.toDatabaseUser(): PublicUserEntity {
    return PublicUserEntity(
        id = id,
        name = name,
        profilePictureUrl = if (URLUtil.isValidUrl(profilePictureUrl)) profilePictureUrl else null,
    )
}

fun PublicUserResponseDto.toQueriedUser(query: String) = QueriedUserEntity(
    userId = id,
    serializedQuery = query,
)