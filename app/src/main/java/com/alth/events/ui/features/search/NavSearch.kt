package com.alth.events.ui.features.search

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.alth.events.ui.viewmodels.SearchViewModel
import com.alth.events.ui.navigation.BottomAppBarRoute

fun NavGraphBuilder.searchNavGraph(navController: NavController) {
    navigation(startDestination = "search/main", route = BottomAppBarRoute.Search.route) {
        composable("search/main") {
            val vm: SearchViewModel = hiltViewModel()
            SearchMain(vm)
        }
    }
}
