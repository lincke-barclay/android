package com.alth.events.ui.features.search.events.viewmodels

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.alth.events.data.repositories.paging.EventQueryPagerRepository
import com.alth.events.database.models.events.derived.SearchEventResult
import com.alth.events.models.domain.events.PublicEventQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

@HiltViewModel
class PagingSearchEventsAfterQuerySubmitViewModel @Inject constructor(
    private val eventQueryPagerRepository: EventQueryPagerRepository,
) : ViewModel() {
    private val _searchEventsFlow =
        MutableStateFlow<Flow<PagingData<SearchEventResult>>>(emptyFlow())
    val searchEventsFlow = _searchEventsFlow.asStateFlow()

    // TODO
    fun onQuerySubmit(newQuery: String) {
        _searchEventsFlow.value =
            eventQueryPagerRepository.searchPager(PublicEventQuery(titleContainsIC = newQuery))
    }
}
