package com.alth.events.models.friends

data class FriendsQuery(
    val page: Int,
    val pageSize: Int,
    val queryString: String? = null,
)
