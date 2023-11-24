package com.alth.events.ui.features.myprofile.calendar.eventitem

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.alth.events.database.models.events.derived.AnonymousEvent
import com.alth.events.ui.util.toUiString
import kotlinx.datetime.Clock

@Composable
fun MyEventItem(
    event: AnonymousEvent,
) {
    Column {
        MyEventTitle(
            title = event.title,
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
        MyEventItem(
            event = AnonymousEvent(
                id = "abc123",
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
