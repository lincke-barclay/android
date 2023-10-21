package com.alth.events.apps.feed

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.alth.events.apps.feed.viewmodels.FeedMainViewModel
import com.alth.events.apps.feed.viewmodels.NewEventViewModel

fun NavGraphBuilder.feedNavGraph(navController: NavController) {
    navigation(startDestination = "feed/main", route = "feed") {
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
                navController.navigate("feed")
            }
        }
    }
}
