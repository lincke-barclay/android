package com.alth.events.ui.features.changeprofilephotourl

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.alth.events.R
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeProfilePhotoUrl(

) {
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val cropImageLauncher = rememberLauncherForActivityResult(
        contract = CropImageContract(),
    ) {
        if (it.isSuccessful) {
            imageUri = it.uriContent
        }
    }

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Please choose a profile photo") })
    }) {
        ChangeProfilePhotoUrlStateless(
            username = "Theo",
            modifier = Modifier.padding(it),
            submitImage = {},
            imageUri = imageUri,
            launchImagePicker = {
                cropImageLauncher.launch(
                    CropImageContractOptions(
                        uri = null,
                        cropImageOptions = CropImageOptions(
                            imageSourceIncludeCamera = true,
                            imageSourceIncludeGallery = true,
                            fixAspectRatio = true,
                            aspectRatioX = 1,
                            aspectRatioY = 1,
                        )
                    )
                )
            }
        )
    }
}

@Composable
fun ChangeProfilePhotoUrlStateless(
    username: String,
    submitImage: () -> Unit,
    imageUri: Uri?,
    launchImagePicker: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box {
            if (imageUri != null) {
                Box(
                    modifier = Modifier
                        .size(224.dp)
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .size(224.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.White, CircleShape)
                            .clickable {
                                launchImagePicker()
                            },
                        contentScale = ContentScale.Crop,
                        model = imageUri,
                        contentDescription = "Translated description of what the image contains"
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .clip(CircleShape)
                            .background(color = Color.LightGray)
                            .border(2.dp, Color.White, CircleShape)
                            .size(50.dp),
                    ) {
                        Icon(
                            modifier = Modifier
                                .align(Alignment.Center),
                            painter = painterResource(
                                id = R.drawable.baseline_camera_alt_24
                            ),
                            contentDescription = "Small camera icon",
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .size(224.dp),
                ) {
                    Icon(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center)
                            .clip(CircleShape)
                            .border(2.dp, Color.Gray, CircleShape)
                            .clickable {
                                launchImagePicker()
                            },
                        tint = Color.LightGray,
                        painter = painterResource(id = R.drawable.baseline_person_24),
                        contentDescription = "No photo picked default placement",
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .clip(CircleShape)
                            .background(color = Color.LightGray)
                            .border(2.dp, Color.White, CircleShape)
                            .size(50.dp)
                            .clickable {
                                launchImagePicker()
                            },
                    ) {
                        Icon(
                            modifier = Modifier
                                .align(Alignment.Center),
                            painter = painterResource(
                                id = R.drawable.baseline_camera_alt_24
                            ),
                            contentDescription = "Small camera icon",
                        )
                    }
                }
            }
        }
        Text(
            text = username,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
        Button(onClick = submitImage) {
            Text("Confirm")
        }
    }
}