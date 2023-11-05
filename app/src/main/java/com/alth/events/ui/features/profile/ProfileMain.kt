package com.alth.events.ui.features.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.alth.events.models.domain.authentication.AuthenticationState

@Composable
fun ProfileMain(
    user: AuthenticationState.UserOk,
    onSignOut: () -> Unit
) {
    Column {
        Text("Signed in as: ${user.name}")
        Button(onClick = onSignOut) {
            Text("Sign Out")
        }
    }
}
