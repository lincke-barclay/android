package com.alth.events.models.network.users.ingress

import java.time.Instant

data class GETPrivateUserResponseDTO(
    val id: String,
    val createdTs: Instant,
    val lastUpdatedTs: Instant,
    val email: String,
    val firstName: String,
    val lastName: String,
)
