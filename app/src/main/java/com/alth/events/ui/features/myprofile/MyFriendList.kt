package com.alth.events.ui.features.myprofile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.alth.events.R
import com.alth.events.models.network.users.ingress.PublicUserResponseDto

data class ToggleableTag(
    val title: String,
    val isSelected: Boolean,
    val onClick: () -> Unit,
)

@Composable
fun RowOfToggleableTagsWithCheckMarks(
    toggleableTags: List<ToggleableTag>
) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        toggleableTags.onEach {
            CheckMarkSearchFilterChip(
                text = it.title,
                currentlySelected = it.isSelected,
                toggle = it.onClick,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckMarkSearchFilterChip(
    text: String,
    currentlySelected: Boolean,
    toggle: () -> Unit,
) {
    FilterChip(
        modifier = Modifier
            .padding(6.dp),
        selected = currentlySelected,
        onClick = toggle,
        leadingIcon = {
            AnimatedVisibility(
                visible = currentlySelected,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_check_24),
                    contentDescription = "check mark"
                )
            }
        },
        label = { Text(text) }
    )
}


@Composable
fun UserHorizontalBarItem(
    organizer: PublicUserResponseDto,
    trailingText: (name: String) -> String = { it },
    rightJustifiedContent: @Composable () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            Box(
                modifier = Modifier
                    .padding(end = 12.dp, top = 6.dp, bottom = 6.dp)
            ) {
                /**
                AsyncImage(
                modifier = Modifier
                .size(45.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Black, CircleShape),
                contentScale = ContentScale.Crop,
                model = ImageRequest.Builder(LocalContext.current)
                .data(organizer.profilePictureUrl.toString())
                .build(),
                contentDescription = null,
                )
                 **/
            }
            Text(
                text = trailingText(organizer.name),
                style = MaterialTheme.typography.labelMedium
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
        ) {
            rightJustifiedContent()
        }
    }
}

@Composable
fun MyFriendsList(
    friends: List<PublicUserResponseDto>,
    pendingFriendsFromMe: List<PublicUserResponseDto>,
    pendingFriendsToMe: List<PublicUserResponseDto>,
) {
    var myFriendsTagSelected by remember {
        mutableStateOf(true)
    }
    var pendingTagSelected by remember {
        mutableStateOf(false)
    }
    var rejectedTagSelected by remember {
        mutableStateOf(false)
    }

    Column {
        RowOfToggleableTagsWithCheckMarks(
            toggleableTags = listOf(
                ToggleableTag(
                    title = "My Friends",
                    isSelected = myFriendsTagSelected,
                    onClick = {
                        myFriendsTagSelected = true
                        pendingTagSelected = false
                        rejectedTagSelected = false
                    },
                ),
                ToggleableTag(
                    title = "Pending",
                    isSelected = pendingTagSelected,
                    onClick = {
                        myFriendsTagSelected = false
                        pendingTagSelected = true
                        rejectedTagSelected = false
                    },
                ),
                ToggleableTag(
                    title = "Rejected",
                    isSelected = rejectedTagSelected,
                    onClick = {
                        myFriendsTagSelected = false
                        pendingTagSelected = false
                        rejectedTagSelected = true
                    },
                )
            )
        )
        AnimatedVisibility(visible = myFriendsTagSelected) {
            LazyColumn {
                items(friends.size) {
                    UserHorizontalBarItem(organizer = friends[it])
                }
            }
        }
        AnimatedVisibility(visible = pendingTagSelected) {
            PendingLazyList(
                pendingToMe = pendingFriendsToMe.toSet(),
                pendingFromMe = pendingFriendsFromMe.toSet(),
            )
        }
    }
}

@Composable
fun PendingLazyList(
    pendingToMe: Set<PublicUserResponseDto>,
    pendingFromMe: Set<PublicUserResponseDto>,
) {
    var toMeTagSelected by remember {
        mutableStateOf(true)
    }
    var fromMeSelected by remember {
        mutableStateOf(true)
    }

    val toDisplay = if (toMeTagSelected && !fromMeSelected) {
        pendingToMe.toList()
    } else if (fromMeSelected && !toMeTagSelected) {
        pendingFromMe.toList()
    } else {
        (pendingToMe + pendingFromMe).toList()
    }
    Column {
        RowOfToggleableTagsWithCheckMarks(
            toggleableTags = listOf(
                ToggleableTag(
                    title = "To Me",
                    isSelected = toMeTagSelected,
                    onClick = {
                        if (fromMeSelected) {
                            toMeTagSelected = !toMeTagSelected
                        }
                    },
                ),
                ToggleableTag(
                    title = "From Me",
                    isSelected = fromMeSelected,
                    onClick = {
                        if (toMeTagSelected) {
                            fromMeSelected = !fromMeSelected
                        }
                    },
                ),
            )
        )
        LazyColumn {
            items(toDisplay.size) {
                UserHorizontalBarItem(organizer = toDisplay[it]) {
                    if (toDisplay[it] in pendingToMe) {
                        Row {
                            Icon(
                                painter = painterResource(
                                    id = R.drawable.baseline_group_add_24
                                ),
                                contentDescription = null
                            )
                            Icon(
                                painter = painterResource(
                                    id = R.drawable.baseline_group_remove_24
                                ),
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}
