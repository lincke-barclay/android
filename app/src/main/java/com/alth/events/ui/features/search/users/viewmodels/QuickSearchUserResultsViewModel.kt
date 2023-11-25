package com.alth.events.ui.features.search.users.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alth.events.data.repositories.QuickSearchUserRepository
import com.alth.events.logging.impl.loggerFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuickSearchUserResultsViewModel @Inject constructor(
    private val quickSearchUserRepository: QuickSearchUserRepository,
) : ViewModel() {
    private val logger = loggerFactory.getLogger(this)
    val results = quickSearchUserRepository.results

    // Prevent from multiple jobs popping up
    private var queryChangeJob: Job = Job().also { it.complete() }

    init {
        onQueryChange("")
    }

    fun onQueryChange(newQuery: String) {
        if (!queryChangeJob.isActive) {
            queryChangeJob = viewModelScope.launch {
                quickSearchUserRepository.onQueryChange(newQuery)
                if (results.value.size < 10) {
                    logger.debug(
                        "Didn't have enough events locally to populate quick search, loading" +
                                " some more from network"
                    )
                    quickSearchUserRepository.populateCache(newQuery)
                    quickSearchUserRepository.onQueryChange(newQuery)
                }
            }
        }
    }
}
