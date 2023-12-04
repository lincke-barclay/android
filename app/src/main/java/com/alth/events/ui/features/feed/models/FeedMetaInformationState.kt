package com.alth.events.ui.features.feed.models

import com.alth.events.models.events.FeedEventQuery

data class FeedMetaInformationState(
    val displayMode: FeedDisplayOption,
    val feedEventQuery: FeedEventQuery,
) {
    companion object {
        fun initial() = FeedMetaInformationState(
            displayMode = FeedDisplayOption.List(),
            feedEventQuery = FeedEventQuery(),
        )
    }
}
