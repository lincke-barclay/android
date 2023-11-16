package com.alth.events.ui.viewmodels.landing

import androidx.lifecycle.ViewModel
import com.alth.events.repositories.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
) : ViewModel() {
    fun signIn(email: String, password: String) {
        authenticationRepository.signIn(email, password)
    }

    fun signUp(email: String, password: String) {
        authenticationRepository.signUp(email, password)
    }
}