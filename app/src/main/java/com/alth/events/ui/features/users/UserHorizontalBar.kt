package com.alth.events.ui.features.users

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alth.events.R

@Composable
fun UserHorizontalBar(
    label: @Composable () -> Unit,
    profilePictureUrl: String?,
    trailingContent: @Composable () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(modifier = Modifier.padding(horizontal = 10.dp)) {
                AsyncImage(
                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.Black, CircleShape),
                    contentScale = ContentScale.Crop,
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(
                            profilePictureUrl
                                ?: stringResource(id = R.string.default_profile_picture_url)
                        )
                        .build(),
                    contentDescription = null,
                )
            }
            label()
        }
        trailingContent()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTrailing() {
    UserHorizontalBar(
        label = {
            Text("Bob Smith")
        },
        profilePictureUrl = "https://picsum.photos/200",
        trailingContent = {
            Button(onClick = { /*TODO*/ }) {
                Text("Trailing Content")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewNoTrailing() {
    UserHorizontalBar(
        label = {
            Text("Bob Smith")
        },
        profilePictureUrl = "https://picsum.photos/200",
    )
}
