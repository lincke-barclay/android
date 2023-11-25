package com.alth.events.ui.features.search.events.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alth.events.data.repositories.QuickSearchEventRepository
import com.alth.events.logging.impl.loggerFactory
import com.alth.events.models.domain.events.PublicEventQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuickSearchEventsResultsViewModel @Inject constructor(
    private val quickSearchEventRepository: QuickSearchEventRepository,
) : ViewModel() {
    private val logger = loggerFactory.getLogger(this)
    val results = quickSearchEventRepository.results

    // Prevent from multiple jobs popping up
    private var queryChangeJob: Job = Job().also { it.complete() }

    init {
        onQueryChange("")
    }

    fun onQueryChange(newQuery: String) {
        val query = PublicEventQuery(
            titleContainsIC = newQuery,
        )
        if (!queryChangeJob.isActive) {
            queryChangeJob = viewModelScope.launch {
                quickSearchEventRepository.onQueryChange(query)
                if (results.value.size < 10) {
                    logger.debug(
                        "Didn't have enough events locally to populate quick search, loading" +
                                " some more from network"
                    )
                    quickSearchEventRepository.populateCache(query)
                    quickSearchEventRepository.onQueryChange(query)
                }
            }
        }
    }
}
