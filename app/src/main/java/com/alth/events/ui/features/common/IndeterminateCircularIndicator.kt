package com.alth.events.ui.features.common

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun IndeterminateCircularIndicator() {
    CircularProgressIndicator(
        color = MaterialTheme.colorScheme.surfaceVariant
    )
}
