package com.alth.events.ui.features.search.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.alth.events.models.domain.events.SuggestedEvent

@Composable
fun SearchResultEventView(event: SuggestedEvent) {
    Row {
        Text("Event: ")
        Column {
            Text(event.title)
            Text("Organizer: $event.organizerName")
            Text(event.shortDescription)
        }
    }
}
