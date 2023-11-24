package com.alth.events.ui.features.myprofile.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.alth.events.ui.features.myprofile.calendar.viewmodels.MyEventsViewModel

@Composable
fun MyEventsMain(
    myEventsViewModel: MyEventsViewModel = hiltViewModel(),
) {
    val eventsFlow by myEventsViewModel.myEventsFlow.collectAsState()
    StatefulLazyListMyEvents(
        events = eventsFlow.collectAsLazyPagingItems(),
    )
}