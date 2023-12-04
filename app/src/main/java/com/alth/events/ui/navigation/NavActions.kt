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
    popUpToStartThenNavigate(RootRoute.Feed.route)
}

fun NavHostController.navigateToProfile() {
    popUpToStartThenNavigate(RootRoute.Profile.route)
}

fun NavHostController.navigateToSearch() {
    popUpToStartThenNavigate(RootRoute.Search.route)
}

fun NavHostController.pushToSearch() {
    navigate(RootRoute.Search.route)
}

fun NavHostController.navigateBack() {
    popBackStack()
}

fun NavHostController.navigateToProfilePicFirstTime() {
    popUpToStartThenNavigate("profile-pic-first-time")
}