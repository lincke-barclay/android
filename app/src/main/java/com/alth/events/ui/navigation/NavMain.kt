package com.alth.events.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alth.events.ui.AuthRoutePicker
import com.alth.events.ui.features.authentication.authGraph
import com.alth.events.ui.features.changeprofilephotourl.ChangeProfilePhotoUrl
import com.alth.events.ui.features.feed.feedNavGraph
import com.alth.events.ui.features.myprofile.MyProfile
import com.alth.events.ui.features.search.searchNavGraph

@Composable
fun NavMain(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navHostController,
        startDestination = "start",
        modifier = modifier,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        /**
         * This never actually renders anything, it's just the root destination
         * that chooses what route to start with
         *
         * On Authentication State change, you can just navigate up to here
         * to choose which screen to show
         */
        composable("start") {
            AuthRoutePicker(
                navigateToFeed = navHostController::navigateToFeed,
                navigateToLogin = navHostController::navigateToLogin,
                navigateToProfilePicFirstTime = navHostController::navigateToProfile,
            )
        }

        /**
         * Route to choose your profile picture for the first time - only navigates here
         * once directly after you set your name in the app.
         */
        composable("profile-pic-first-time") {
            ChangeProfilePhotoUrl()
        }

        /**
         * Un authenticated route
         */
        authGraph(navHostController)

        /**
         * Main three routes
         */
        feedNavGraph(navHostController)
        composable(RootRoute.Profile.route) {
            MyProfile(
                navigateBack = navHostController::navigateBack,
            )
        }
        searchNavGraph(navHostController)
    }
}
