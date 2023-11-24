package com.alth.events.ui.features.feed.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.alth.events.logging.impl.loggerFactory
import com.alth.events.networking.models.events.ingress.PublicEventResponseDto
import com.alth.events.networking.models.users.ingress.PublicUserResponseDto
import com.alth.events.data.repositories.paging.PagingFeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

data class FeedUiState(
    val events: List<FeedCardUIState>,
    val loading: Boolean
)

data class FeedCardUIState(
    val feedEvent: PublicEventResponseDto,
    val owner: PublicUserResponseDto,
)

@HiltViewModel
class FeedMainViewModel @Inject constructor(
    pagingFeedRepository: PagingFeedRepository,
) : ViewModel() {
    private val logger = loggerFactory.getLogger(this)

    val eventPagingFlow = pagingFeedRepository
        .feedEventFlow
        .cachedIn(viewModelScope)
}
