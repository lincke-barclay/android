package com.alth.events.ui.features.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.alth.events.models.network.events.ingress.MinimalEventListResponseDto

@Composable
fun EventsSearchStateless(
    eventResults: MinimalEventListResponseDto,
    lazyListState: LazyListState = rememberLazyListState(),
    consumeItem: (Int) -> Unit,
) {

    Column {
        LazyColumn(
            state = lazyListState
        ) {
            items(eventResults.publicEvents.size) { friendId ->
                Text("Friend: ${eventResults.publicEvents[friendId].title}")
            }
        }
    }
}

