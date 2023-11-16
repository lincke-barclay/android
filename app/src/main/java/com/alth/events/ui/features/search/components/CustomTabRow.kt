package com.alth.events.ui.features.search.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import com.alth.events.R

enum class SearchTab(val title: String, val iconId: Int) {
    Events("Events", R.drawable.baseline_event_24),
    Friends("Friends", R.drawable.baseline_people_24),
}

@Composable
fun CustomTabRow(
    eventContent: @Composable () -> Unit,
    friendsContent: @Composable () -> Unit,
    onEventsClicked: () -> Unit = {},
    onFriendsClicked: () -> Unit = {},
) {
    var state by remember { mutableStateOf(SearchTab.Events) }

    Column {
        TabRow(selectedTabIndex = SearchTab.values().indexOf(state)) {
            CustomTabTitle(
                text = SearchTab.Events.title,
                iconId = SearchTab.Events.iconId,
                selected = state == SearchTab.Events,
            ) {
                onEventsClicked()
                state = SearchTab.Events
            }

            CustomTabTitle(
                text = SearchTab.Friends.title,
                iconId = SearchTab.Friends.iconId,
                selected = state == SearchTab.Friends,
            ) {
                onFriendsClicked()
                state = SearchTab.Friends
            }
        }
        AnimatedContent(
            targetState = state,
            label = "Profile content animation"
        ) { state ->
            when (state) {
                SearchTab.Events -> eventContent()
                SearchTab.Friends -> friendsContent()
            }
        }
    }
}

@Composable
fun CustomTabTitle(
    text: String,
    iconId: Int,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Tab(
        selected = selected,
        onClick = onClick,
        text = {
            Row {
                Icon(
                    painter = painterResource(id = iconId),
                    contentDescription = "abcde",
                )
                Text(
                    text = text,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    )
}
