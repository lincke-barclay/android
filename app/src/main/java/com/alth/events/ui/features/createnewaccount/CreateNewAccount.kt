package com.alth.events.ui.features.createnewaccount

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alth.events.R
import com.alth.events.util.Keyboard
import com.alth.events.util.keyboardAsState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNewAccount(
    navigateBack: () -> Unit,
    createAccountViewModel: CreateAccountViewModel = hiltViewModel(),
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val isKeyboardOpen by keyboardAsState()

    LaunchedEffect(key1 = createAccountViewModel.errorMessage) {
        createAccountViewModel.errorMessage?.let {
            scope.launch {
                snackbarHostState.showSnackbar(it)
            }
            createAccountViewModel.consumeErrorMessage(it)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { }, navigationIcon = {
                IconButton(onClick = navigateBack) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                        contentDescription = "Back arrow from create new account page",
                    )
                }
            })
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        CreateNewAccountStateless(
            modifier = Modifier.padding(it),
            submit = createAccountViewModel::createAccount,
            email = createAccountViewModel.email,
            password = createAccountViewModel.password,
            confirmPassword = createAccountViewModel.confirmPassword,
            onEmailChange = createAccountViewModel::onEmailChange,
            onPasswordChange = createAccountViewModel::onPasswordChange,
            onConfirmPasswordChange = createAccountViewModel::onConfirmPasswordChange,
            submitAvailable = createAccountViewModel.submitAvailable,
            loading = createAccountViewModel.loading,
            isKeyboardOpen = isKeyboardOpen == Keyboard.Opened,
        )
    }
}

@Composable
fun CreateNewAccountStateless(
    modifier: Modifier = Modifier,
    submit: () -> Unit,
    email: String,
    password: String,
    confirmPassword: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    loading: Boolean,
    submitAvailable: Boolean,
    isKeyboardOpen: Boolean,
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
                .imePadding(),
            verticalArrangement = Arrangement.Top,
        ) {
            // Only display info if keyboard is open to save space
            if (!isKeyboardOpen) {
                Text(
                    text = "Create New Account",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(top = 64.dp)
                )
                Text(
                    text = "Please enter your information to continue",
                    style = MaterialTheme.typography.labelLarge,
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(
                        if (!isKeyboardOpen) {
                            Modifier.padding(top = 36.dp)
                        } else {
                            Modifier
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(
                        id = R.drawable.baseline_email_24
                    ),
                    contentDescription = "Email icon for sign in screen",
                )
                Column {
                    OutlinedTextField(
                        value = email,
                        onValueChange = onEmailChange,
                        label = { Text("EMAIL") },
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .fillMaxWidth(),
                        maxLines = 1,
                        singleLine = true,
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(
                        id = R.drawable.baseline_lock_24,
                    ),
                    contentDescription = "Lock icon for password in sign in screen",
                )
                Column {
                    OutlinedTextField(
                        value = password,
                        onValueChange = onPasswordChange,
                        label = { Text("PASSWORD") },
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .fillMaxWidth()
                            .onKeyEvent {
                                if (it.key != Key.Enter) return@onKeyEvent false
                                if (it.type == KeyEventType.KeyUp && submitAvailable) {
                                    submit()
                                }
                                true
                            },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                        ),
                        visualTransformation = PasswordVisualTransformation(),
                        maxLines = 1,
                        singleLine = true,
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(
                        id = R.drawable.baseline_lock_24,
                    ),
                    contentDescription = "Lock icon for password in sign in screen",
                )
                Column {
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = onConfirmPasswordChange,
                        label = { Text("CONFIRM PASSWORD") },
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .fillMaxWidth()
                            .onKeyEvent {
                                if (it.key != Key.Enter) return@onKeyEvent false
                                if (it.type == KeyEventType.KeyUp && submitAvailable) {
                                    submit()
                                }
                                true
                            },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                        ),
                        visualTransformation = PasswordVisualTransformation(),
                        maxLines = 1,
                        singleLine = true,
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.End,
            ) {
                Button(
                    enabled = submitAvailable,
                    onClick = submit
                ) {
                    Text(text = "Submit")
                }
            }
        }
    }
}
