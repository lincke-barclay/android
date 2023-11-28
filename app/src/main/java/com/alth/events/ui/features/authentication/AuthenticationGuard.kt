package com.alth.events.ui.features.authentication

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.alth.events.models.domain.authentication.AuthenticationState
import com.alth.events.ui.features.changedisplayname.ChangeDisplayName
import com.alth.events.ui.features.changeprofilephotourl.ChangeProfilePhotoUrl
import com.alth.events.ui.features.splashscreen.SplashScreen
import com.alth.events.ui.features.unverifieduser.UnverifiedUser
import com.alth.events.ui.layouts.BottomAppNavTheme
import com.alth.events.ui.viewmodels.AuthenticationViewModel

/**
 * Renders a certain nav graph or plain screen
 * depending on the authentication state
 *
 * Meant to act as a guard for all authentication states
 * to reduce the turbulence of navigating via authentication
 */
@Composable
fun AuthenticationGuard(
    viewModel: AuthenticationViewModel = hiltViewModel(),
    navHostController: NavHostController = rememberNavController(),
) {
    val authState by viewModel.currentlySignedInState.collectAsState()

    authState.let {
        when (it) {
            /**
             * User is signed in
             */
            is AuthenticationState.UserOk -> {
                BottomAppNavTheme(
                    navHostController = navHostController,
                )
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
                ChangeDisplayName()
            }

            /**
             * Splash screen of sorts - waiting on backend to
             * get the state of auth
             */
            is AuthenticationState.Unknown -> {
                SplashScreen()
            }

            /**
             * User is signed out
             */
            is AuthenticationState.SignedOut -> {
                Unauthenticated(
                    navHostController = navHostController,
                )
            }
        }
    }
}


