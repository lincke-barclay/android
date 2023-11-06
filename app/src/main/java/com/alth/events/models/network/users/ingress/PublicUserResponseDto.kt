package com.alth.events.models.network.users.ingress

import kotlinx.serialization.Serializable

@Serializable
data class PublicUserResponseDto(
        val id: String,
        val name: String,
)

