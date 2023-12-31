package com.alth.events.ui.features.myprofile.calendar

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.alth.events.database.models.events.derived.AnonymousEvent
import com.alth.events.ui.features.common.IndeterminateCircularIndicator
import com.alth.events.ui.features.myprofile.calendar.eventitem.MyEventItem
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatefulLazyListMyEvents(
    events: LazyPagingItems<AnonymousEvent>,
    lazyListState: LazyListState = rememberLazyListState(),
    modifier: Modifier = Modifier,
) {

    val context = LocalContext.current

    val state = rememberPullRefreshState(
        refreshing = events.loadState.refresh is LoadState.Loading,
        onRefresh = { events.refresh() })

    LaunchedEffect(key1 = events.loadState) {
        if (events.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (events.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        AnimatedVisibility(visible = events.loadState.refresh is LoadState.Loading) {
            IndeterminateCircularIndicator()
        }
        LazyColumn(
            modifier = Modifier
                .pullRefresh(state)
                .testTag("feedEntryPoint"),
            state = lazyListState
        ) {
            items(events.itemCount) { eventId ->
                val event = events[eventId]
                if (event != null) {
                    Column(Modifier.padding(bottom = 12.dp)) {
                        MyEventItem(
                            event = event,
                        )
                        Divider(thickness = 3.dp)
                    }
                }
            }
            item {
                if (events.loadState.append is LoadState.Loading) {
                    IndeterminateCircularIndicator()
                }
            }
        }
    }
}

