package com.alth.events.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alth.events.models.domain.users.PublicUser
import com.alth.events.repositories.impl.InMemoryCachingFriendshipRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendViewModel @Inject constructor(
    private val friendshipRepository: InMemoryCachingFriendshipRepository,
) : ViewModel() {
    fun sendFriendRequest(user: PublicUser) {
        viewModelScope.launch {
            friendshipRepository.getPendingFriendsSentToMe(0, 10)(false)
        }
    }
}