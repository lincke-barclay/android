package com.alth.events.ui.features.feed.models

data class FilterState(
    val includeMine: Boolean,
    val includeMyFriends: Boolean,
    val includeInvited: Boolean,
    val includePublic: Boolean,
)

