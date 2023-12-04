package com.alth.events.ui.features.myprofile.calendar.eventitem

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alth.events.ui.features.common.FadedExpandableText

@Composable
fun FeedItemContent(
    content: String,
    imageUrls: List<String>,
) {
    Column {
        Text("Images are here")
        Text(
            modifier = Modifier.padding(vertical = 10.dp),
            text = "Description:",
            style = MaterialTheme.typography.labelMedium,
        )
        Divider()

        Box(modifier = Modifier.animateContentSize()) {
            FadedExpandableText(content = content)
        }
    }
}
