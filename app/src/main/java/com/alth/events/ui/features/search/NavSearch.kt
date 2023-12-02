package com.alth.events.ui.features.search

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.alth.events.ui.navigation.BottomAppBarRoute
import com.alth.events.ui.navigation.navigateToFeed
import com.alth.events.ui.navigation.navigateToProfile

fun NavGraphBuilder.searchNavGraph(navController: NavHostController) {
    navigation(startDestination = "search/main", route = BottomAppBarRoute.Search.route) {
        composable("search/main") {
            SearchMain(
                navigateToProfile = navController::navigateToProfile,
                navigateToFeed = navController::navigateToFeed,
            )
        }
    }
}
