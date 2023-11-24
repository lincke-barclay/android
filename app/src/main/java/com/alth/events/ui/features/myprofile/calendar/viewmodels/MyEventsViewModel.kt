package com.alth.events.ui.features.myprofile.calendar.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alth.events.data.repositories.paging.PagingMyEventsRepository
import com.alth.events.database.models.derived.MyEvent
import com.alth.events.logging.impl.loggerFactory
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
    private val _myEventsFlow = MutableStateFlow<Flow<PagingData<MyEvent>>>(emptyFlow())
    val myEventsFlow = _myEventsFlow.asStateFlow()

    init {
        viewModelScope.launch {
            _myEventsFlow.value = pagingMyEventsRepository.myEventsFlow(
                query = PublicEventQuery.blank()
            ).cachedIn(viewModelScope)
        }
    }
}
