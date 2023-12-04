package com.alth.events.ui.features.splashscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.alth.events.ui.features.common.IndeterminateCircularIndicator

@Composable
fun SplashScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        IndeterminateCircularIndicator()
    }
}
