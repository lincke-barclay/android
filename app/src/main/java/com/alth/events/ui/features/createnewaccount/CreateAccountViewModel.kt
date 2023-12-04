package com.alth.events.ui.features.createnewaccount

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alth.events.data.repositories.AuthenticationRepository
import com.alth.events.models.authentication.results.SignUpResult
import com.alth.events.util.isEmailValid
import com.alth.events.util.isPasswordValid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
) : ViewModel() {
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var confirmPassword by mutableStateOf("")
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set
    var loading by mutableStateOf(false)
        private set
    private var createAccountJob: Job = Job().also { it.complete() }

    private val emailIsValid get() = isEmailValid(email)
    private val passwordIsValid get() = isPasswordValid(password)
    private val confirmPasswordIsValid get() = isPasswordValid(confirmPassword)
    val submitAvailable get() = emailIsValid && passwordIsValid && confirmPasswordIsValid

    fun createAccount() {
        if (password != confirmPassword) {
            errorMessage = "Passwords must be the same"
        }
        if (!createAccountJob.isActive) {
            createAccountJob = viewModelScope.launch {
                loading = true
                when (authenticationRepository.signUp(email, password)) {
                    SignUpResult.EmailAddressMalformed -> {
                        errorMessage = "Invalid Email address"
                    }

                    SignUpResult.Success -> {
                        /* Nothing to do - we'll transition soon */
                    }

                    SignUpResult.UserCollision -> {
                        errorMessage =
                            "There's already a user with that account. If you believe " +
                                    "this is a mistake, please contact support " // TODO - support
                    }

                    SignUpResult.WeakPasswordException -> {
                        errorMessage =
                            "Password is too weak" // TODO - match my password matcher with firebase
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

    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    fun onConfirmPasswordChange(newConfirmPassword: String) {
        confirmPassword = newConfirmPassword
    }
}