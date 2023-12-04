package com.alth.events.ui.features.feed.viewmodels

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import com.alth.events.data.repositories.paging.events.PagingFeedRepository
import com.alth.events.database.models.events.derived.FeedEvent
import com.alth.events.ui.features.feed.models.FeedDisplayOption
import com.alth.events.ui.features.feed.models.FeedMetaInformationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.Instant
import javax.inject.Inject


@HiltViewModel
class PagingFeedViewModel @Inject constructor(
    private val pagingFeedRepository: PagingFeedRepository,
) : ViewModel() {

    /**
     * Filter parameters
     */
    private val _feedMetaState = MutableStateFlow(FeedMetaInformationState.initial())
    val feedMetaState = _feedMetaState.asStateFlow()

    /**
     * Actual paging data
     */
    private val _eventPagerFlow = MutableStateFlow(derivePager().flow)
    val eventPagerFlow = _eventPagerFlow.asStateFlow()

    /**
     * Derives pager from ui parameters
     */
    private fun derivePager(): Pager<Int, FeedEvent> {
        return pagingFeedRepository.getFeedEventFlow(
            _feedMetaState.value.feedEventQuery,
        )
    }

    fun refresh() {
        _eventPagerFlow.value = derivePager().flow
    }

    fun onToggleMyFriendsCheck(newValue: Boolean) {
        _feedMetaState.value = _feedMetaState.value.copy(
            feedEventQuery = _feedMetaState.value.feedEventQuery.copy(
                includeMyFriendsEvents = newValue,
            )
        )
    }

    fun onToggleInvitedCheck(newValue: Boolean) {
        _feedMetaState.value = _feedMetaState.value.copy(
            feedEventQuery = _feedMetaState.value.feedEventQuery.copy(
                includeEventsImInvitedTo = newValue,
            )
        )
    }

    fun onTogglePublicCheck(newValue: Boolean) {
        _feedMetaState.value = _feedMetaState.value.copy(
            feedEventQuery = _feedMetaState.value.feedEventQuery.copy(
                includePublicEvents = newValue,
            )
        )
    }

    fun onChangeDisplayOption(newDisplayOption: FeedDisplayOption) {
        _feedMetaState.value = _feedMetaState.value.copy(
            displayMode = newDisplayOption,
        )
    }

    fun onChangeStartDateTime(newInstant: Instant) {
        _feedMetaState.value = _feedMetaState.value.copy(
            feedEventQuery = _feedMetaState.value.feedEventQuery.copy(
                fromDateTimeInclusive = newInstant
            )
        )
    }

    fun onChangeEndDateTime(newInstant: Instant) {
        _feedMetaState.value = _feedMetaState.value.copy(
            feedEventQuery = _feedMetaState.value.feedEventQuery.copy(
                toDateTimeInclusive = newInstant
            )
        )
    }
}
