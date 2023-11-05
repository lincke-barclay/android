package com.alth.events.ui.features.feed.components.eventitem

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.alth.events.ui.viewmodels.FeedCardUIState

@Composable
fun FeedItem(
    feedCardUIState: FeedCardUIState,
) {
    val event = feedCardUIState.feedEvent

    Column {
        FeedTitle(
            title = event.title,
            organizer = event.organizer,
            date = event.startDateTime.toString(),
        )
        FeedItemContent(
            content = event.longDescription,
            imageUrls = event.images.map { it.url },
        )
    }
}
