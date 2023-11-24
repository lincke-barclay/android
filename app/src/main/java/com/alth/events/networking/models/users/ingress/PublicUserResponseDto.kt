package com.alth.events.networking.models.users.ingress

import kotlinx.serialization.Serializable

@Serializable
data class PublicUserResponseDto(
    val id: String,
    val name: String,
    val profilePictureUrl: String,
) {
    companion object {
        fun empty() = PublicUserResponseDto("", "", "")

    }
}

