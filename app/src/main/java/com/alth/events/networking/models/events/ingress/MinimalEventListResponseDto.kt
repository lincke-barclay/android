package com.alth.events.networking.models.events.ingress

import com.alth.events.networking.models.users.ingress.PublicUserResponseDto
import kotlinx.serialization.Serializable

@Serializable
data class MinimalEventListResponseDto(
    val publicEvents: List<PublicEventResponseDto>,
    val publicUsers: HashMap<String, PublicUserResponseDto>
) {
    companion object {
        fun empty() = MinimalEventListResponseDto(listOf(), HashMap())
    }
}
