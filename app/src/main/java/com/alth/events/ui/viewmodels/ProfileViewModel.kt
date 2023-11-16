package com.alth.events.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alth.events.models.domain.authentication.AuthenticationState
import com.alth.events.models.network.users.ingress.PublicUserResponseDto
import com.alth.events.repositories.AuthenticationRepository
import com.alth.events.repositories.CachingFriendshipRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileUIState(
    val myConfirmedFriends: List<PublicUserResponseDto>,
    val pendingFriendsISent: List<PublicUserResponseDto>,
    val pendingFriendsSentToMe: List<PublicUserResponseDto>,
) {
    companion object {
        fun initial() =
            ProfileUIState(emptyList(), emptyList(), emptyList())
    }
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val friendshipRepository: CachingFriendshipRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUIState.initial())
    val uiState = _uiState.asStateFlow()
    private var loadJob: Job // Currently running network job to not overlap jobs
    private var page = 0 // Current page of friends

    private val pageSize = 20
    private val pageBufferRefresh =
        10  // number of elements left before we trigger a new call to get friends

    init {
        loadJob = loadNextFriends()
    }

    fun consumeFriendIndex(i: Int) {
        if (i >= page * pageSize - pageBufferRefresh) {
            if (!loadJob.isActive) {
                loadJob = loadNextFriends()
            }
        }
    }

    fun signOut() {
        authenticationRepository.signOut()
    }

    private fun loadNextFriends() = viewModelScope.launch {
        when (authenticationRepository.authenticationState.first()) {
            is AuthenticationState.UserOk -> {
                val myFriends = async {
                    friendshipRepository.getMyConfirmedFriends(page, pageSize)(true)
                }
                val sentToMe = async {
                    friendshipRepository.getPendingFriendsSentToMe(page, pageSize)(true)
                }
                val sentFromMe = async {
                    friendshipRepository.getPendingFriendsThatISent(page, pageSize)(true)
                }
                _uiState.value = uiState.value.copy(
                    myConfirmedFriends = uiState.value.myConfirmedFriends + myFriends.await().t,
                    pendingFriendsISent = uiState.value.pendingFriendsISent + sentFromMe.await().t,
                    pendingFriendsSentToMe = uiState.value.pendingFriendsSentToMe + sentToMe.await().t,
                )
                page += 1
            }

            else -> {
                _uiState.value = uiState.value.copy(
                    myConfirmedFriends = emptyList(),
                    pendingFriendsISent = emptyList(),
                    pendingFriendsSentToMe = emptyList(),
                )
                page = 0
            }
        }
    }
}