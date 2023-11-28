package com.alth.events.models.domain.friends

data class FriendsQuery(
    val page: Int,
    val pageSize: Int,
    val queryString: String? = null,
)
