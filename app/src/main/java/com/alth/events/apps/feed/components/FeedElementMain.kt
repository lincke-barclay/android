package com.alth.events.apps.feed.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.alth.events.data.feed.models.Event
import com.alth.events.ui.util.toUiString

@Composable
fun FeedElementMain(
    event: Event,
) {
    Column {
        Text("Title: ")
        Text(event.title)
        Text("Short Description: ")
        Text(event.shortDescription)
        Text("Long Description: ")
        Text(event.longDescription)
        Text("Created: ")
        Text(event.createdDateTime.toUiString())
        Text("Start Time: ")
        Text(event.startDateTime.toUiString())
        Text("End Time: ")
        Text(event.endDateTime.toUiString())
    }
}
