package com.alth.events.ui.layouts

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.alth.events.ui.navigation.AuthenticatedNavMain
import com.alth.events.ui.navigation.BottomAppBarRoute

@Composable
fun BottomAppNavTheme(
    navHostController: NavHostController,
) {
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navHostController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                // Search
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == BottomAppBarRoute.Search.route } == true,
                    onClick = {
                        navHostController.navigate(BottomAppBarRoute.Search.route) {
                            popUpTo(navHostController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            painterResource(id = BottomAppBarRoute.Search.iconResourceId),
                            contentDescription = null
                        )
                    },
                )
                // Home
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == BottomAppBarRoute.Home.route } == true,
                    onClick = {
                        navHostController.navigate(BottomAppBarRoute.Home.route) {
                            popUpTo(navHostController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            painterResource(id = BottomAppBarRoute.Home.iconResourceId),
                            contentDescription = null
                        )
                    },
                )

                // Profile
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == BottomAppBarRoute.Profile.route } == true,
                    onClick = {
                        navHostController.navigate(BottomAppBarRoute.Profile.route) {
                            popUpTo(navHostController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            painterResource(id = BottomAppBarRoute.Profile.iconResourceId),
                            contentDescription = null
                        )
                    },
                )
            }
        }
    ) { innerPadding ->
        AuthenticatedNavMain(
            navHostController = navHostController,
            modifier = Modifier.padding(innerPadding),
        )
    }
}
