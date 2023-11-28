package com.alth.events.networking.apis

import com.alth.events.networking.models.users.ingress.PublicUserResponseDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FriendsApi {
    @GET("/users/{userId}/friends")
    suspend fun getConfirmedFriends(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
    ): List<PublicUserResponseDto>

    @GET("/users/{userId}/friends/pending/from-me")
    suspend fun getFriendsIRequested(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
    ): List<PublicUserResponseDto>

    @GET("/users/{userId}/friends/pending/to-me")
    suspend fun getFriendsRequestedToMe(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
    ): List<PublicUserResponseDto>

    @POST("/users/{requesterId}/friends/{recipientId}")
    suspend fun postFriendship(
        @Header("Authorization") token: String,
        @Path("requesterId") requesterId: String,
        @Path("recipientId") recipientId: String,
    )

    @DELETE("/users/{requesterId}/friends/{toDeleteId}")
    suspend fun deleteFriendship(
        @Header("Authorization") token: String,
        @Path("requesterId") requesterId: String,
        @Path("toDeleteId") toDeleteId: String,
    )
}