package com.alth.events.ui.features.myprofile.friends

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.alth.events.R
import com.alth.events.ui.features.common.CancelButton
import com.alth.events.ui.features.common.CheckButton
import com.alth.events.ui.features.common.GenericLazyPager
import com.alth.events.ui.features.myprofile.viewmodels.FriendISentViewModel
import com.alth.events.ui.features.myprofile.viewmodels.FriendsISentPagingViewModel
import com.alth.events.ui.features.myprofile.viewmodels.FriendsSentToMePagingViewModel
import com.alth.events.ui.features.myprofile.viewmodels.MyFriendsPagingViewModel
import com.alth.events.ui.features.users.UserHorizontalBar

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyFriendsMain() {
    var screenShowing by remember {
        mutableStateOf<ProfileScreenPage>(ProfileScreenPage.MyFriends)
    }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            ProfileScreenPage.tabs().forEach { screen ->
                FilterChip(
                    selected = screen == screenShowing,
                    onClick = { screenShowing = screen },
                    label = { Text(screen.label) },
                )
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
        UserHorizontalBar(
            { Text(user.name) },
            user.profilePictureUrl,
        )
    }
}

@Composable
fun FriendsSentToMeScreen(
    friendsSentToMePagingViewModel: FriendsSentToMePagingViewModel = hiltViewModel(),
) {
    val items = friendsSentToMePagingViewModel.pager.collectAsLazyPagingItems()
    GenericLazyPager(items = items) { user ->
        UserHorizontalBar(
            { Text(user.name) },
            user.profilePictureUrl,
        ) {
            Row {
                CheckButton(action = { friendsSentToMePagingViewModel.accept(user.id) })
                CancelButton(action = { friendsSentToMePagingViewModel.decline(user.id) })
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
        UserHorizontalBar(
            { Text(user.name) },
            user.profilePictureUrl,
        ) {
            Row {
                CancelButton(action = { friendsISentPagingViewModel.cancel(user.id) })
            }
        }
    }
}

@Composable
fun FriendISentRow(
    vm: FriendISentViewModel = hiltViewModel()
) {

}