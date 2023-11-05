package com.alth.events.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alth.events.models.domain.events.FeedEvent
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
    val feedEvent: FeedEvent,
) {
    companion object {
        fun initFromEvent(feedEvent: FeedEvent) = FeedCardUIState(feedEvent)
    }
}

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
            val feedCacheResult = eventRepository.getFeed(0, 10)(true)

            // TODO handle errors
            val feed = feedCacheResult.t
                .map(FeedCardUIState.Companion::initFromEvent)

            _uiState.value = _uiState.value.copy(events = feed, loading = false)
        }
    }
}

