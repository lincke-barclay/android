package com.alth.events.ui.features.feed.models

import com.alth.events.R

sealed class FeedDisplayOption(val displayNameId: Int, val iconId: Int) {

    class List :
        FeedDisplayOption(R.string.feed, R.drawable.baseline_view_day_24) // TODO Find this icon

    class Day : FeedDisplayOption(R.string.day, R.drawable.baseline_calendar_view_day_24)
    class ThreeDay : FeedDisplayOption(
        R.string.three_day,
        R.drawable.baseline_view_column_24
    ) // TODO Find this icon

    class Week : FeedDisplayOption(R.string.week, R.drawable.baseline_calendar_view_week_24)
    class Month : FeedDisplayOption(R.string.month, R.drawable.baseline_calendar_view_month_24)

    companion object {
        val instances = listOf(List(), Day(), ThreeDay(), Week(), Month())
    }
}

