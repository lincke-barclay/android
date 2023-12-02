package com.alth.events.ui.features.unverifieduser

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alth.events.ui.viewmodels.landing.EmailVerificationViewModel
import kotlinx.coroutines.launch

@Composable
fun UnverifiedUser(
    emailVerificationViewModel: EmailVerificationViewModel = hiltViewModel(),
) {
    var scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val secondsTillReset = emailVerificationViewModel.secondsTillTryAgain
    val errorMessage = emailVerificationViewModel.errorMessage
    val loading = emailVerificationViewModel.loading

    LaunchedEffect(key1 = errorMessage) {
        errorMessage?.let {
            scope.launch {
                snackbarHostState.showSnackbar(it)
            }
            emailVerificationViewModel.consumeErrorMessage(it)
        }
    }

    Scaffold(
        topBar = {
            //
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        UnverifiedUserStateless(
            modifier = Modifier.padding(it),
            sendEmail = emailVerificationViewModel::tryAgain,
            signOut = emailVerificationViewModel::signOut,
            loading = loading,
            secondsTillReset = secondsTillReset,
        )
    }
}


@Composable
fun UnverifiedUserStateless(
    modifier: Modifier = Modifier,
    sendEmail: () -> Unit,
    signOut: () -> Unit,
    loading: Boolean,
    secondsTillReset: Int,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.Center,
    ) {
        if (loading) {
            CircularProgressIndicator()
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                "Email Sent",
                style = MaterialTheme.typography.headlineLarge,
            )
            Text(
                "We just sent you an email with instructions to " +
                        "activate your account. If you don't see the email in your inbox, be" +
                        " sure to check your spam folder as well",
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                "Need further assistance? Contact our support team at " +
                        "lincketheo@gmail.com",
                style = MaterialTheme.typography.bodySmall,
            )
            Text("Didn't get it?")
            if (secondsTillReset == 0) {
                TextButton(onClick = sendEmail) {
                    Text("Try Again")
                }
            } else {
                TextButton(
                    onClick = sendEmail,
                ) {
                    Text("Try again in $secondsTillReset seconds")
                }
            }
            Button(onClick = signOut) {
                Text("Log In")
            }
        }
    }
}

@Composable
@Preview
fun PreviewUnverifiedUser() {
    UnverifiedUserStateless(
        sendEmail = { /*TODO*/ },
        signOut = { /*TODO*/ },
        loading = false,
        secondsTillReset = 5,
    )
}