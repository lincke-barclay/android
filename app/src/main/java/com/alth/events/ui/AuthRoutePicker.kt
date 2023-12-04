package com.alth.events.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.alth.events.models.authentication.AuthenticationState
import com.alth.events.ui.features.authentication.AuthenticationViewModel
import com.alth.events.ui.features.changedisplayname.ChangeDisplayName
import com.alth.events.ui.features.emaiverificiation.UnverifiedUser
import com.alth.events.ui.features.splashscreen.SplashScreen

/**
 * Renders a certain nav graph or plain screen
 * depending on the authentication state
 *
 * Meant to act as a guard for all authentication states
 * to reduce the turbulence of navigating via authentication
 */
@Composable
fun AuthRoutePicker(
    viewModel: AuthenticationViewModel = hiltViewModel(),
    navigateToFeed: () -> Unit,
    navigateToLogin: () -> Unit,
    navigateToProfilePicFirstTime: () -> Unit,
) {
    val authState by viewModel.currentlySignedInState.collectAsState()

    when (authState) {
        /**
         * User is signed in and good to go
         */
        is AuthenticationState.UserOk -> {
            LaunchedEffect(key1 = authState) {
                navigateToFeed()
            }
        }

        /**
         * User is signed out
         */
        is AuthenticationState.SignedOut -> {
            LaunchedEffect(key1 = authState) {
                navigateToLogin()
            }
        }

        /**
         * User is signed in but they haven't
         * verified their email
         */
        is AuthenticationState.UserUnverified -> {
            UnverifiedUser()
        }

        /**
         * User is signed in but they don't have both a display name
         * set in their firebase account
         */
        is AuthenticationState.UsernameNotSet -> {
            ChangeDisplayName(
                navigateToProfilePicFirstTime = navigateToProfilePicFirstTime,
            )
        }

        /**
         * Splash screen (but not because Android doesn't like that word)
         * Either waiting on state of auth to be determined
         * or waiting on the navigation to finish
         */
        is AuthenticationState.Unknown -> {
            SplashScreen()
        }
    }
}
