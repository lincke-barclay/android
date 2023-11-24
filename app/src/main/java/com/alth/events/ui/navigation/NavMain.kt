package com.alth.events.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alth.events.ui.features.home.homeNavGraph
import com.alth.events.ui.features.myprofile.MyProfile
import com.alth.events.ui.features.otherprofile.OtherProfile
import com.alth.events.ui.features.search.searchNavGraph

@Composable
fun NavMain(
    navHostController: NavHostController,
    modifier: Modifier,
) {
    NavHost(
        navController = navHostController,
        startDestination = BottomAppBarRoute.Home.route,
        modifier = modifier,
    ) {
        homeNavGraph(navHostController)
        composable(BottomAppBarRoute.Profile.route) {
            MyProfile()
        }
        composable(
            "users/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) {
            navHostController.currentBackStackEntry?.arguments?.getString("userId")?.let {
                OtherProfile(hiltViewModel())
            }
        }
        searchNavGraph(navHostController)
    }
}
