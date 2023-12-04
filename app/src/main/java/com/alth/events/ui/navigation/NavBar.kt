package com.alth.events.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource

@Composable
fun BottomAppNavBar(
    navigateToFeed: () -> Unit,
    navigateToProfile: () -> Unit,
    navigateToSearch: () -> Unit,
    currentRoute: RootRoute,
) {
    NavigationBar {
        // Search
        NavigationBarItem(
            selected = currentRoute == RootRoute.Search,
            onClick = navigateToSearch,
            icon = {
                Icon(
                    painterResource(id = RootRoute.Search.iconResourceId),
                    contentDescription = null
                )
            },
        )
        // Feed
        NavigationBarItem(
            selected = currentRoute == RootRoute.Feed,
            onClick = navigateToFeed,
            icon = {
                Icon(
                    painterResource(id = RootRoute.Feed.iconResourceId),
                    contentDescription = null
                )
            },
        )

        // Profile
        NavigationBarItem(
            selected = currentRoute == RootRoute.Profile,
            onClick = navigateToProfile,
            icon = {
                Icon(
                    painterResource(id = RootRoute.Profile.iconResourceId),
                    contentDescription = null
                )
            },
        )
    }
}

