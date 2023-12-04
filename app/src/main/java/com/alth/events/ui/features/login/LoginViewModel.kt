package com.alth.events.ui.features.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alth.events.data.repositories.AuthenticationRepository
import com.alth.events.models.authentication.results.SignInResult
import com.alth.events.util.isEmailValid
import com.alth.events.util.isPasswordValid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
) : ViewModel() {

    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    var loading by mutableStateOf(false)
        private set

    private var signInJob: Job = Job().also { it.complete() }

    private val emailIsValid get() = isEmailValid(email)
    private val passwordIsValid get() = isPasswordValid(password)
    val submitAvailable get() = emailIsValid && passwordIsValid

    fun signIn() {
        if (!signInJob.isActive) {
            signInJob = viewModelScope.launch {
                loading = true
                when (authenticationRepository.signIn(email, password)) {
                    SignInResult.PasswordIsIncorrect -> {
                        errorMessage = "Invalid Login"
                    }

                    SignInResult.Success -> {
                        // do nothing - state will change soon
                    }

                    SignInResult.ThatUserDoesntExist -> {
                        errorMessage = "Invalid Login"
                    }
                }
                loading = false
            }
        }
    }

    fun consumeErrorMessage(message: String) {
        if (message == errorMessage) {
            errorMessage = null
        } else {
            throw Exception(
                "UI consumed an error message that didn't match the " +
                        "current message. This is probably a bug"
            )
        }
    }

    fun onEmailChange(value: String) {
        email = value
    }

    fun onPasswordChange(value: String) {
        password = value
    }
}