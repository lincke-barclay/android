package com.alth.events.networking.apis

import com.alth.events.networking.models.users.ingress.PublicUserResponseDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface UsersApi {
    @GET("/users/{userId}")
    suspend fun getUser(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
    ): String

    @GET("/users")
    suspend fun getUsersByQuery(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("queryStr") queryStr: String,
    ): List<PublicUserResponseDto>
}