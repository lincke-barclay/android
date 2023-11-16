package com.alth.events.ui.features.search

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.alth.events.models.network.users.ingress.PublicUserResponseDto
import com.alth.events.ui.features.search.components.CustomTabRow
import com.alth.events.ui.viewmodels.search.SearchEventsViewModel
import com.alth.events.ui.viewmodels.search.SearchFriendsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchMain(
    searchEventsViewModel: SearchEventsViewModel = hiltViewModel(),
    searchFriendsViewModel: SearchFriendsViewModel = hiltViewModel(),
    navigateToPublicUser: (PublicUserResponseDto) -> Unit,
) {
    var queryString by remember { mutableStateOf("") }
    val eventUiState by searchEventsViewModel.uiState.collectAsState()
    val friendsUiState by searchFriendsViewModel.uiState.collectAsState()

    Column {
        TextField(value = queryString, onValueChange = {
            queryString = it
            searchEventsViewModel.onQueryStringChange(it)
            searchFriendsViewModel.onQueryStringChange(it)
        })
        CustomTabRow(
            eventContent = {
                EventsSearchStateless(
                    eventResults = eventUiState.events,
                    consumeItem = searchEventsViewModel::consumeItem,
                )
            },
            friendsContent = {
                FriendsSearchStateless(
                    friendResults = friendsUiState.friends,
                    consumeItem = searchFriendsViewModel::consumeItem,
                    onClickOnUser = navigateToPublicUser,
                )
            },
        )
    }
}

