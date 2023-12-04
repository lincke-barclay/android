package com.alth.events.ui.features.feed

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.paging.compose.LazyPagingItems
import com.alth.events.R
import com.alth.events.database.models.events.derived.FeedEvent
import com.alth.events.ui.features.common.CircularProfilePictureIcon
import com.alth.events.ui.features.common.SearchButton
import com.alth.events.ui.features.feed.models.FeedDisplayOption


/**
 * Inner Content
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedOuterDrawerContent(
    feedEvents: LazyPagingItems<FeedEvent>,
    navigateToNewEvent: () -> Unit,
    navigateToProfile: () -> Unit,
    navigateToSearch: () -> Unit,
    openDrawer: () -> Unit,
    feedDisplayOption: FeedDisplayOption,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navigateToNewEvent() }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    contentDescription = "foo"
                )
            }
        },
        topBar = {
            TopAppBar(
                title = { Text("Communeo") },
                navigationIcon = {
                    IconButton(onClick = openDrawer) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_menu_24),
                            contentDescription = "Toggle nav drawer on feed",
                        )
                    }
                },
                actions = {
                    SearchButton(action = navigateToSearch)
                    CircularProfilePictureIcon(action = navigateToProfile)
                },
            )
        },
    ) { padding ->
        FeedInnerScaffoldContent(
            modifier = Modifier.padding(padding),
            feedEvents = feedEvents,
            displayOption = feedDisplayOption,
        )
    }
}
