package com.alth.events.ui.viewmodels.landing

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alth.events.data.repositories.AuthenticationRepository
import com.alth.events.logging.impl.loggerFactory
import com.alth.events.models.domain.authentication.results.SendVerificationEmailResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailVerificationViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
) : ViewModel() {
    private val logger = loggerFactory.getLogger(this)

    /**
     * Jobs
     */
    private var sendVerificationJob: Job = Job().also { it.complete() }
    private var counterJob: Job = Job().also { it.complete() }

    /**
     * State
     */
    private val totalSeconds = 20
    var secondsTillTryAgain by mutableIntStateOf(totalSeconds)
        private set
    var loading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        // We should have sent email once
        // Don't make the mistake of calling try again here
        counterJob = createCounterJob()
    }

    fun tryAgain() {
        if (!sendVerificationJob.isActive && !counterJob.isActive) {
            sendVerificationJob = viewModelScope.launch {
                loading = true
                when (authenticationRepository.sendVerificationEmailForCurrentlySignedInUser()) {
                    SendVerificationEmailResult.Success -> {
                        // Do nothing
                    }
                }
                loading = false
                secondsTillTryAgain = totalSeconds
                counterJob = createCounterJob()
            }
        }
    }

    private fun createCounterJob() = viewModelScope.launch {
        while (secondsTillTryAgain > 0) {
            logger.debug("Can try again in $secondsTillTryAgain seconds")
            delay(1000)
            secondsTillTryAgain--
        }
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