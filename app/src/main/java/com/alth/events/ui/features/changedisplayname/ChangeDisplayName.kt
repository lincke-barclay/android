package com.alth.events.ui.features.changedisplayname

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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch

@Composable
fun ChangeDisplayName(
    changeDisplayNameViewModel: ChangeDisplayNameViewModel = hiltViewModel()
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
            // TODO ?
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
            onSubmit = changeDisplayNameViewModel::changeName,
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
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    )
    if (loading) {
        CircularProgressIndicator()
    }
    Column {
        TextField(
            value = name,
            onValueChange = onChangeName,
            maxLines = 1,
            singleLine = true,
        )
        Button(onClick = onSubmit) {
            Text(text = "Submit")
        }
    }
}
