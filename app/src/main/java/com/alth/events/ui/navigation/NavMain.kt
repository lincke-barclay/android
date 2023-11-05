package com.alth.events.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alth.events.models.domain.authentication.AuthenticationState
import com.alth.events.ui.features.feed.feedNavGraph
import com.alth.events.ui.features.profile.ProfileMain
import com.alth.events.ui.features.search.searchNavGraph

@Composable
fun NavMain(
    navHostController: NavHostController,
    modifier: Modifier,
    user: AuthenticationState.UserOk,
    onSignOut: () -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = BottomAppBarRoute.Feed.route,
        modifier = modifier,
    ) {
        feedNavGraph(navHostController)
        composable(BottomAppBarRoute.Profile.route) {
            ProfileMain(user, onSignOut)
        }
        searchNavGraph(navHostController)
    }
}
