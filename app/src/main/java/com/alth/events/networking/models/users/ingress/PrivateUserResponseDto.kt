package com.alth.events.networking.models.users.ingress

import kotlinx.serialization.Serializable

@Serializable
data class PrivateUserResponseDto(
    val id: String,
    val name: String,
    val email: String,
    val profilePictureUrl: String,
) {
    companion object {
        fun empty() = PrivateUserResponseDto("", "", "", "")
    }
}
