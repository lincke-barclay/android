package com.alth.events.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alth.events.models.network.users.ingress.PublicUserResponseDto

@Composable
fun UserHorizontalBarItem(
    organizer: PublicUserResponseDto,
    trailingText: (name: String) -> String = { it },
    rightJustifiedContent: @Composable () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            Box(
                modifier = Modifier
                    .padding(end = 12.dp, top = 6.dp, bottom = 6.dp)
            ) {
                /**
                AsyncImage(
                modifier = Modifier
                .size(45.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Black, CircleShape),
                contentScale = ContentScale.Crop,
                model = ImageRequest.Builder(LocalContext.current)
                .data(organizer)
                .build(),
                contentDescription = null,
                )
                 **/
                Text("Profile Pic")
            }
            Text(
                text = trailingText(organizer.name),
                style = MaterialTheme.typography.labelMedium
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
        ) {
            rightJustifiedContent()
        }
    }
}
