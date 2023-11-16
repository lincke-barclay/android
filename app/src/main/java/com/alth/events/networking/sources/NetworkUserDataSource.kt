package com.alth.events.networking.sources

import com.alth.events.models.network.NetworkResult
import com.alth.events.models.network.users.ingress.PrivateUserResponseDto
import com.alth.events.models.network.users.ingress.PublicUserResponseDto

interface NetworkUserDataSource {
    suspend fun getMe(): NetworkResult<PrivateUserResponseDto>
    suspend fun getNotMe(id: String): NetworkResult<PublicUserResponseDto>
}