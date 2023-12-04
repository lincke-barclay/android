package com.alth.events.ui.features.search.events

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.alth.events.database.models.events.derived.SearchEventResult
import com.alth.events.ui.features.common.GenericLazyPager
import com.alth.events.ui.features.search.events.eventitem.SearchEventItem

@Composable
fun LazyListSearchEventsResult(
    modifier: Modifier = Modifier,
    events: LazyPagingItems<SearchEventResult>,
) {
    GenericLazyPager(items = events, modifier = modifier) {
        Column(Modifier.padding(bottom = 12.dp)) {
            SearchEventItem(
                event = it,
            )
            Divider(thickness = 3.dp)
        }
    }
}

@Composable
fun LimitedLazyListSearchEventsResult(
    modifier: Modifier = Modifier,
    events: List<SearchEventResult>,
    lazyListState: LazyListState = rememberLazyListState(),
) {
    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        LazyColumn(
            state = lazyListState
        ) {
            items(events.size) { eventId ->
                val event = events[eventId]
                Column(Modifier.padding(bottom = 12.dp)) {
                    SearchEventItem(
                        event = event,
                    )
                    Divider(thickness = 3.dp)
                }
            }
        }
    }
}

