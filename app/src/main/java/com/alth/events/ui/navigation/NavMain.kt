package com.alth.events.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alth.events.ui.Start
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
        composable("start") {
            Start(
                navigateToFeed = navHostController::navigateToFeed,
                navigateToLogin = navHostController::navigateToLogin,
                navigateToProfilePicFirstTime = navHostController::navigateToProfile,
            )
        }

        composable("profile-pic-first-time") {
            ChangeProfilePhotoUrl()
        }
        authGraph(navHostController)
        feedNavGraph(navHostController)
        composable(BottomAppBarRoute.Profile.route) {
            MyProfile(
                navigateToFeed = navHostController::navigateToFeed,
                navigateToSearch = navHostController::navigateToSearch,
            )
        }
        searchNavGraph(navHostController)
    }
}
