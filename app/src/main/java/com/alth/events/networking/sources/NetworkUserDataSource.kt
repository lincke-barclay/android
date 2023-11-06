package com.alth.events.networking.sources

import com.alth.events.models.network.NetworkResult
import com.alth.events.models.network.users.ingress.PrivateUserResponseDto

interface NetworkUserDataSource {
    suspend fun getMe(): NetworkResult<PrivateUserResponseDto>
}