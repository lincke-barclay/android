package com.alth.events.ui.features.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.alth.events.R
import com.alth.events.database.models.events.derived.SearchEventResult
import com.alth.events.database.models.users.PublicUserEntity
import com.alth.events.ui.features.search.events.LazyListSearchEventsResult
import com.alth.events.ui.features.search.events.LimitedLazyListSearchEventsResult
import com.alth.events.ui.features.search.events.viewmodels.PagingSearchEventsAfterQuerySubmitViewModel
import com.alth.events.ui.features.search.events.viewmodels.QuickSearchEventsResultsViewModel
import com.alth.events.ui.features.search.users.LazyListSearchUserResult
import com.alth.events.ui.features.search.users.LimitedLazyListSearchUserResult
import com.alth.events.ui.features.search.users.viewmodels.PagingSearchUsersAfterQuerySubmitViewModel
import com.alth.events.ui.features.search.users.viewmodels.QuickSearchUserResultsViewModel
import com.alth.events.ui.layouts.BottomAppNavBar
import com.alth.events.ui.navigation.BottomAppBarRoute
import kotlinx.coroutines.flow.Flow

@Composable
fun SearchMain(
    pagingSearchEventsAfterQuerySubmitViewModel: PagingSearchEventsAfterQuerySubmitViewModel = hiltViewModel(),
    quickSearchEventsResultsViewModel: QuickSearchEventsResultsViewModel = hiltViewModel(),
    quickSearchUserResultsViewModel: QuickSearchUserResultsViewModel = hiltViewModel(),
    pagingSearchUsersAfterQuerySubmitViewModel: PagingSearchUsersAfterQuerySubmitViewModel = hiltViewModel(),
    navigateToFeed: () -> Unit,
    navigateToProfile: () -> Unit,
) {
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(true) }

    val eventSearchResultsFromQuerySubmit by pagingSearchEventsAfterQuerySubmitViewModel.searchEventsFlow.collectAsState()
    val eventQuickSearchResults by quickSearchEventsResultsViewModel.results.collectAsState()

    val userSearchResultsFromQuerySubmit by pagingSearchUsersAfterQuerySubmitViewModel.searchUsersFlow.collectAsState()
    val userQuickSearchResults by quickSearchUserResultsViewModel.results.collectAsState()

    var currentResultsShowing by remember {
        mutableStateOf<SearchResultsPage>(SearchResultsPage.Events)
    }

    Scaffold(
        bottomBar = {
            BottomAppNavBar(
                navigateToFeed = navigateToFeed,
                navigateToProfile = navigateToProfile,
                navigateToSearch = { /* nothing to do */ },
                currentRoute = BottomAppBarRoute.Search
            )
        },
    ) { innerPadding ->
        SearchStateless(
            searchQuery = searchQuery,
            onQueryChange = {
                quickSearchEventsResultsViewModel.onQueryChange(it)
                quickSearchUserResultsViewModel.onQueryChange(it)
                searchQuery = it
            },
            onQuerySubmit = {
                pagingSearchEventsAfterQuerySubmitViewModel.onQuerySubmit(it)
                pagingSearchUsersAfterQuerySubmitViewModel.onQuerySubmit(it)
                isSearchActive = false
            },
            isSearchActive = isSearchActive,
            onActiveChange = {
                isSearchActive = it
            },
            pageShowing = currentResultsShowing,
            switchToEventResults = { currentResultsShowing = SearchResultsPage.Events },
            switchToPeopleResults = { currentResultsShowing = SearchResultsPage.People },
            quickQueryEventResults = eventQuickSearchResults,
            quickQueryPeopleResults = userQuickSearchResults,
            pagingEventsResults = eventSearchResultsFromQuerySubmit,
            pagingUserResults = userSearchResultsFromQuerySubmit,
            modifier = Modifier.padding(innerPadding)
        )
    }
}


sealed interface SearchResultsPage {
    data object People : SearchResultsPage
    data object Events : SearchResultsPage
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchStateless(
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    onQuerySubmit: (String) -> Unit,
    isSearchActive: Boolean,
    onActiveChange: (Boolean) -> Unit,
    pageShowing: SearchResultsPage,
    switchToPeopleResults: () -> Unit, // TODO
    switchToEventResults: () -> Unit, // TODO
    quickQueryEventResults: List<SearchEventResult>,
    quickQueryPeopleResults: List<PublicUserEntity>,
    pagingEventsResults: Flow<PagingData<SearchEventResult>>,
    pagingUserResults: Flow<PagingData<PublicUserEntity>>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        SearchBar(
            query = searchQuery,
            onQueryChange = onQueryChange,
            onSearch = onQuerySubmit,
            active = isSearchActive,
            onActiveChange = onActiveChange,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_search_24),
                    contentDescription = "Search Icon",
                )
            },
            placeholder = { Text(text = "Search") }
        ) {
            when (pageShowing) {
                is SearchResultsPage.Events -> {
                    LimitedEventSearchResultsView(events = quickQueryEventResults)
                }

                is SearchResultsPage.People -> {
                    LimitedUserSearchResultsView(users = quickQueryPeopleResults)
                }
            }
        }
        when (pageShowing) {
            is SearchResultsPage.Events -> {
                UserSearchResultsView(userFlow = pagingUserResults)
            }

            is SearchResultsPage.People -> {
                EventSearchResultsView(eventsFlow = pagingEventsResults)
            }
        }
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
