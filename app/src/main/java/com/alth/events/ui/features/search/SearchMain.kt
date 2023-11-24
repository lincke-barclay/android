package com.alth.events.ui.features.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.alth.events.R
import com.alth.events.database.models.derived.SearchEventResult
import com.alth.events.networking.models.users.ingress.PublicUserResponseDto
import com.alth.events.ui.features.search.components.CustomTabRow
import com.alth.events.ui.features.search.events.LazyListSearchEventsResult
import com.alth.events.ui.features.search.events.viewmodels.PagingSearchEventsAfterQuerySubmitViewModel
import com.alth.events.ui.features.search.events.viewmodels.QuickSearchResultsViewModel
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchMain(
    pagingSearchEventsAfterQuerySubmitViewModel: PagingSearchEventsAfterQuerySubmitViewModel = hiltViewModel(),
    quickSearchResultsViewModel: QuickSearchResultsViewModel = hiltViewModel(),
) {
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(true) }

    val searchResultsFromQuerySubmit by pagingSearchEventsAfterQuerySubmitViewModel.searchEventsFlow.collectAsState()
    val quickSearchResults by quickSearchResultsViewModel.results.collectAsState()

    Column {
        SearchBar(
            query = searchQuery,
            onQueryChange = {
                quickSearchResultsViewModel.onQueryChange(it)
                searchQuery = it
            },
            onSearch = {
                pagingSearchEventsAfterQuerySubmitViewModel.onQuerySubmit(it)
                isSearchActive = false
            },
            active = isSearchActive,
            onActiveChange = { isSearchActive = it },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_search_24),
                    contentDescription = "Search Icon",
                )
            },
            placeholder = { Text(text = "Search for events") }
        ) {
            CustomTabRow(
                eventContent = {
                    InsideEventSearchActive(
                        quickSearchResults = quickSearchResults,
                    )
                },
                friendsContent = {
                    Text("Friends")
                },
            )
        }
        CustomTabRow(
            eventContent = {
                SearchResultsView(
                    eventsFlow = searchResultsFromQuerySubmit,
                )
            },
            friendsContent = {
                Text("Friends")
            },
        )
    }
}

@Composable
fun InsideEventSearchActive(
    quickSearchResults: List<SearchEventResult>
) {
    Column {
        quickSearchResults.forEach {
            Row {
                Text(it.title)
                Text(it.ownerName)
            }
        }
    }
}

@Composable
fun SearchResultsView(
    eventsFlow: Flow<PagingData<SearchEventResult>>
) {
    LazyListSearchEventsResult(
        events = eventsFlow.collectAsLazyPagingItems(),
    )
}
