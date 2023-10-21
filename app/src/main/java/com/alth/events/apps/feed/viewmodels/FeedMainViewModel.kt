package com.alth.events.apps.feed.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alth.events.data.feed.FeedElementRepository
import com.alth.events.data.feed.models.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FeedUiState(
    val events: List<Event>,
    val loading: Boolean
)

@HiltViewModel
class FeedMainViewModel @Inject constructor(
    private val feedElementRepository: FeedElementRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(FeedUiState(emptyList(), false))
    val uiState = _uiState.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loading = true)
            _uiState.value =
                _uiState.value.copy(events = feedElementRepository.getFeedElements())
            _uiState.value = _uiState.value.copy(loading = false)
        }
    }
}

