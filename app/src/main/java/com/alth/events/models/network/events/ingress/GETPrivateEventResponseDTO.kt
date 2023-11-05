package com.alth.events.models.network.events.ingress

import com.alth.events.models.network.users.ingress.GETPrivateUserResponseDTO
import kotlinx.datetime.LocalTime
import java.time.Instant

data class GETPrivateEventResponseDTO(
    val id: String,
    val createdTs: Instant,
    val lastUpdatedTs: Instant,
    val owner: GETPrivateUserResponseDTO,
    val startDateTime: LocalTime,
    val endDateTime: kotlinx.datetime.Instant,
    val title: String,
    val shortDescription: String,
    val longDescription: String,
)
