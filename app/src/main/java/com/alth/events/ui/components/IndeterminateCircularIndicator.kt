package com.alth.events.ui.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun IndeterminateCircularIndicator() {
    CircularProgressIndicator(
        color = MaterialTheme.colorScheme.surfaceVariant
    )
}
