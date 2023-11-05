package com.alth.events.models.network.users.ingress

import com.alth.events.models.domain.users.PublicUser
import kotlinx.serialization.Serializable

@Serializable
data class GETPublicUserResponseDTO(
    val id: String,
    val firstName: String,
    val lastName: String,
) {
    fun toPublicUser() = PublicUser(
        id = id,
        firstName = firstName,
        lastName = lastName,
    )
}
