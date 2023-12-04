package com.alth.events.ui.navigation

import com.alth.events.R

/**
 * Routes with an empty back stack
 */
sealed class RootRoute(val route: String, val title: String, val iconResourceId: Int) {
    data object Feed : RootRoute("feed", "Feed", R.drawable.baseline_home_24)
    data object Profile : RootRoute("profile", "Profile", R.drawable.baseline_person_24)
    data object Search : RootRoute("search", "Search", R.drawable.baseline_search_24)
}
