package com.alth.events.ui.features.feed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alth.events.R
import com.alth.events.ui.features.feed.models.FeedDisplayOption
import com.alth.events.ui.features.feed.models.FeedMetaInformationState
import com.alth.events.ui.util.toUiString
import kotlinx.datetime.Instant

@Composable
fun FeedInnerDrawerContent(
    feedMetaState: FeedMetaInformationState,

    /**
     * Callbacks
     */
    onClickNewDisplayOption: (FeedDisplayOption) -> Unit,
    refresh: () -> Unit,
    toggleIncludeMyFriendsEvents: (Boolean) -> Unit,
    toggleIncludeInvited: (Boolean) -> Unit,
    toggleIncludePublicEvents: (Boolean) -> Unit,
    changeFromDateTime: (Instant) -> Unit,
    changeToDateTime: (Instant) -> Unit,
) {
    ModalDrawerSheet {
        LazyColumn {
            item {
                Text(
                    style = MaterialTheme.typography.headlineLarge,
                    text = "Communeo",
                )
            }
            items(FeedDisplayOption.instances.size) { index ->
                FeedDisplayOption.instances[index].let {
                    NavigationDrawerItem(
                        label = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Icon(
                                    painter = painterResource(id = it.iconId),
                                    contentDescription = "TODO",
                                    modifier = Modifier
                                        .padding(horizontal = 10.dp)
                                )
                                Text(stringResource(id = it.displayNameId))
                            }
                        },
                        selected = it::class == feedMetaState.displayMode::class,
                        onClick = { onClickNewDisplayOption(it) },
                    )
                }
            }
            item {
                Divider()
            }
            item {
                RefreshFeedDrawerButton(
                    refresh = refresh,
                    modifier = Modifier.fillMaxSize(),
                )
            }
            item {
                Divider()
            }
            item {
                DateSelectorDrawer(
                    date = feedMetaState.feedEventQuery.fromDateTimeInclusive,
                    onChangeDate = changeFromDateTime,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 10.dp)
                ) {
                    Text("From: ")
                }
                DateSelectorDrawer(
                    date = feedMetaState.feedEventQuery.toDateTimeInclusive,
                    onChangeDate = changeToDateTime,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 10.dp)
                ) {
                    Text("To: ")
                }
            }
            item {
                Divider()
            }
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 24.dp),
                    text = "Filter"
                )
            }
            item {
                DrawerSelectableCheckBox(
                    text = "My Friend's Events",
                    onChange = toggleIncludeMyFriendsEvents,
                    modifier = Modifier.padding(horizontal = 10.dp),
                    checked = feedMetaState.feedEventQuery.includeMyFriendsEvents,
                )
            }
            item {
                DrawerSelectableCheckBox(
                    text = "Invited",
                    onChange = toggleIncludeInvited,
                    modifier = Modifier.padding(horizontal = 10.dp),
                    checked = feedMetaState.feedEventQuery.includeEventsImInvitedTo,
                )
            }
            item {
                DrawerSelectableCheckBox(
                    text = "Public Events",
                    onChange = toggleIncludePublicEvents,
                    modifier = Modifier.padding(horizontal = 10.dp),
                    checked = feedMetaState.feedEventQuery.includePublicEvents,
                )
            }
        }
    }
}

@Composable
fun DateSelectorDrawer(
    modifier: Modifier = Modifier,
    date: Instant?,
    onChangeDate: (Instant) -> Unit,
    label: @Composable () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        label()
        TextButton(onClick = { }) {
            Text(date?.toUiString(LocalContext.current) ?: "All")
        }
    }
}

@Composable
fun RefreshFeedDrawerButton(
    refresh: () -> Unit,
    modifier: Modifier,
) {
    Row(
        modifier = modifier
            .clickable { refresh() }
            .padding(vertical = 24.dp, horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_refresh_24),
            contentDescription = "TODO",
            modifier = Modifier
                .padding(horizontal = 10.dp)
        )
        Text(stringResource(id = R.string.refresh))
    }
}

@Composable
fun DrawerSelectableCheckBox(
    text: String,
    onChange: (Boolean) -> Unit,
    modifier: Modifier,
    checked: Boolean,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onChange(!checked) },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onChange,
        )
        Text(
            color = MaterialTheme.colorScheme.onBackground,
            text = text,
        )
    }
}



