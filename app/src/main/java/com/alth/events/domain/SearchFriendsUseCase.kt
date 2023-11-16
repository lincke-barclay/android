package com.alth.events.domain

import com.alth.events.models.network.users.ingress.PublicUserResponseDto
import com.alth.events.repositories.CachingFriendshipRepository
import javax.inject.Inject

class SearchFriendsUseCase @Inject constructor(
    private val friendshipRepository: CachingFriendshipRepository,
) {
    suspend operator fun invoke(
        page: Int,
        pageSize: Int,
        queryStr: String,
    ): List<PublicUserResponseDto> {
        return friendshipRepository.getSuggestedFriends(page, pageSize, queryStr)(true).t
    }
}