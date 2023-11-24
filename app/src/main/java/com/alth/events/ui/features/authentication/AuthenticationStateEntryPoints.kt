package com.alth.events.ui.features.authentication

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.alth.events.models.domain.authentication.AuthenticationState
import com.alth.events.ui.components.IndeterminateCircularIndicator
import com.alth.events.ui.layouts.BottomAppNavTheme
import com.alth.events.ui.viewmodels.AuthenticationViewModel
import com.alth.events.ui.viewmodels.landing.EditProfileViewModel
import com.alth.events.ui.viewmodels.landing.EmailVerificationViewModel
import com.alth.events.ui.viewmodels.landing.SignInViewModel

@Composable
fun AuthenticationStateEntryPoint(
    viewModel: AuthenticationViewModel,
) {
    val authState by viewModel.currentlySignedInState.collectAsState()

    authState.let {
        when (it) {
            is AuthenticationState.UserOk -> {
                UserOkEntryPoint()
            }

            is AuthenticationState.UserUnverified -> {
                UserUnverifiedEntryPoint()
            }

            is AuthenticationState.UserUninitialized -> {
                UserUninitializedEntryPoint()
            }

            is AuthenticationState.Unknown -> {
                UnknownEntryPoint()
            }

            is AuthenticationState.SignedOut -> {
                SignedOutEntryPoint()
            }
        }
    }
}

@Composable
fun UserOkEntryPoint() {
    BottomAppNavTheme()
}

@Composable
fun UserUnverifiedEntryPoint(
    emailVerificationViewModel: EmailVerificationViewModel = hiltViewModel()
) {
    Column {
        Text("Signed In but unverified")
        Button(
            onClick = emailVerificationViewModel::onClickSendVerificationEmail
        ) {
            Text("Send Verification Email")
        }
        Button(
            onClick = emailVerificationViewModel::reload
        ) {
            Text("Try Again")
        }
        Button(onClick = { emailVerificationViewModel.signOut() }) {
            Text("Sign Out")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserUninitializedEntryPoint(
    editProfileViewModel: EditProfileViewModel = hiltViewModel()
) {
    var enteredName by remember { mutableStateOf("") }
    Column {
        Text("Name:")
        TextField(value = enteredName, onValueChange = { enteredName = it })
        Button(
            enabled = enteredName.isNotEmpty(),
            onClick = { editProfileViewModel.changeName(enteredName) }) {
            Text("Create Profile")
        }
        Button(onClick = { editProfileViewModel.signOut() }) {
            Text("Sign Out")
        }
    }
}

@Composable
fun UnknownEntryPoint() {
    IndeterminateCircularIndicator()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignedOutEntryPoint(
    viewModel: SignInViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column {
        Text("Not Signed In")
        Text("Email:")
        TextField(value = email, onValueChange = { email = it })
        Text("Password:")
        TextField(value = password, onValueChange = { password = it })

        Button(onClick = { viewModel.signIn(email, password) }) {
            Text("Sign In")
        }
        Button(onClick = { viewModel.signUp(email, password) }) {
            Text("Sign Up")
        }
    }
}
