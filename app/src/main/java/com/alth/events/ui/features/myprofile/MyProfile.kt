package com.alth.events.ui.features.myprofile

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.alth.events.ui.features.common.BackIconButton
import com.alth.events.ui.features.common.CircularProfilePictureIcon
import com.alth.events.ui.features.common.SignOutButton
import com.alth.events.ui.features.myprofile.viewmodels.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProfile(
    pvm: ProfileViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {
    val currentlySignedInUser by pvm.uiState.collectAsState()
    currentlySignedInUser?.let { signedInUser ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = signedInUser.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                        )
                    },
                    navigationIcon = {
                        BackIconButton(action = navigateBack)
                    },
                    actions = {
                        CircularProfilePictureIcon(
                            action = { /*TODO*/ },
                        )
                        SignOutButton(action = pvm::signOut)
                    }
                )
            }
        ) { innerPadding ->
            MyProfileInnerScaffoldContent(
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}


