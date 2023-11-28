package com.alth.events.ui.features.authentication

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alth.events.ui.features.createnewaccount.CreateNewAccount
import com.alth.events.ui.features.login.Login

@Composable
fun UnauthenticatedNavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = "auth/root",
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        composable(
            "auth/root",
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
        ) {
            ChooseLoginOrCreate(
                navigateToLogin = {
                    navHostController.navigate("auth/login")
                },
                navigateToCreateNewAccount = {
                    navHostController.navigate("auth/create")
                })
        }
        composable(
            "auth/login",
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
        ) {
            Login(
                navigateBack = {
                    navHostController.popBackStack()
                }
            )
        }
        composable("auth/create") {
            CreateNewAccount(
                navigateBack = {
                    navHostController.popBackStack()
                }
            )
        }
    }
}
