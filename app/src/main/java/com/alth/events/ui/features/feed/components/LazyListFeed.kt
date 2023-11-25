package com.alth.events.ui.features.feed.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.alth.events.database.models.events.derived.FeedEvent
import com.alth.events.ui.components.GenericLazyPager
import com.alth.events.ui.features.feed.components.eventitem.FeedItem

@Composable
fun StatefulLazyListFeed(
    modifier: Modifier,
    events: LazyPagingItems<FeedEvent>,
) {
    GenericLazyPager(
        modifier = modifier,
        items = events,
    ) {
        Column(Modifier.padding(bottom = 12.dp)) {
            FeedItem(
                event = it,
            )
            Divider(thickness = 3.dp)
        }
    }
}

