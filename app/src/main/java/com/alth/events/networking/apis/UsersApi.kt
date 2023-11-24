package com.alth.events.networking.apis

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface UsersApi {
    @GET("/users/{userId}")
    suspend fun getUser(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
    ): String
}