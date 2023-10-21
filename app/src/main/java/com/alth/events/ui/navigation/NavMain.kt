package com.alth.events.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alth.events.apps.calendar.CalendarMain
import com.alth.events.apps.feed.feedNavGraph

@Composable
fun NavMain(
    navHostController: NavHostController,
    modifier: Modifier,
) {
    NavHost(
        navController = navHostController,
        startDestination = "feed",
        modifier = modifier,
    ) {
        feedNavGraph(navHostController)
        composable("calendar") {
            CalendarMain()
        }
    }
}
