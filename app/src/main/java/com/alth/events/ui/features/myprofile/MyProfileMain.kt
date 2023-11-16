package com.alth.events.ui.features.myprofile

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.alth.events.models.domain.authentication.AuthenticationState
import com.alth.events.ui.viewmodels.AuthenticationViewModel
import com.alth.events.ui.viewmodels.ProfileUIState
import com.alth.events.ui.viewmodels.ProfileViewModel

@Composable
fun MyProfileMain(
    pvm: ProfileViewModel = hiltViewModel(),
    avm: AuthenticationViewModel = hiltViewModel(),
) {
    val uiState by pvm.uiState.collectAsState()
    val user by avm.currentlySignedInState.collectAsState()

    user.let {
        when (it) {
            is AuthenticationState.UserOk -> {
                ProfileMainStateless(
                    uiState = uiState,
                    onSignOut = pvm::signOut,
                    signedInUser = it,
                    consumeFriend = pvm::consumeFriendIndex,
                )
            }

            else -> {}
        }
    }
}

@Composable
fun ProfileMainStateless(
    uiState: ProfileUIState,
    onSignOut: () -> Unit,
    signedInUser: AuthenticationState.UserOk,
    consumeFriend: (Int) -> Unit,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .testTag("profileEntryPoint"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        /**
        AsyncImage(
        modifier = Modifier
        .size(128.dp)
        .clip(CircleShape)
        .border(2.dp, Color.Black, CircleShape),
        contentScale = ContentScale.Crop,
        model = myProfileState.user.publicUser.profilePictureUrl.toString(),
        contentDescription = "Translated description of what the image contains"
        )
         **/
        Text(
            text = signedInUser.name,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
        Button(onClick = onSignOut) {
            Text("Sign out")
        }
        CustomTabRow(
            friendsContent = {
                MyFriendsList(
                    friends = uiState.myConfirmedFriends,
                    pendingFriendsFromMe = uiState.pendingFriendsISent,
                    pendingFriendsToMe = uiState.pendingFriendsSentToMe,
                )
            },
            calendarContent = {
                Text("Calendar")
            },
        )
    }
}
