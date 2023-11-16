package com.alth.events.ui.viewmodels.search

import androidx.lifecycle.viewModelScope
import com.alth.events.domain.SearchEventsUseCase
import com.alth.events.models.network.events.ingress.MinimalEventListResponseDto
import com.alth.events.ui.viewmodels.PagingViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchEventsUIState(
    val events: MinimalEventListResponseDto,
) {
    companion object {
        fun initial() = SearchEventsUIState(
            MinimalEventListResponseDto.empty(),
        )
    }
}

@HiltViewModel
class SearchEventsViewModel @Inject constructor(
    private val searchEventsUseCase: SearchEventsUseCase,
) : PagingViewModel<MinimalEventListResponseDto>(
    pageSize = 30,
    numBeforeNextFetch = 5,
) {
    private var lastSearchString = "" // This is bad

    private val _uiState = MutableStateFlow(SearchEventsUIState.initial())
    val uiState = _uiState.asStateFlow()

    init {
        consumeItem(0)
    }

    override suspend fun getNextPageImpl(page: Int, pageSize: Int): MinimalEventListResponseDto {
        return searchEventsUseCase(page, pageSize, lastSearchString)
    }

    override suspend fun setNextPageImpl(page: MinimalEventListResponseDto) {
        _uiState.value = uiState.value.copy(
            events = uiState.value.events.append(page)
        )
    }

    fun onQueryStringChange(newString: String) {
        lastSearchString = newString
    }

    fun onSearchQuerySubmitted(newQuery: String) {
        lastSearchString = newQuery
        viewModelScope.launch {
            stopAndResetPaging {
                _uiState.value = _uiState.value.copy(events = MinimalEventListResponseDto.empty())
            }
        }
    }
}