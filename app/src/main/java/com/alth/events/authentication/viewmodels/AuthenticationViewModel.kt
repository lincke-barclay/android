package com.alth.events.authentication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alth.events.data.authentication.FirebaseAuthenticationRepository
import com.alth.events.data.authentication.models.AuthenticationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val firebaseAuthenticationRepository: FirebaseAuthenticationRepository,
) : ViewModel() {

    val uiState = firebaseAuthenticationRepository
        .authenticationState
        .stateIn(
            viewModelScope,
            initialValue = AuthenticationState.Unknown,
            started = SharingStarted.WhileSubscribed(),
        )

    fun signIn(email: String, password: String) {
        firebaseAuthenticationRepository.signIn(email, password)
    }

    fun signOut() {
        firebaseAuthenticationRepository.signOut()
    }

    fun signUp(email: String, password: String) {
        firebaseAuthenticationRepository.signUp(email, password)
    }

    fun onClickSendVerificationEmail() {
        firebaseAuthenticationRepository.sendVerificationEmailForCurrentlySignedInUser()
    }

    fun reload() {
        firebaseAuthenticationRepository.reload()
    }

    fun changeName(newName: String) {
        firebaseAuthenticationRepository.changeNameOfCurrentlySignedInUser(newName)
    }
}
