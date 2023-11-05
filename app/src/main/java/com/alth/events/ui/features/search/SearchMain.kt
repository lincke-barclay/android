package com.alth.events.ui.features.search

import android.view.KeyEvent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.input.ImeAction
import com.alth.events.models.domain.search.SearchResult
import com.alth.events.models.domain.users.PublicUser
import com.alth.events.ui.features.search.components.SearchResultEventView
import com.alth.events.ui.features.search.components.SearchResultUserView
import com.alth.events.ui.viewmodels.SearchViewModel

@Composable
fun SearchMain(searchViewModel: SearchViewModel) {
    val uiState by searchViewModel.uiState.collectAsState()
    var searchText by remember {
        mutableStateOf("")
    }
    SearchMainStateless(
        searchResults = uiState.searchResults,
        searchText = searchText,
        onSearchTextChange = {
            searchText = it
            searchViewModel.onNewQueryTyped(searchText)
        },
        onEnterButtonPressed = {
            searchViewModel.onNewQueryEnterButtonPressed(searchText)
        },
        sendFriendRequest = {
            searchViewModel.sendFriendRequest(it)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchMainStateless(
    searchResults: List<SearchResult>,
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onEnterButtonPressed: () -> Unit,
    sendFriendRequest: (user: PublicUser) -> Unit,
) {
    Column {
        TextField(
            value = searchText,
            onValueChange = onSearchTextChange,
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                onEnterButtonPressed()
            }),
            modifier = Modifier.onKeyEvent {
                if (it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                    onEnterButtonPressed()
                    true
                } else {
                    false
                }
            }
        )
        searchResults.forEach {
            when (it) {
                is SearchResult.Event -> {
                    SearchResultEventView(event = it.event)
                }

                is SearchResult.User -> {
                    SearchResultUserView(
                        user = it.user,
                        sendFriendRequest = sendFriendRequest
                    )
                }
            }
        }
    }
}


