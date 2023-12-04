package com.alth.events.ui.features.search

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.alth.events.ui.navigation.RootRoute
import com.alth.events.ui.navigation.navigateBack

fun NavGraphBuilder.searchNavGraph(navController: NavHostController) {
    navigation(startDestination = "search/main", route = RootRoute.Search.route) {
        composable("search/main") {
            Search(
                navigateBack = navController::navigateBack,
            )
        }
    }
}
