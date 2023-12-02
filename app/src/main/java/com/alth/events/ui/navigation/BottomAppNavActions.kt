package com.alth.events.ui.navigation

import androidx.navigation.NavHostController

private fun NavHostController.popUpToStartThenNavigate(route: String) {
    navigate(route) {
        popUpTo("start") {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavHostController.navigateToStart() {
    navigate("start") {
        popUpTo("start") {
            inclusive = true
        }
    }
}

fun NavHostController.navigateToLogin() {
    popUpToStartThenNavigate("auth")
}

fun NavHostController.navigateToFeed() {
    popUpToStartThenNavigate(BottomAppBarRoute.Feed.route)
}

fun NavHostController.navigateToProfile() {
    popUpToStartThenNavigate(BottomAppBarRoute.Profile.route)
}

fun NavHostController.navigateToSearch() {
    popUpToStartThenNavigate(BottomAppBarRoute.Search.route)
}

fun NavHostController.navigateToProfilePicFirstTime() {
    popUpToStartThenNavigate("profile-pic-first-time")
}