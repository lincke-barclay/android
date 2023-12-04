package com.alth.events.ui.features.common.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alth.events.data.caching.CachingMeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfilePhotoViewModel @Inject constructor(
    private val meRepository: CachingMeRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<String?>(null)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = meRepository.getMe()(true).t.profilePictureUrl
        }
    }
}