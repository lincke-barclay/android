package com.alth.events.ui.features.common

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.alth.events.R
import com.alth.events.ui.features.common.viewmodels.ProfilePhotoViewModel

@Composable
private fun DrawableIconButton(
    iconId: Int,
    contentDescription: String,
    action: () -> Unit,
) {
    IconButton(onClick = action) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = contentDescription,
        )
    }
}

@Composable
fun BackIconButton(
    action: () -> Unit,
    contentDescription: String? = null,
) {
    DrawableIconButton(
        iconId = R.drawable.baseline_arrow_back_24,
        contentDescription = contentDescription ?: "Navigate Back",
        action = action,
    )
}

@Composable
fun SignOutButton(
    action: () -> Unit,
    contentDescription: String? = null,
) {
    DrawableIconButton(
        iconId = R.drawable.baseline_logout_24,
        contentDescription = contentDescription ?: "Sign Out",
        action = action,
    )
}

@Composable
fun SearchButton(
    action: () -> Unit,
    contentDescription: String? = null,
) {
    DrawableIconButton(
        iconId = R.drawable.baseline_search_24,
        contentDescription = contentDescription ?: "Search Button",
        action = action,
    )
}


@Composable
fun CircularProfilePictureIcon(
    action: () -> Unit,
    contentDescription: String? = null,
    profilePhotoViewModel: ProfilePhotoViewModel = hiltViewModel(),
) {
    val url by profilePhotoViewModel.uiState.collectAsState()

    IconButton(onClick = action) {
        Icon(
            modifier = Modifier
                .clip(CircleShape)
                .border(0.5.dp, Color.Black, CircleShape),
            painter = rememberAsyncImagePainter(
                model = url ?: stringResource(id = R.string.default_profile_picture_url),
            ),
            contentDescription = contentDescription ?: "Profile picture",
            tint = Color.Unspecified,
        )
    }
}

@Composable
fun CancelButton(
    action: () -> Unit,
    contentDescription: String? = null,
) {
    DrawableIconButton(
        iconId = R.drawable.baseline_close_24,
        contentDescription = contentDescription ?: "Cancel",
        action = action,
    )
}

@Composable
fun CheckButton(
    action: () -> Unit,
    contentDescription: String? = null,
) {
    DrawableIconButton(
        iconId = R.drawable.baseline_close_24,
        contentDescription = contentDescription ?: "Confirm",
        action = action,
    )
}
