package com.alth.events.ui.features.otherprofile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alth.events.data.caching.CachingFriendshipRepository
import com.alth.events.data.caching.CachingUserRepository
import com.alth.events.models.CacheGetResult
import com.alth.events.networking.models.users.ingress.PublicUserResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtherProfileViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userRepository: CachingUserRepository,
    private val friendshipRepository: CachingFriendshipRepository,
) : ViewModel() {
    val id = savedStateHandle.get<String>("userId")
        ?: throw Exception(
            "userId was not passed to saved " +
                    "state handle for other profile view model"
        )

    // Jobs
    var sendFriendRequestJob: Job = Job().also { it.complete() }

    private val _uiState = MutableStateFlow<PublicUserResponseDto?>(null).also {
        viewModelScope.launch {
            when (val result = userRepository.getPublicUser(id)(true)) {
                is CacheGetResult.CacheSuccess,
                is CacheGetResult.SourceOfTruth -> {
                    it.value = result.t
                }

                is CacheGetResult.SourceOfTruthFailedButCacheWorked,
                is CacheGetResult.EverythingFailed -> {
                    throw Exception("Failed to fetch user!")
                }
            }
        }
    }

    val uiState = _uiState.asStateFlow()

    fun sendFriendRequest() {
        if (!sendFriendRequestJob.isActive) {
            sendFriendRequestJob = viewModelScope.launch {
                friendshipRepository.sendFriendRequest(id)
            }
        }
    }
}