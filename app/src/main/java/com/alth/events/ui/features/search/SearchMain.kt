package com.alth.events.ui.features.search

import androidx.compose.foundation.layout.Column
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
import com.alth.events.database.models.events.derived.SearchEventResult
import com.alth.events.database.models.users.PublicUserEntity
import com.alth.events.ui.features.search.components.CustomTabRow
import com.alth.events.ui.features.search.events.LazyListSearchEventsResult
import com.alth.events.ui.features.search.events.LimitedLazyListSearchEventsResult
import com.alth.events.ui.features.search.events.viewmodels.PagingSearchEventsAfterQuerySubmitViewModel
import com.alth.events.ui.features.search.events.viewmodels.QuickSearchEventsResultsViewModel
import com.alth.events.ui.features.search.users.LazyListSearchUserResult
import com.alth.events.ui.features.search.users.LimitedLazyListSearchUserResult
import com.alth.events.ui.features.search.users.viewmodels.PagingSearchUsersAfterQuerySubmitViewModel
import com.alth.events.ui.features.search.users.viewmodels.QuickSearchUserResultsViewModel
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchMain(
    pagingSearchEventsAfterQuerySubmitViewModel: PagingSearchEventsAfterQuerySubmitViewModel = hiltViewModel(),
    quickSearchEventsResultsViewModel: QuickSearchEventsResultsViewModel = hiltViewModel(),
    quickSearchUserResultsViewModel: QuickSearchUserResultsViewModel = hiltViewModel(),
    pagingSearchUsersAfterQuerySubmitViewModel: PagingSearchUsersAfterQuerySubmitViewModel = hiltViewModel(),
) {
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(true) }

    val eventSearchResultsFromQuerySubmit by pagingSearchEventsAfterQuerySubmitViewModel.searchEventsFlow.collectAsState()
    val eventQuickSearchResults by quickSearchEventsResultsViewModel.results.collectAsState()

    val userSearchResultsFromQuerySubmit by pagingSearchUsersAfterQuerySubmitViewModel.searchUsersFlow.collectAsState()
    val userQuickSearchResults by quickSearchUserResultsViewModel.results.collectAsState()

    Column {
        SearchBar(
            query = searchQuery,
            onQueryChange = {
                quickSearchEventsResultsViewModel.onQueryChange(it)
                quickSearchUserResultsViewModel.onQueryChange(it)
                searchQuery = it
            },
            onSearch = {
                pagingSearchEventsAfterQuerySubmitViewModel.onQuerySubmit(it)
                pagingSearchUsersAfterQuerySubmitViewModel.onQuerySubmit(it)
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
            placeholder = { Text(text = "Search") }
        ) {
            CustomTabRow(
                eventContent = {
                    LimitedEventSearchResultsView(events = eventQuickSearchResults)
                },
                friendsContent = {
                    LimitedUserSearchResultsView(users = userQuickSearchResults)
                },
            )
        }
        CustomTabRow(
            eventContent = {
                EventSearchResultsView(
                    eventsFlow = eventSearchResultsFromQuerySubmit,
                )
            },
            friendsContent = {
                UserSearchResultsView(userFlow = userSearchResultsFromQuerySubmit)
            },
        )
    }
}

@Composable
fun LimitedEventSearchResultsView(
    events: List<SearchEventResult>
) {
    LimitedLazyListSearchEventsResult(events = events)
}

@Composable
fun EventSearchResultsView(
    eventsFlow: Flow<PagingData<SearchEventResult>>
) {
    LazyListSearchEventsResult(
        events = eventsFlow.collectAsLazyPagingItems(),
    )
}

@Composable
fun LimitedUserSearchResultsView(
    users: List<PublicUserEntity>
) {
    LimitedLazyListSearchUserResult(users = users)
}

@Composable
fun UserSearchResultsView(
    userFlow: Flow<PagingData<PublicUserEntity>>
) {
    LazyListSearchUserResult(
        users = userFlow.collectAsLazyPagingItems(),
    )
}
