package com.alth.events.ui.features.myprofile

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

enum class ProfileTab(val title: String, val iconId: Int) {
    Calendar("Calendar", R.drawable.baseline_event_24),
    Friends("Friends", R.drawable.baseline_people_24),
}

@Composable
fun CustomTabRow(
    calendarContent: @Composable () -> Unit,
    friendsContent: @Composable () -> Unit,
    onCalendarClicked: () -> Unit = {},
    onFriendsClicked: () -> Unit = {},
) {
    var state by remember { mutableStateOf(ProfileTab.Calendar) }

    Column {
        TabRow(selectedTabIndex = ProfileTab.values().indexOf(state)) {
            CustomTabTitle(
                text = ProfileTab.Calendar.title,
                iconId = ProfileTab.Calendar.iconId,
                selected = state == ProfileTab.Calendar,
            ) {
                onCalendarClicked()
                state = ProfileTab.Calendar
            }

            CustomTabTitle(
                text = ProfileTab.Friends.title,
                iconId = ProfileTab.Friends.iconId,
                selected = state == ProfileTab.Friends,
            ) {
                onFriendsClicked()
                state = ProfileTab.Friends
            }
        }
        AnimatedContent(
            targetState = state,
            label = "Profile content animation"
        ) { state ->
            when (state) {
                ProfileTab.Calendar -> calendarContent()
                ProfileTab.Friends -> friendsContent()
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
