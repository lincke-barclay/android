package com.alth.events.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alth.events.models.domain.authentication.AuthenticationState
import com.alth.events.repositories.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
) : ViewModel() {

    val uiState = authenticationRepository
        .authenticationState
        .stateIn(
            viewModelScope,
            initialValue = AuthenticationState.Unknown,
            started = SharingStarted.WhileSubscribed(),
        )

    fun signIn(email: String, password: String) {
        authenticationRepository.signIn(email, password)
    }

    fun signOut() {
        authenticationRepository.signOut()
    }

    fun signUp(email: String, password: String) {
        authenticationRepository.signUp(email, password)
    }

    fun onClickSendVerificationEmail() {
        authenticationRepository.sendVerificationEmailForCurrentlySignedInUser()
    }

    fun reload() {
        authenticationRepository.reload()
    }

    fun changeName(newName: String) {
        authenticationRepository.changeNameOfCurrentlySignedInUser(newName)
    }
}
