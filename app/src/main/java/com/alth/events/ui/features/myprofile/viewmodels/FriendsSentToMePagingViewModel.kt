package com.alth.events.ui.features.myprofile.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.alth.events.data.caching.friends.MyConfirmedFriendsCachingManager
import com.alth.events.data.repositories.paging.friends.PendingFriendsSentToMePagerRepository
import com.alth.events.models.domain.friends.FriendsQuery
import com.alth.events.networking.sources.NetworkFriendshipDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendsSentToMePagingViewModel @Inject constructor(
    friendsSentToMePagerRepository: PendingFriendsSentToMePagerRepository,
    private val remoteFriendshipDataSource: NetworkFriendshipDataSource,
    private val myConfirmedFriendsCachingManager: MyConfirmedFriendsCachingManager,
) : ViewModel() {
    val pager = friendsSentToMePagerRepository.pager().cachedIn(viewModelScope)

    private var job: Job = Job().also { it.complete() }

    fun accept(userId: String) {
        if (!job.isActive) {
            job = viewModelScope.launch {
                remoteFriendshipDataSource.postFriendship(userId)

                // TODO - change this
                myConfirmedFriendsCachingManager.updateLocalFriends(
                    query = FriendsQuery(page = 0, pageSize = 20),
                    invalidateCache = true,
                )
            }
        }
    }

    fun decline(userId: String) {
        if (!job.isActive) {
            job = viewModelScope.launch { remoteFriendshipDataSource.deleteFriendship(userId) }
        }
    }
}