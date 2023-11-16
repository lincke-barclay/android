package com.alth.events.domain

import com.alth.events.models.network.events.ingress.MinimalEventListResponseDto
import com.alth.events.repositories.CachingEventRepository
import javax.inject.Inject

class SearchEventsUseCase @Inject constructor(
    private val cachingEventRepository: CachingEventRepository,
) {
    suspend operator fun invoke(
        page: Int,
        pageSize: Int,
        queryStr: String,
    ): MinimalEventListResponseDto {
        return cachingEventRepository.getSuggestedEvents(
            page,
            pageSize,
            queryStr
        )(true).t
    }
}