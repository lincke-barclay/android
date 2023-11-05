package com.alth.events.ui.layouts

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alth.events.models.domain.authentication.AuthenticationState
import com.alth.events.ui.navigation.BottomAppBarRoute
import com.alth.events.ui.navigation.NavMain

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomAppNavTheme(
    user: AuthenticationState.UserOk,
    signOutCallback: () -> Unit,
) {
    val navHostController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navHostController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                BottomAppBarRoute.items.forEach { screen ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navHostController.navigate(screen.route) {
                                popUpTo(navHostController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Text(screen.title) },
                    )
                }
            }
        }
    ) { innerPadding ->
        NavMain(
            navHostController = navHostController,
            modifier = Modifier.padding(innerPadding),
            user = user,
            onSignOut = signOutCallback
        )
    }
}
