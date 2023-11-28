package com.alth.events.ui.features.search.users

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.alth.events.database.models.users.PublicUserEntity
import com.alth.events.ui.components.GenericLazyPager
import com.alth.events.ui.components.UserHorizontalBarItem

@Composable
fun LazyListSearchUserResult(
    modifier: Modifier = Modifier,
    users: LazyPagingItems<PublicUserEntity>,
) {
    GenericLazyPager(items = users, modifier = modifier) {
        Column(Modifier.padding(bottom = 12.dp)) {
            UserHorizontalBarItem(
                name = it.name,
                profilePictureUrl = it.profilePictureUrl,
                trailingText = { it }
            )
            Divider(thickness = 3.dp)
        }
    }
}

@Composable
fun LimitedLazyListSearchUserResult(
    modifier: Modifier = Modifier,
    users: List<PublicUserEntity>,
    lazyListState: LazyListState = rememberLazyListState(),
) {
    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        LazyColumn(
            state = lazyListState
        ) {
            items(users.size) { userId ->
                val user = users[userId]
                Column(Modifier.padding(bottom = 12.dp)) {
                    UserHorizontalBarItem(
                        name = user.name,
                        profilePictureUrl = user.profilePictureUrl,
                        trailingText = { it }
                    )
                    Divider(thickness = 3.dp)
                }
            }
        }
    }
}

