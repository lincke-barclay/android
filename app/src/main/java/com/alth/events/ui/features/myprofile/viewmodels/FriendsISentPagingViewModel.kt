package com.alth.events.ui.features.myprofile.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.alth.events.data.repositories.paging.friends.PendingFriendsISentPagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FriendsISentPagingViewModel @Inject constructor(
    friendsISentPagerRepository: PendingFriendsISentPagerRepository,
) : ViewModel() {
    val pager = friendsISentPagerRepository.pager().cachedIn(viewModelScope)
}
