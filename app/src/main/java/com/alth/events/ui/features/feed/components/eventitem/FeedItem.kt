package com.alth.events.ui.features.feed.components.eventitem

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.alth.events.ui.viewmodels.FeedCardUIState

@Composable
fun FeedItem(
    feedCardUIState: FeedCardUIState,
) {
    val event = feedCardUIState.feedEvent
    val organizer = feedCardUIState.owner

    Column {
        FeedTitle(
            title = event.title,
            organizer = organizer,
            date = event.startDateTime.toString(),
        )
        FeedItemContent(
            content = event.longDescription,
            imageUrls = listOf(),
        )
    }
}
