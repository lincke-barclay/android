package com.alth.events.ui.features.changedisplayname

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeDisplayName(
    changeDisplayNameViewModel: ChangeDisplayNameViewModel = hiltViewModel(),
    navigateToProfilePicFirstTime: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = changeDisplayNameViewModel.errorMessage) {
        changeDisplayNameViewModel.errorMessage?.let {
            scope.launch {
                snackbarHostState.showSnackbar(it)
            }
            changeDisplayNameViewModel.consumeErrorMessage(it)
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Change your display name") },
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) {
        ChangeDisplayNameStateless(
            modifier = Modifier.padding(it),
            name = changeDisplayNameViewModel.name,
            onChangeName = changeDisplayNameViewModel::onChangeName,
            loading = changeDisplayNameViewModel.loading,
            onSubmit = {
                changeDisplayNameViewModel.changeName(navigateToProfilePicFirstTime)
            },
            submitAvailable = changeDisplayNameViewModel.nameIsValid,
        )
    }
}

@Composable
fun ChangeDisplayNameStateless(
    modifier: Modifier = Modifier,
    name: String,
    onChangeName: (String) -> Unit,
    loading: Boolean,
    onSubmit: () -> Unit,
    submitAvailable: Boolean,
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        if (loading) {
            CircularProgressIndicator()
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.Top,
        ) {
            Text(
                "What name do you want people to call you?",
                style = MaterialTheme.typography.labelLarge,
            )
            OutlinedTextField(
                value = name,
                onValueChange = onChangeName,
                label = { Text("Name") },
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth(),
                maxLines = 1,
                singleLine = true,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.End,
            ) {
                Button(
                    enabled = submitAvailable,
                    onClick = onSubmit,
                ) {
                    Text(text = "Submit")
                }
            }
        }
    }
}
