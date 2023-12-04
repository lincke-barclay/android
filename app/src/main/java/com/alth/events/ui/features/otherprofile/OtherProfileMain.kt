package com.alth.events.ui.features.otherprofile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.alth.events.networking.models.users.ingress.PublicUserResponseDto

@Composable
fun OtherProfile(
    userProfileViewModel: OtherProfileViewModel,
) {
    val uiState by userProfileViewModel.uiState.collectAsState()

    uiState?.let {
        OtherUserMainStateless(
            user = it,
            sendFriendRequest = userProfileViewModel::sendFriendRequest
        )
    }
}

@Composable
fun OtherUserMainStateless(
    user: PublicUserResponseDto,
    sendFriendRequest: () -> Unit,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .testTag("profileEntryPoint"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = user.name,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
        Button(onClick = sendFriendRequest) {
            Text("Add Friend")
        }
    }
}
