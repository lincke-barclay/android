package com.alth.events.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alth.events.models.domain.search.SearchResult
import com.alth.events.models.domain.users.PublicUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchUIState(
    val searchResults: List<SearchResult>,
    val loading: Boolean,
) {
    companion object {
        fun initial() = SearchUIState(emptyList(), false)
    }
}

@HiltViewModel
class SearchViewModel @Inject constructor(
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUIState.initial())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            setLoading()
            _uiState.value = _uiState.value.copy(searchResults = emptyList())
            unsetLoading()
        }
    }


    fun onNewQueryTyped(query: String) {
        if (!_uiState.value.loading) {
            viewModelScope.launch {
                setLoading()
                _uiState.value = _uiState.value.copy(searchResults = emptyList())
                unsetLoading()
            }
        }
    }

    fun onNewQueryEnterButtonPressed(query: String) {
        if (!_uiState.value.loading) {
            viewModelScope.launch {
                TODO()
            }
        }
    }

    fun sendFriendRequest(user: PublicUser) {
        if (!_uiState.value.loading) {
            viewModelScope.launch {
                TODO()
            }
        }
    }

    private fun setLoading() {
        if (!_uiState.value.loading) {
            _uiState.value = _uiState.value.copy(loading = true)
        }
    }

    private fun unsetLoading() {
        if (_uiState.value.loading) {
            _uiState.value = _uiState.value.copy(loading = false)
        }
    }
}