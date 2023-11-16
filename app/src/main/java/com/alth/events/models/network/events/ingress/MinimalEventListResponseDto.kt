package com.alth.events.models.network.events.ingress

import com.alth.events.models.network.users.ingress.PublicUserResponseDto
import kotlinx.serialization.Serializable

@Serializable
data class MinimalEventListResponseDto(
    val publicEvents: List<PublicEventResponseDto>,
    val publicUsers: HashMap<String, PublicUserResponseDto>
) {
    companion object {
        fun empty() = MinimalEventListResponseDto(listOf(), HashMap())
    }

    fun append(resp: MinimalEventListResponseDto): MinimalEventListResponseDto {
        val newPublicUsers = HashMap<String, PublicUserResponseDto>()
        newPublicUsers.putAll(publicUsers)
        newPublicUsers.putAll(resp.publicUsers)

        return MinimalEventListResponseDto(
            publicEvents = publicEvents + resp.publicEvents,
            publicUsers = newPublicUsers
        )
    }
}
