package com.alth.events.ui.features.feed.components.eventitem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.alth.events.R
import com.alth.events.ui.components.UserHorizontalBarItem

@Composable
fun FeedTitle(
    title: String,
    organizerName: String,
    organizerProfilePictureUrl: String?,
    date: String,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        UserHorizontalBarItem(
            organizerName,
            organizerProfilePictureUrl ?: stringResource(id = R.string.default_profile_picture_url),
            trailingText = { "Organizer: $it" }
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier.padding(vertical = 3.dp),
        ) {
            Text("Date: ", style = MaterialTheme.typography.labelSmall)
            Text(
                text = date,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}
