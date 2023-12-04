package com.alth.events.ui.features.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.alth.events.R

@Composable
fun Search(
    navigateBack: () -> Unit,
) {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    Scaffold { innerPadding ->
        SearchInnerScaffold(
            modifier = Modifier.padding(innerPadding),
            currentQuery = query,
            onQueryChange = { query = it },
            onQuerySubmit = { active = false },
            navigateBack = navigateBack,
            active = active,
            onActiveChange = { active = it }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchInnerScaffold(
    currentQuery: String,
    onQueryChange: (String) -> Unit,
    onQuerySubmit: (String) -> Unit,
    navigateBack: () -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            query = currentQuery,
            onQueryChange = onQueryChange,
            onSearch = onQuerySubmit,
            active = active,
            onActiveChange = onActiveChange,
            placeholder = {
                Text("Search for events or users")
            },
            leadingIcon = {
                LeadingIconByActiveQuery(
                    isActive = active,
                    resetQuery = { onQueryChange("") },
                    makeInactive = { onActiveChange(false) },
                    navigateBack = navigateBack,
                )
            },
            trailingIcon = {
                TrailingIconByActiveQuery(
                    isActive = active,
                    query = currentQuery,
                    resetQuery = { onQueryChange("") },
                    makeActive = { onActiveChange(true) },
                )
            }
        ) {
            Text("Foo")
        }
    }
}

@Composable
fun LeadingIconByActiveQuery(
    isActive: Boolean,
    resetQuery: () -> Unit,
    navigateBack: () -> Unit,
    makeInactive: () -> Unit,
) {
    if (isActive) {
        IconButton(onClick = {
            makeInactive()
            resetQuery()
        }) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = "Back arrow for search",
            )
        }
    } else {
        IconButton(
            onClick = {
                navigateBack()
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = "Back arrow for search",
            )
        }
    }
}

@Composable
fun TrailingIconByActiveQuery(
    isActive: Boolean,
    query: String,
    resetQuery: () -> Unit,
    makeActive: () -> Unit,
) {
    if (isActive) {
        if (query.isNotEmpty()) {
            IconButton(onClick = { resetQuery() }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_close_24),
                    contentDescription = "Clear search query",
                )
            }
        }
    } else {
        if (query.isNotEmpty()) {
            IconButton(onClick = {
                resetQuery()
                makeActive()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_close_24),
                    contentDescription = "Clear search query",
                )
            }
        }
    }
}