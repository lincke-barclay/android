package com.alth.events.ui.features.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import com.alth.events.models.network.users.ingress.PublicUserResponseDto
import com.alth.events.ui.features.search.components.SearchResultUserView

@Composable
fun FriendsSearchStateless(
    friendResults: List<PublicUserResponseDto>,
    lazyListState: LazyListState = rememberLazyListState(),
    consumeItem: (Int) -> Unit,
    onClickOnUser: (PublicUserResponseDto) -> Unit,
) {
    Column {
        LazyColumn(
            state = lazyListState
        ) {
            items(friendResults.size) { friendId ->
                SearchResultUserView(
                    user = friendResults[friendId],
                    onClickOnUser = onClickOnUser,
                )
            }
        }
    }
}

