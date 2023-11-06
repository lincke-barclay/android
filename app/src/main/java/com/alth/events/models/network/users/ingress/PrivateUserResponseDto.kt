package com.alth.events.models.network.users.ingress

import kotlinx.serialization.Serializable

@Serializable
data class PrivateUserResponseDto(
    val id: String,
    val name: String,
    val email: String,
)
