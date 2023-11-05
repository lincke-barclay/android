package com.alth.events.networking.apis

import com.alth.events.models.network.users.egress.POSTUserRequestDto
import com.alth.events.models.network.users.ingress.GETPrivateUserResponseDTO
import com.alth.events.models.network.users.ingress.GETPublicUserResponseDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface UsersApi {
    @POST("/users")
    suspend fun createUser(
        @Header("Authorization") token: String,
        @Body request: POSTUserRequestDto,
    ): GETPublicUserResponseDTO

    @GET("/users/{userId}")
    suspend fun getMe(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
    ): GETPrivateUserResponseDTO
}