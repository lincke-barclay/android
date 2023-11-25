package com.alth.events.ui.features.myprofile.friends

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.alth.events.R
import com.alth.events.ui.components.GenericLazyPager
import com.alth.events.ui.components.UserHorizontalBarItem
import com.alth.events.ui.features.myprofile.viewmodels.FriendsISentPagingViewModel
import com.alth.events.ui.features.myprofile.viewmodels.FriendsSentToMePagingViewModel
import com.alth.events.ui.features.myprofile.viewmodels.MyFriendsPagingViewModel

sealed class ProfileScreenPage(
    val label: String,
    val iconId: Int,
) {
    data object MyFriends : ProfileScreenPage(
        "Confirmed",
        R.drawable.baseline_check_24,
    )

    data object ISent : ProfileScreenPage(
        "Sent",
        R.drawable.baseline_send_24,
    )

    data object SentToMe : ProfileScreenPage(
        "Pending",
        R.drawable.baseline_change_circle_24,
    )

    companion object {
        fun tabs() = listOf(MyFriends, ISent, SentToMe)
    }
}

@Composable
fun MyFriendsMain() {
    var screenShowing by remember {
        mutableStateOf<ProfileScreenPage>(ProfileScreenPage.MyFriends)
    }

    Column {
        TabRow(selectedTabIndex = ProfileScreenPage.tabs().indexOf(screenShowing)) {
            ProfileScreenPage.tabs().forEach { screen ->
                Tab(
                    text = { Text(text = screen.label) },
                    selected = screen == screenShowing,
                    onClick = { screenShowing = screen })
            }
        }

        when (screenShowing) {
            ProfileScreenPage.ISent -> FriendsISentScreen()
            ProfileScreenPage.MyFriends -> MyFriendsScreen()
            ProfileScreenPage.SentToMe -> FriendsSentToMeScreen()
        }
    }
}

@Composable
fun MyFriendsScreen(
    myFriendsPagingViewModel: MyFriendsPagingViewModel = hiltViewModel(),
) {
    val items = myFriendsPagingViewModel.pager.collectAsLazyPagingItems()
    GenericLazyPager(items = items) { user ->
        UserHorizontalBarItem(
            user.name,
            user.profilePictureUrl,
            trailingText = { "Organizer: $it" }
        )
    }
}

@Composable
fun FriendsSentToMeScreen(
    friendsSentToMePagingViewModel: FriendsSentToMePagingViewModel = hiltViewModel(),
) {
    val items = friendsSentToMePagingViewModel.pager.collectAsLazyPagingItems()
    GenericLazyPager(items = items) { user ->
        UserHorizontalBarItem(
            user.name,
            user.profilePictureUrl,
            trailingText = { it }
        ) {
            Row {
                Button(onClick = { friendsSentToMePagingViewModel.accept(user.id) }) {
                    Text("Accept")
                }
                Button(onClick = { friendsSentToMePagingViewModel.decline(user.id) }) {
                    Text("Decline")
                }
            }
        }
    }
}

@Composable
fun FriendsISentScreen(
    friendsISentPagingViewModel: FriendsISentPagingViewModel = hiltViewModel(),
) {
    val items = friendsISentPagingViewModel.pager.collectAsLazyPagingItems()
    GenericLazyPager(items = items) { user ->
        UserHorizontalBarItem(
            user.name,
            user.profilePictureUrl,
            trailingText = { "Organizer: $it" }
        )
    }
}
