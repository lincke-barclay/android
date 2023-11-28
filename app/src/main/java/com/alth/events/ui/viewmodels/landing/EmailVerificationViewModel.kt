package com.alth.events.ui.viewmodels.landing

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alth.events.data.repositories.AuthenticationRepository
import com.alth.events.models.domain.authentication.results.SendVerificationEmailResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailVerificationViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
) : ViewModel() {

    private var sendVerificationJob: Job = Job().also { it.complete() }
    private var tryAgainJob: Job = Job().also { it.complete() }

    var loading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    // TODO - prevent more than 24 seconds or something like that
    fun onClickSendVerificationEmail() {
        if (!sendVerificationJob.isActive) {
            sendVerificationJob = viewModelScope.launch {
                loading = true
                when (authenticationRepository.sendVerificationEmailForCurrentlySignedInUser()) {
                    SendVerificationEmailResult.Success -> {
                        // Do nothing
                    }
                }
                loading = false
            }
        }
    }

    fun reload() {
        authenticationRepository.signOut() // TODO - make this cleaner
    }

    fun signOut() {
        authenticationRepository.signOut()
    }

    fun consumeErrorMessage(message: String) {
        if (message == errorMessage) {
            errorMessage = null
        } else {
            throw Exception(
                "UI encountered an error message that is not what was set in the view model"
            )
        }
    }
}