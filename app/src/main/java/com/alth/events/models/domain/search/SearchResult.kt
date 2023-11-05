package com.alth.events.models.domain.search

import com.alth.events.models.domain.events.SuggestedEvent
import com.alth.events.models.domain.users.PublicUser

sealed interface SearchResult {
    data class User(val user: PublicUser) : SearchResult
    data class Event(val event: SuggestedEvent) : SearchResult
}