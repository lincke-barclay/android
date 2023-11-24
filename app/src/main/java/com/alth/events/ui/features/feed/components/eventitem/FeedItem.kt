package com.alth.events.ui.features.feed.components.eventitem

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.alth.events.database.models.events.derived.FeedEvent
import com.alth.events.ui.util.toUiString
import kotlinx.datetime.Clock

@Composable
fun FeedItem(
    event: FeedEvent,
) {
    Column {
        FeedTitle(
            title = event.title,
            organizerName = event.ownerName,
            organizerProfilePictureUrl = event.ownerProfilePictureUrl,
            date = event.startDateTime.toUiString(LocalContext.current),
        )
        FeedItemContent(
            content = event.longDescription,
            imageUrls = listOf(),
        )
    }
}

@Preview
@Composable
fun FeedItemPreview() {
    MaterialTheme {
        FeedItem(
            event = FeedEvent(
                id = "abc123",
                ownerName = "Theo Lincke",
                ownerProfilePictureUrl = "https://picsum.photos/200",
                startDateTime = Clock.System.now(),
                endDateTime = Clock.System.now(),
                title = "This is an Event",
                shortDescription = "This is a fun event short description",
                longDescription = "Lorem This is a fun event short " +
                        "description This is a fun event short description " +
                        "This is a fun event short description This is a " +
                        "fun event short description This is a fun event " +
                        "short description",
            )
        )
    }
}
