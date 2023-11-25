package com.alth.events.ui.features.myprofile.calendar.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alth.events.data.repositories.paging.events.PagingMyEventsRepository
import com.alth.events.database.models.events.derived.AnonymousEvent
import com.alth.events.models.domain.events.PublicEventQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyEventsViewModel @Inject constructor(
    pagingMyEventsRepository: PagingMyEventsRepository,
) : ViewModel() {
    private val _anonymousEventsFlow = MutableStateFlow<Flow<PagingData<AnonymousEvent>>>(emptyFlow())
    val myEventsFlow = _anonymousEventsFlow.asStateFlow()

    init {
        viewModelScope.launch {
            _anonymousEventsFlow.value = pagingMyEventsRepository.myEventsFlow(
                query = PublicEventQuery.blank()
            ).cachedIn(viewModelScope)
        }
    }
}
