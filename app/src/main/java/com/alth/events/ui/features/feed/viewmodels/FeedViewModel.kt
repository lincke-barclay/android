package com.alth.events.ui.features.feed.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.alth.events.data.repositories.paging.events.PagingFeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedMainViewModel @Inject constructor(
    pagingFeedRepository: PagingFeedRepository,
) : ViewModel() {
    val eventPagingFlow = pagingFeedRepository
        .feedEventFlow
        .cachedIn(viewModelScope)
}
