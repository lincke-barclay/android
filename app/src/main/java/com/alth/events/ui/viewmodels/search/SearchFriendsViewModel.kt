package com.alth.events.ui.viewmodels.search

import androidx.lifecycle.viewModelScope
import com.alth.events.domain.SearchFriendsUseCase
import com.alth.events.models.network.users.ingress.PublicUserResponseDto
import com.alth.events.repositories.CachingFriendshipRepository
import com.alth.events.ui.viewmodels.PagingViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class FriendRequestStatus {
    NotSending,
    Sending,
    Sent
}

data class SearchFriendUiState(
    val friends: List<PublicUserResponseDto>,
) {
    companion object {
        fun initial() = SearchFriendUiState(emptyList())
    }
}

@HiltViewModel
class SearchFriendsViewModel @Inject constructor(
    private val searchFriendsUseCase: SearchFriendsUseCase,
    private val friendshipRepository: CachingFriendshipRepository,
) : PagingViewModel<List<PublicUserResponseDto>>(
    pageSize = 30,
    numBeforeNextFetch = 5,
) {
    private var lastSearchString = ""
    private val _uiState = MutableStateFlow(SearchFriendUiState.initial())
    val uiState = _uiState.asStateFlow()

    // Jobs
    private var sendingFriendRequestJob: Job = Job().also { it.complete() }

    init {
        consumeItem(0)
    }

    override suspend fun getNextPageImpl(page: Int, pageSize: Int): List<PublicUserResponseDto> {
        return searchFriendsUseCase(page, pageSize, lastSearchString)
    }

    override suspend fun setNextPageImpl(page: List<PublicUserResponseDto>) {
        _uiState.value = _uiState.value.copy(
            friends = _uiState.value.friends + page
        )
    }

    fun onQueryStringChange(newString: String) {
        lastSearchString = newString
    }

    fun onSearchQuerySubmitted(newQuery: String) {
        lastSearchString = newQuery
        viewModelScope.launch {
            stopAndResetPaging {
                _uiState.value = _uiState.value.copy(friends = emptyList())
            }
        }
    }

    fun sendFriendRequest(user: PublicUserResponseDto) {
        if (!sendingFriendRequestJob.isActive) {
            sendingFriendRequestJob = viewModelScope.launch {
                friendshipRepository.sendFriendRequest(user.id)
            }
        }
    }
}