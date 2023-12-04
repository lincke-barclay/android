package com.alth.events.ui.features.feed

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.alth.events.ui.features.newevent.NewEvent
import com.alth.events.ui.navigation.RootRoute
import com.alth.events.ui.navigation.navigateToProfile
import com.alth.events.ui.navigation.pushToSearch

fun NavGraphBuilder.feedNavGraph(navController: NavHostController) {
    navigation(startDestination = "feed/main", route = RootRoute.Feed.route) {
        composable("feed/main") {
            Feed(
                navigateToNewEvent = {
                    navController.navigate("feed/newEvent")
                },
                navigateToSearch = navController::pushToSearch,
                navigateToProfile = navController::navigateToProfile,
            )
        }
        composable("feed/newEvent") {
            NewEvent {
                navController.navigate(RootRoute.Feed.route)
            }
        }
    }
}
