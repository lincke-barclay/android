package com.alth.events.ui.features.unverifieduser

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.alth.events.ui.viewmodels.landing.EmailVerificationViewModel
import kotlinx.coroutines.launch

@Composable
fun UnverifiedUser(
    emailVerificationViewModel: EmailVerificationViewModel = hiltViewModel(),
) {
    var scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = emailVerificationViewModel.errorMessage) {
        emailVerificationViewModel.errorMessage?.let {
            scope.launch {
                snackbarHostState.showSnackbar(it)
            }
            emailVerificationViewModel.consumeErrorMessage(it)
        }
    }

    Scaffold(
        topBar = {
            // TODO
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        UnverifiedUserStateless(
            modifier = Modifier.padding(it),
            sendEmail = emailVerificationViewModel::onClickSendVerificationEmail,
            tryAgain = emailVerificationViewModel::reload,
            signOut = emailVerificationViewModel::signOut,
            loading = emailVerificationViewModel.loading,
        )
    }
}


@Composable
fun UnverifiedUserStateless(
    modifier: Modifier = Modifier,
    sendEmail: () -> Unit,
    tryAgain: () -> Unit,
    signOut: () -> Unit,
    loading: Boolean,
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
    ) {
        if (loading) {
            CircularProgressIndicator()
        }
        Column {
            Text("Signed In but unverified")
            Button(
                onClick = sendEmail,
            ) {
                Text("Send Verification Email")
            }
            Button(
                onClick = tryAgain,
            ) {
                Text("Try Again")
            }
            Button(onClick = signOut) {
                Text("Sign Out")
            }
        }
    }
}

