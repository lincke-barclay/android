package com.alth.events.ui.features.feed

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.alth.events.ui.navigation.BottomAppBarRoute
import com.alth.events.ui.viewmodels.FeedMainViewModel
import com.alth.events.ui.viewmodels.NewEventViewModel

fun NavGraphBuilder.feedNavGraph(navController: NavController) {
    navigation(startDestination = "feed/main", route = BottomAppBarRoute.Feed.route) {
        composable("feed/main") {
            val feedMainViewModel: FeedMainViewModel = hiltViewModel()
            FeedMain(
                feedMainViewModel = feedMainViewModel,
                navigateToNewEvent = {
                    navController.navigate("feed/newEvent")
                }
            )
        }
        composable("feed/newEvent") {
            val newEventViewModel: NewEventViewModel = hiltViewModel()
            NewFeedMain(newEventViewModel) {
                navController.navigate(BottomAppBarRoute.Feed.route)
            }
        }
    }
}
