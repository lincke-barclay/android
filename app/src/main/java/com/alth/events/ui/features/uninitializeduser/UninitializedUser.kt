package com.alth.events.ui.features.uninitializeduser

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.alth.events.ui.viewmodels.landing.EditProfileViewModel

@Composable
fun UninitializedUser(
    editProfileViewModel: EditProfileViewModel = hiltViewModel()
) {
    var enteredName by remember { mutableStateOf("") }
    Column {
        Text("Name:")
        TextField(value = enteredName, onValueChange = { enteredName = it })
        Button(
            enabled = enteredName.isNotEmpty(),
            onClick = { editProfileViewModel.changeName(enteredName) }) {
            Text("Create Profile")
        }
        Button(onClick = { editProfileViewModel.signOut() }) {
            Text("Sign Out")
        }
    }
}
