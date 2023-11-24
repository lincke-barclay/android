package com.alth.events.ui.viewmodels.landing

import androidx.lifecycle.ViewModel
import com.alth.events.data.repositories.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EmailVerificationViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
) : ViewModel() {
    fun onClickSendVerificationEmail() {
        authenticationRepository.sendVerificationEmailForCurrentlySignedInUser()
    }

    fun reload() {
        authenticationRepository.reload()
    }

    fun signOut() {
        authenticationRepository.signOut()
    }
}