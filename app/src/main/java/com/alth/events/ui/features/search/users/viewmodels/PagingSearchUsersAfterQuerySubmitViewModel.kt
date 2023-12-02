package com.alth.events.ui.features.search.users.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alth.events.data.repositories.paging.users.UserQueryPagerRepository
import com.alth.events.database.models.users.PublicUserEntity
import com.alth.events.logging.impl.loggerFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class PagingSearchUsersAfterQuerySubmitViewModel @Inject constructor(
    private val userQueryPagerRepository: UserQueryPagerRepository,
) : ViewModel() {
    private val logger = loggerFactory.getLogger(this)

    private val _searchUsersFlow =
        MutableStateFlow<Flow<PagingData<PublicUserEntity>>>(emptyFlow())
    val searchUsersFlow = _searchUsersFlow.asStateFlow()

    fun onQuerySubmit(newQuery: String) {
        _searchUsersFlow.value =
            userQueryPagerRepository
                .pager(newQuery)
                .flow
                .map {
                    logger.debug("User consumed in query vm: $it")
                    it
                }
                .cachedIn(viewModelScope)
    }
}
