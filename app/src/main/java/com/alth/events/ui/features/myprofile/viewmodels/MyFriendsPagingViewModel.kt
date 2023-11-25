package com.alth.events.ui.features.myprofile.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.alth.events.data.repositories.paging.friends.ConfirmedFriendsPagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyFriendsPagingViewModel @Inject constructor(
    myFriendsPagerRepository: ConfirmedFriendsPagerRepository,
) : ViewModel() {
    val pager = myFriendsPagerRepository.pager().cachedIn(viewModelScope)
}