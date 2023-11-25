package com.alth.events.ui.features.feed

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.paging.compose.collectAsLazyPagingItems
import com.alth.events.R
import com.alth.events.ui.features.feed.components.StatefulLazyListFeed
import com.alth.events.ui.features.feed.viewmodels.FeedMainViewModel

@Composable
fun FeedMain(
    feedMainViewModel: FeedMainViewModel,
    navigateToNewEvent: () -> Unit,
) {
    val feedEvents = feedMainViewModel.eventPagingFlow.collectAsLazyPagingItems()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navigateToNewEvent() }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    contentDescription = "foo"
                )
            }
        }) { padding ->
        StatefulLazyListFeed(
            events = feedEvents,
            modifier = Modifier.padding(padding),
        )
    }
}
