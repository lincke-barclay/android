package com.alth.events.ui.viewmodels.landing

import androidx.lifecycle.ViewModel
import com.alth.events.data.repositories.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
) : ViewModel() {
    fun changeName(newName: String) {
        //authenticationRepository.changeNameOfCurrentlySignedInUser(newName)
    }

    fun signOut() {
        authenticationRepository.signOut()
    }
}
