package com.alth.events.ui.features.events

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.alth.events.ui.features.common.FadedExpandableText
import com.alth.events.ui.features.users.UserHorizontalBar
import com.alth.events.ui.util.toUiString
import kotlinx.datetime.Instant

@Composable
fun EventBoxDisplay(
    ownerName: String,
    ownerProfilePictureUrl: String,
    startDateTime: Instant,
    endDateTime: Instant,
    title: String,
    longDescription: String,
) {
    Column {
        EventBoxTitle(
            title = title,
            ownerName = ownerName,
            startDateTime = startDateTime,
            endDateTime = endDateTime,
            ownerProfilePictureUrl = ownerProfilePictureUrl,
        )
        EventBoxContent(content = longDescription)
    }
}

@Composable
fun EventBoxTitle(
    title: String,
    ownerName: String,
    ownerProfilePictureUrl: String,
    modifier: Modifier = Modifier,
    startDateTime: Instant,
    endDateTime: Instant,
) {
    Column(
        modifier = modifier,
    ) {
        UserHorizontalBar(
            label = { Text("Organizer: $ownerName") },
            profilePictureUrl = ownerProfilePictureUrl,
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier.padding(vertical = 3.dp),
        ) {
            Text("From: ", style = MaterialTheme.typography.labelSmall)
            Text(
                text = startDateTime.toUiString(LocalContext.current),
                style = MaterialTheme.typography.labelSmall
            )
        }
        Row(
            modifier = Modifier.padding(vertical = 3.dp),
        ) {
            Text("To: ", style = MaterialTheme.typography.labelSmall)
            Text(
                text = endDateTime.toUiString(LocalContext.current),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Composable
fun EventBoxContent(
    content: String,
) {
    Column {
        Text("Images go here")
        Text(
            text = "Description",
            modifier = Modifier.padding(vertical = 10.dp),
            style = MaterialTheme.typography.labelMedium,
        )
        Divider()
        Box(modifier = Modifier.animateContentSize()) {
            FadedExpandableText(content = content)
        }
    }
}