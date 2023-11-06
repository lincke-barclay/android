package com.alth.events.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alth.events.exceptions.ApiException
import com.alth.events.models.network.events.ingress.PublicEventResponseDto
import com.alth.events.models.network.users.ingress.PublicUserResponseDto
import com.alth.events.repositories.CachingEventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
    private val eventRepository: CachingEventRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(FeedUiState(emptyList(), false))
    val uiState = _uiState.asStateFlow()

    init {
        refresh()
    }

    private fun refresh() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loading = true)
            val feedCacheResult = eventRepository.getFeed(0, 10)(true).t

            val feed = feedCacheResult.publicEvents.map { event ->
                feedCacheResult.publicUsers[event.ownerId]?.let { owner ->
                    FeedCardUIState(feedEvent = event, owner = owner)
                } ?: throw ApiException("Expected all users to be full")
            }

            _uiState.value = _uiState.value.copy(events = feed, loading = false)
        }
    }
}

