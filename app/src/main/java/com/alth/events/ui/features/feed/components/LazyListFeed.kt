package com.alth.events.ui.features.feed.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.alth.events.ui.components.IndeterminateCircularIndicator
import com.alth.events.ui.features.feed.components.eventitem.FeedItem
import com.alth.events.ui.viewmodels.FeedUiState

@Composable
fun StatefulLazyListFeed(
    lazyListState: LazyListState,
    feedUiState: FeedUiState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.testTag("feedEntryPoint"),
        state = lazyListState
    ) {
        items(feedUiState.events.size) { eventId ->

            val feedCardUIState = feedUiState.events[eventId]

            Column(Modifier.padding(bottom = 12.dp)) {
                FeedItem(
                    feedCardUIState = feedCardUIState,
                )
                Divider(thickness = 3.dp)
            }
        }

        if (feedUiState.loading) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    IndeterminateCircularIndicator()
                }
            }
        }
    }
}

