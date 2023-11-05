package com.alth.events.models.network.users.egress

import kotlinx.serialization.Serializable

@Serializable
data class POSTUserRequestDto(
    val email: String,
    val firstName: String,
    val lastName: String,
)
