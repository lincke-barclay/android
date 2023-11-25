package com.alth.events.ui.components

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : Any> GenericLazyPager(
    items: LazyPagingItems<T>,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
    renderItem: @Composable (T) -> Unit,
) {
    val state = rememberPullRefreshState(
        refreshing = items.loadState.refresh is LoadState.Loading,
        onRefresh = { items.refresh() })
    val context = LocalContext.current

    LaunchedEffect(key1 = items.loadState) {
        if (items.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (items.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        AnimatedVisibility(visible = items.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator()
        }
        LazyColumn(
            modifier = Modifier
                .pullRefresh(state)
                .testTag("feedEntryPoint"),
            state = lazyListState,
        ) {
            items(items.itemCount) { itemId ->
                val item = items[itemId]
                if (item != null) {
                    renderItem(item)
                }
            }
            item {
                if (items.loadState.append is LoadState.Loading) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}