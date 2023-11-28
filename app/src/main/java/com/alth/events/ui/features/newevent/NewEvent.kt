package com.alth.events.ui.features.newevent

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.alth.events.ui.features.newevent.viewmodels.NewEventViewModel
import kotlinx.datetime.Clock

@Composable
fun NewFeedMain(
    newEventViewModel: NewEventViewModel,
    navigateBack: () -> Unit,
) {
    val a = Clock.System.now()
    val uiState by newEventViewModel.uiState.collectAsState()


    /**


    NewEventMainStateless(
    enteredTitle = uiState.enteredTitle,
    enteredShortDescription = uiState.enteredShortDescription,
    enteredLongDescription = uiState.enteredLongDescription,
    selectedStartDay = uiState.startDate,
    selectedStartTime = uiState.startTime,
    selectedEndDay = uiState.endDate,
    selectedEndTime = uiState.endTime,
    loading = uiState.loading,
    onChangeEnteredTitle = newEventViewModel::changeTitle,
    onChangeEnteredShortDescription = newEventViewModel::changeShortDesc,
    onChangeEnteredLongDescription = newEventViewModel::changeLongDesc,
    onChangeSelectedStartDay = ::onStartDayChange,
    onChangeSelectedStartTime = ::onStartTimeChange,
    onChangeSelectedEndDay = ::onEndDayChange,
    onChangeSelectedEndTime = ::onEndTimeChange,
    canSubmit = uiState.canSubmit,
    ) {
    newEventViewModel::submit {
    navigateBack()
    }
    }
     **/
}

/**
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewEventTopAppBar(
onExit: () -> Unit,
onSave: () -> Unit,
) {
TopAppBar(
title = { Text("Hello World") }
)
}

@Composable
fun NewEventTitleSelection(
enteredTitle: String,
onTitleChange: (String) -> Unit,
) {
Text(text = "Title:")
TextField(value = enteredTitle, onValueChange = onTitleChange)
}


@Composable
fun NewEventMainStateless(
enteredTitle: String,
enteredShortDescription: String,
enteredLongDescription: String,

loading: Boolean,
onChangeEnteredTitle: (String) -> Unit,
onChangeEnteredShortDescription: (String) -> Unit,
onChangeEnteredLongDescription: (String) -> Unit,

canSubmit: Boolean,
onSubmit: () -> Unit,
) {


if (loading) {
IndeterminateCircularIndicator()
}
Column {
Text("New Feed")

Text(text = "ShortDescription:")
TextField(value = enteredShortDescription, onValueChange = onChangeEnteredShortDescription)
Text(text = "LongDescription:")
TextField(value = enteredLongDescription, onValueChange = onChangeEnteredLongDescription)
Row {
Text("Start: ")


}

Row {
Text("End: ")
Button(onClick = { endDatePicker.show() }) {
Row {
Text("$selectedEndDay")
}
}
Button(onClick = { endTimePicker.show() }) {
Row {
Text("$selectedEndTime")
}
}
}

Button(enabled = canSubmit, onClick = onSubmit) {
Text("Submit")
}
}
}

@Preview
@Composable
fun CreateNewEventPreview() {
NewEventMainStateless(
enteredTitle = "Foo bar",
enteredShortDescription = "This is a foo bar",
enteredLongDescription = "This is a long description",
selectedStartDay = Clock.System.now().toLocalDateTime(TimeZone.UTC).date,
selectedStartTime = Clock.System.now().toLocalDateTime(TimeZone.UTC).time,
selectedEndDay = Clock.System.now().toLocalDateTime(TimeZone.UTC).date,
selectedEndTime = Clock.System.now().toLocalDateTime(TimeZone.UTC).time,
loading = false,
onChangeEnteredTitle = {},
onChangeEnteredShortDescription = {},
onChangeEnteredLongDescription = {},
onChangeSelectedStartDay = {},
onChangeSelectedStartTime = {},
onChangeSelectedEndDay = {},
onChangeSelectedEndTime = {},
canSubmit = false,
onSubmit = {}
)
}
 **/