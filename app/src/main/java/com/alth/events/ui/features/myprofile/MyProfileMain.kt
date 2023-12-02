package com.alth.events.ui.features.myprofile

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.alth.events.R
import com.alth.events.networking.models.users.ingress.PrivateUserResponseDto
import com.alth.events.ui.features.myprofile.calendar.MyEventsMain
import com.alth.events.ui.features.myprofile.friends.MyFriendsMain
import com.alth.events.ui.features.myprofile.viewmodels.ProfileViewModel
import com.alth.events.ui.layouts.BottomAppNavBar
import com.alth.events.ui.navigation.BottomAppBarRoute


@Composable
fun MyProfile(
    pvm: ProfileViewModel = hiltViewModel(),
    navigateToFeed: () -> Unit,
    navigateToSearch: () -> Unit,
) {
    val currentlySignedInUser by pvm.uiState.collectAsState()
    currentlySignedInUser?.let { signedInUser ->
        Scaffold(
            bottomBar = {
                BottomAppNavBar(
                    navigateToFeed = navigateToFeed,
                    navigateToProfile = { /* do nothing */ },
                    navigateToSearch = navigateToSearch,
                    currentRoute = BottomAppBarRoute.Profile,
                )
            },
        ) { innerPadding ->
            ProfileMainStateless(
                onSignOut = { pvm.signOut() },
                signedInUser = signedInUser,
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}

@Composable
fun ProfileMainStateless(
    onSignOut: () -> Unit,
    signedInUser: PrivateUserResponseDto,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .size(128.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Black, CircleShape),
            contentScale = ContentScale.Crop,
            model = signedInUser.profilePictureUrl
                ?: stringResource(id = R.string.default_profile_picture_url),
            contentDescription = "Translated description of what the image contains"
        )
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
                MyFriendsMain()
            },
            calendarContent = {
                MyEventsMain()
            },
        )
    }
}
