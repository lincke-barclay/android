package com.alth.events.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alth.events.data.repositories.AuthenticationRepository
import com.alth.events.models.domain.authentication.AuthenticationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
open class AuthenticationViewModel @Inject constructor(
    authenticationRepository: AuthenticationRepository,
) : ViewModel() {

    val currentlySignedInState = authenticationRepository
        .authenticationState
        .stateIn(
            viewModelScope,
            initialValue = AuthenticationState.Unknown,
            started = SharingStarted.WhileSubscribed(),
        )
}
