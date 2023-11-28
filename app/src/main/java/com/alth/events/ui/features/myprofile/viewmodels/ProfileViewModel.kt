package com.alth.events.ui.features.myprofile.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alth.events.data.caching.CachingMeRepository
import com.alth.events.data.repositories.AuthenticationRepository
import com.alth.events.networking.models.users.ingress.PrivateUserResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val meRepository: CachingMeRepository,
    private val authenticationRepository: AuthenticationRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<PrivateUserResponseDto?>(null)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = meRepository.getMe()(true).t
        }
    }

    fun signOut() {
        authenticationRepository.signOut()
    }
}