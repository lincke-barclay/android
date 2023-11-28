package com.alth.events.ui.features.createnewaccount

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.alth.events.R

@Composable
fun CreateNewAccount(
    navigateBack: () -> Unit,
) {
    CreateNewAccountStateless(navigateBack)
}

@Composable
fun CreateNewAccountStateless(
    navigateBack: () -> Unit,
) {
    Column {
        IconButton(onClick = navigateBack) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = "Back Button on the Sign In screen",
            )
        }
        Text("Create New Account")
    }
}
