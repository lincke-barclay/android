package com.alth.events.ui.features.feed.displaymodes.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.alth.events.database.models.events.derived.FeedEvent
import com.alth.events.ui.features.common.GenericLazyPager
import com.alth.events.ui.features.events.EventBoxDisplay

@Composable
fun FeedListDisplay(
    modifier: Modifier,
    events: LazyPagingItems<FeedEvent>,
) {
    GenericLazyPager(
        modifier = modifier,
        items = events,
    ) {
        Column(Modifier.padding(bottom = 12.dp)) {
            EventBoxDisplay(
                ownerName = it.ownerName,
                ownerProfilePictureUrl = it.ownerProfilePictureUrl,
                startDateTime = it.startDateTime,
                endDateTime = it.endDateTime,
                title = it.title,
                longDescription = it.longDescription,
            )
            Divider(thickness = 3.dp)
        }
    }
}
