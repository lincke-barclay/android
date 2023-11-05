package com.alth.events.ui.navigation

sealed class BottomAppBarRoute(val route: String, val title: String) {
    data object Feed : BottomAppBarRoute("feed", "Feed")
    data object Profile : BottomAppBarRoute("profile", "Profile")
    data object Search : BottomAppBarRoute("search", "Search")

    companion object {
        val items = listOf(Feed, Profile, Search)
    }
}
