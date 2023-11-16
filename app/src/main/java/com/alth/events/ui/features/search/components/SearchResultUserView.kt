package com.alth.events.ui.features.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.alth.events.models.network.users.ingress.PublicUserResponseDto

@Composable
fun SearchResultUserView(
    user: PublicUserResponseDto,
    onClickOnUser: (user: PublicUserResponseDto) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClickOnUser(user) }
            .background(color = Color.Red),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(user.name)
    }
}
