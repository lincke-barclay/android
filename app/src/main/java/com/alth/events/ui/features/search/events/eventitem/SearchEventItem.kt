package com.alth.events.ui.features.search.events.eventitem

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.alth.events.database.models.events.derived.SearchEventResult
import com.alth.events.ui.util.toUiString
import kotlinx.datetime.Clock

@Composable
fun SearchEventItem(
    event: SearchEventResult,
) {
    Column {
        SearchEventTitle(
            title = event.title,
            date = event.startDateTime.toUiString(LocalContext.current),
        )
        SearchEventContent(
            content = event.longDescription,
            imageUrls = listOf(),
        )
    }
}

@Preview
@Composable
fun FeedItemPreview() {
    MaterialTheme {
        SearchEventItem(
            event = SearchEventResult(
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
                ownerName = "Theo",
            )
        )
    }
}
