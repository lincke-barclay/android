package com.alth.events.ui.features.myprofile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.alth.events.ui.features.myprofile.calendar.MyEventsMain
import com.alth.events.ui.features.myprofile.friends.MyFriendsMain

enum class ProfileTabOptions(
    val title: String,
) {
    Calendar("Calendar"),
    Friends("Friends"),
    MyEvents("My Events"),
}

@Composable
fun MyProfileInnerScaffoldContent(
    modifier: Modifier,
) {
    var selectedOption by remember { mutableStateOf(ProfileTabOptions.Calendar) }
    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        ProfileTabRow(
            selectedOption = selectedOption,
            onChangeSelectedOption = { selectedOption = it },
        )
        when (selectedOption) {
            ProfileTabOptions.Calendar -> {
                Text("Calendar")
            }

            ProfileTabOptions.Friends -> {
                MyFriendsMain()
            }

            ProfileTabOptions.MyEvents -> {
                MyEventsMain()
            }
        }
    }


}


@Composable
fun ProfileTabRow(
    selectedOption: ProfileTabOptions,
    onChangeSelectedOption: (ProfileTabOptions) -> Unit,
) {
    TabRow(selectedTabIndex = ProfileTabOptions.values().indexOf(selectedOption)) {
        ProfileTabOptions.values().forEach { option ->
            Tab(
                selected = option == selectedOption,
                onClick = { onChangeSelectedOption(option) }) {
                Text(
                    text = option.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}
