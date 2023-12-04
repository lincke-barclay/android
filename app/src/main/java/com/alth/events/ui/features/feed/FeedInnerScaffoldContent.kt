package com.alth.events.ui.features.feed

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.alth.events.database.models.events.derived.FeedEvent
import com.alth.events.ui.features.feed.displaymodes.day.FeedDayDisplay
import com.alth.events.ui.features.feed.displaymodes.list.FeedListDisplay
import com.alth.events.ui.features.feed.displaymodes.month.FeedMonthDisplay
import com.alth.events.ui.features.feed.displaymodes.threeday.FeedThreeDayDisplay
import com.alth.events.ui.features.feed.displaymodes.week.FeedWeekDisplay
import com.alth.events.ui.features.feed.models.FeedDisplayOption

@Composable
fun FeedInnerScaffoldContent(
    feedEvents: LazyPagingItems<FeedEvent>,
    modifier: Modifier = Modifier,
    displayOption: FeedDisplayOption,
) {
    when (displayOption) {
        is FeedDisplayOption.Day -> {
            FeedDayDisplay()
        }

        is FeedDisplayOption.List -> {
            FeedListDisplay(
                events = feedEvents,
                modifier = modifier,
            )
        }

        is FeedDisplayOption.Month -> {
            FeedMonthDisplay()
        }

        is FeedDisplayOption.ThreeDay -> {
            FeedThreeDayDisplay()
        }

        is FeedDisplayOption.Week -> {
            FeedWeekDisplay()
        }
    }
}

