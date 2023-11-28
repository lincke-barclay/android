package com.alth.events.ui.features.authentication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChooseLoginOrCreate(
    navigateToLogin: () -> Unit,
    navigateToCreateNewAccount: () -> Unit,
) {
    ChooseLoginOrCreateStateless(
        navigateToLogin = navigateToLogin,
        navigateToCreateNewAccount = navigateToCreateNewAccount,
    )
}

@Composable
fun ChooseLoginOrCreateStateless(
    navigateToLogin: () -> Unit,
    navigateToCreateNewAccount: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Button(
            onClick = navigateToLogin,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
        ) {
            Text("Log In")
        }
        OutlinedButton(
            onClick = navigateToCreateNewAccount,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
        ) {
            Text("Create new account")
        }
    }
}
