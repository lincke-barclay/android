package com.alth.events.ui.features.authentication

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.alth.events.ui.features.createnewaccount.CreateNewAccount
import com.alth.events.ui.features.login.Login

fun NavGraphBuilder.authGraph(navHostController: NavHostController) {
    navigation(startDestination = "auth/root", route = "auth") {

        /**
         * Choose your adventure screen
         */
        composable("auth/root") {
            ChooseLoginOrCreate(
                navigateToLogin = {
                    navHostController.navigate("auth/login")
                },
                navigateToCreateNewAccount = {
                    navHostController.navigate("auth/create")
                })
        }

        /**
         * Login Page
         */
        composable("auth/login") {
            Login(
                navigateBack = {
                    navHostController.popBackStack()
                },
            )
        }

        composable("auth/create") {
            CreateNewAccount(
                navigateBack = {
                    navHostController.popBackStack()
                },
            )
        }
    }
}