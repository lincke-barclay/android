package com.alth.events.ui.navigation

import com.alth.events.R

sealed class BottomAppBarRoute(val route: String, val title: String, val iconResourceId: Int) {
    data object Feed : BottomAppBarRoute("feed", "Feed", R.drawable.baseline_home_24)
    data object Profile : BottomAppBarRoute("profile", "Profile", R.drawable.baseline_person_24)
    data object Search : BottomAppBarRoute("search", "Search", R.drawable.baseline_search_24)
}
