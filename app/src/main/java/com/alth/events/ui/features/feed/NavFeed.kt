package com.alth.events.ui.features.feed

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.alth.events.ui.features.newevent.NewEventMain
import com.alth.events.ui.navigation.BottomAppBarRoute
import com.alth.events.ui.navigation.navigateToProfile
import com.alth.events.ui.navigation.navigateToSearch

fun NavGraphBuilder.feedNavGraph(navController: NavHostController) {
    navigation(startDestination = "feed/main", route = BottomAppBarRoute.Feed.route) {
        composable("feed/main") {
            Feed(
                navigateToNewEvent = {
                    navController.navigate("feed/newEvent")
                },
                navigateToSearch = navController::navigateToSearch,
                navigateToProfile = navController::navigateToProfile,
            )
        }
        composable("feed/newEvent") {
            NewEventMain {
                navController.navigate(BottomAppBarRoute.Feed.route)
            }
        }
    }
}
