package com.alth.events.ui.features.changedisplayname

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alth.events.data.repositories.AuthenticationRepository
import com.alth.events.models.domain.authentication.results.ChangeNameResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeDisplayNameViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
) : ViewModel() {
    var name by mutableStateOf("")
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var loading by mutableStateOf(false)
    private var changeNameJob: Job = Job().also { it.complete() }

    fun changeName() {
        if (!changeNameJob.isActive) {
            changeNameJob = viewModelScope.launch {
                loading = true
                when (authenticationRepository.changeNameOfCurrentlySignedInUser(name)) {
                    ChangeNameResult.InvalidUserException -> {
                        errorMessage = "Error: invalid user"
                    }

                    ChangeNameResult.Success -> {
                        // Do nothing
                    }
                }
            }
        }
    }

    fun onChangeName(newName: String) {
        name = newName
    }

    fun consumeErrorMessage(message: String) {
        if (message == errorMessage) {
            errorMessage = null
        } else {
            throw Exception(
                "UI Got a different error message than view model"
            )
        }
    }
}