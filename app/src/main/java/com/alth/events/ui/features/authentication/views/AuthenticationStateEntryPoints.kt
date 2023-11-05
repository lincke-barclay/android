package com.alth.events.ui.features.authentication.views

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
import com.alth.events.models.domain.authentication.AuthenticationState
import com.alth.events.ui.components.IndeterminateCircularIndicator
import com.alth.events.ui.viewmodels.AuthenticationViewModel
import com.alth.events.ui.layouts.BottomAppNavTheme

@Composable
fun AuthenticationStateEntryPoint(
    viewModel: AuthenticationViewModel,
) {
    val authState by viewModel.uiState.collectAsState()

    authState.let {
        when (it) {
            is AuthenticationState.UserOk -> {
                UserOkEntryPoint(
                    userOk = it,
                    signOutCallback = viewModel::signOut
                )
            }

            is AuthenticationState.UserUnverified -> {
                UserUnverifiedEntryPoint(
                    sendVerificationEmailCallback = viewModel::onClickSendVerificationEmail,
                    tryAgainCallback = viewModel::reload,
                )
            }

            is AuthenticationState.UserUninitialized -> {
                UserUninitializedEntryPoint(
                    submitNameData = viewModel::changeName,
                )
            }

            is AuthenticationState.Unknown -> {
                UnknownEntryPoint()
            }

            is AuthenticationState.SignedOut -> {
                SignedOutEntryPoint(viewModel::signIn, viewModel::signUp)
            }
        }
    }
}

@Composable
fun UserOkEntryPoint(
    userOk: AuthenticationState.UserOk,
    signOutCallback: () -> Unit,
) {
    BottomAppNavTheme(userOk, signOutCallback)
}

@Composable
fun UserUnverifiedEntryPoint(
    sendVerificationEmailCallback: () -> Unit,
    tryAgainCallback: () -> Unit,
) {
    Column {
        Text("Signed In but unverified")
        Button(onClick = sendVerificationEmailCallback) {
            Text("Send Verification Email")
        }
        Button(onClick = tryAgainCallback) {
            Text("Try Again")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserUninitializedEntryPoint(
    submitNameData: (String) -> Unit,
) {
    var enteredName by remember { mutableStateOf("") }
    Column {
        Text("Name:")
        TextField(value = enteredName, onValueChange = { enteredName = it })
        Button(enabled = enteredName.isNotEmpty(), onClick = { submitNameData(enteredName) }) {
            Text("Create Profile")
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
    onSignInCallback: (String, String) -> Unit,
    onSignUpCallback: (String, String) -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column {
        Text("Not Signed In")
        Text("Email:")
        TextField(value = email, onValueChange = { email = it })
        Text("Password:")
        TextField(value = password, onValueChange = { password = it })

        Button(onClick = { onSignInCallback(email, password) }) {
            Text("Sign In")
        }
        Button(onClick = { onSignUpCallback(email, password) }) {
            Text("Sign Up")
        }
    }
}
