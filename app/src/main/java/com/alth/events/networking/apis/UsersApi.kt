package com.alth.events.networking.apis

import com.alth.events.models.network.users.ingress.PrivateUserResponseDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface UsersApi {
    @GET("/users/{userId}")
    suspend fun getMe(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
    ): PrivateUserResponseDto
}