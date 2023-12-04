package com.alth.events.ui.features.feed

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.alth.events.ui.features.feed.viewmodels.PagingFeedViewModel
import kotlinx.coroutines.launch

/**
 * Feed Entry point - Renders a Modal Navigation Drawer with content inside
 */
@Composable
fun Feed(
    pagingFeedViewModel: PagingFeedViewModel = hiltViewModel(),
    navigateToNewEvent: () -> Unit,
    navigateToProfile: () -> Unit,
    navigateToSearch: () -> Unit,
) {
    val eventPagerFlow by pagingFeedViewModel.eventPagerFlow.collectAsState()
    val feedEvents = eventPagerFlow.collectAsLazyPagingItems()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val feedMetaState by pagingFeedViewModel.feedMetaState.collectAsState()

    ModalNavigationDrawer(
        modifier = Modifier
            .fillMaxSize(),
        drawerState = drawerState,
        drawerContent = {
            FeedInnerDrawerContent(
                feedMetaState = feedMetaState,

                /**
                 * Callbacks
                 */
                onClickNewDisplayOption = pagingFeedViewModel::onChangeDisplayOption,
                refresh = pagingFeedViewModel::refresh,
                toggleIncludeInvited = pagingFeedViewModel::onToggleInvitedCheck,
                toggleIncludeMyFriendsEvents = pagingFeedViewModel::onToggleMyFriendsCheck,
                toggleIncludePublicEvents = pagingFeedViewModel::onTogglePublicCheck,
                changeFromDateTime = pagingFeedViewModel::onChangeStartDateTime,
                changeToDateTime = pagingFeedViewModel::onChangeEndDateTime,
            )
        },
    ) {
        FeedOuterDrawerContent(
            feedEvents = feedEvents,
            navigateToNewEvent = navigateToNewEvent,
            navigateToProfile = navigateToProfile,
            navigateToSearch = navigateToSearch,
            openDrawer = {
                scope.launch {
                    drawerState.open()
                }
            },
            feedMetaState.displayMode,
        )
    }
}
