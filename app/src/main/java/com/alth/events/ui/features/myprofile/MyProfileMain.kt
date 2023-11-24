package com.alth.events.ui.features.myprofile

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.alth.events.networking.models.users.ingress.PrivateUserResponseDto
import com.alth.events.ui.features.myprofile.calendar.MyEventsMain
import com.alth.events.ui.features.myprofile.viewmodels.ProfileViewModel


@Composable
fun MyProfile(
    pvm: ProfileViewModel = hiltViewModel(),
) {
    val currentlySignedInUser by pvm.uiState.collectAsState()
    currentlySignedInUser?.let {
        ProfileMainStateless(
            onSignOut = { pvm.signOut() },
            signedInUser = it,
        )
    }
}

@Composable
fun ProfileMainStateless(
    onSignOut: () -> Unit,
    signedInUser: PrivateUserResponseDto,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .testTag("profileEntryPoint"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .size(128.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Black, CircleShape),
            contentScale = ContentScale.Crop,
            model = signedInUser.profilePictureUrl,
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
                Text("Friends")
            },
            calendarContent = {
                MyEventsMain()
            },
        )
    }
}
