package com.alth.events.networking.sources

import com.alth.events.models.network.NetworkResult
import com.alth.events.models.network.users.egress.POSTUserRequestDto
import com.alth.events.models.network.users.ingress.GETPrivateUserResponseDTO
import com.alth.events.models.network.users.ingress.GETPublicUserResponseDTO

interface NetworkUserDataSource {
    suspend fun createUser(
        request: POSTUserRequestDto,
    ): NetworkResult<GETPublicUserResponseDTO>

    suspend fun getMe(): NetworkResult<GETPrivateUserResponseDTO>
}