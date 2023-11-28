package com.alth.events.ui.features.unverifieduser

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.alth.events.ui.viewmodels.landing.EmailVerificationViewModel

@Composable
fun UnverifiedUser(
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

