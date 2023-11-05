package com.alth.events.ui.features.search.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.alth.events.models.domain.users.PublicUser

@Composable
fun SearchResultUserView(
    user: PublicUser,
    sendFriendRequest: (user: PublicUser) -> Unit,
) {
    Row {
        Text("Name: ")
        Text(user.firstName)
        Button(onClick = { sendFriendRequest(user) }) {
            Text("Request Friend")
        }
    }
}
