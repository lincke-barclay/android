package com.alth.events.ui.layouts

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.alth.events.ui.navigation.BottomAppBarRoute

@Composable
fun BottomAppNavBar(
    navigateToFeed: () -> Unit,
    navigateToProfile: () -> Unit,
    navigateToSearch: () -> Unit,
    currentRoute: BottomAppBarRoute,
) {
    NavigationBar {
        // Search
        NavigationBarItem(
            selected = currentRoute == BottomAppBarRoute.Search,
            onClick = navigateToSearch,
            icon = {
                Icon(
                    painterResource(id = BottomAppBarRoute.Search.iconResourceId),
                    contentDescription = null
                )
            },
        )
        // Feed
        NavigationBarItem(
            selected = currentRoute == BottomAppBarRoute.Feed,
            onClick = navigateToFeed,
            icon = {
                Icon(
                    painterResource(id = BottomAppBarRoute.Feed.iconResourceId),
                    contentDescription = null
                )
            },
        )

        // Profile
        NavigationBarItem(
            selected = currentRoute == BottomAppBarRoute.Profile,
            onClick = navigateToProfile,
            icon = {
                Icon(
                    painterResource(id = BottomAppBarRoute.Profile.iconResourceId),
                    contentDescription = null
                )
            },
        )
    }
}

