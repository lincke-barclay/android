package com.alth.events.apps.feed

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.alth.events.apps.feed.components.FeedElementMain
import com.alth.events.apps.feed.viewmodels.FeedMainViewModel
import com.alth.events.data.feed.models.Event
import com.alth.events.ui.components.IndeterminateCircularIndicator

@Composable
fun FeedMain(
    feedMainViewModel: FeedMainViewModel,
    navigateToNewEvent: () -> Unit,
) {
    val uiState by feedMainViewModel.uiState.collectAsState()
    FeedMainStateless(
        events = uiState.events,
        navigateToNewEvent = navigateToNewEvent,
        feedMainViewModel::refresh,
        uiState.loading,
    )
}

@Composable
fun FeedMainStateless(
    events: List<Event>,
    navigateToNewEvent: () -> Unit,
    refreshEvents: () -> Unit,
    loading: Boolean,
) {
    Column {
        Button(onClick = navigateToNewEvent) {
            Text("New Event")
        }
        Button(onClick = refreshEvents) {
            Text("Refresh")
        }
        if(loading){
            IndeterminateCircularIndicator()
        }
        events.forEach {
            FeedElementMain(event = it)
            Divider()
        }
    }
}