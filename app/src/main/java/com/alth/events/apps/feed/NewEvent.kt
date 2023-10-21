package com.alth.events.apps.feed

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.alth.events.apps.feed.viewmodels.NewEventViewModel
import com.alth.events.ui.components.IndeterminateCircularIndicator
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

@Composable
fun NewFeedMain(
    newEventViewModel: NewEventViewModel,
    navigateBack: () -> Unit,
) {
    val uiState by newEventViewModel.uiState.collectAsState()

    /**
     * TODO - Everything about this is stupid and awful
     */
    val timeZone = TimeZone.currentSystemDefault()

    fun onStartDayChange(day: LocalDate) {
        val newLocalDateTime = LocalDateTime(day, uiState.startTime)
        val newInstant = newLocalDateTime.toInstant(timeZone)
        newEventViewModel.changeStartDate(newInstant)
    }

    fun onEndDayChange(day: LocalDate) {
        val newLocalDateTime = LocalDateTime(day, uiState.endTime)
        val newInstant = newLocalDateTime.toInstant(timeZone)
        newEventViewModel.changeEndDate(newInstant)
    }

    fun onStartTimeChange(time: LocalTime) {
        val newLocalDateTime = LocalDateTime(uiState.startDate, time)
        val newInstant = newLocalDateTime.toInstant(timeZone)
        newEventViewModel.changeStartDate(newInstant)
    }

    fun onEndTimeChange(time: LocalTime) {
        val newLocalDateTime = LocalDateTime(uiState.endDate, time)
        val newInstant = newLocalDateTime.toInstant(timeZone)
        newEventViewModel.changeEndDate(newInstant)
    }

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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewEventMainStateless(
    enteredTitle: String,
    enteredShortDescription: String,
    enteredLongDescription: String,
    selectedStartDay: LocalDate,
    selectedStartTime: LocalTime,
    selectedEndDay: LocalDate,
    selectedEndTime: LocalTime,
    loading: Boolean,
    onChangeEnteredTitle: (String) -> Unit,
    onChangeEnteredShortDescription: (String) -> Unit,
    onChangeEnteredLongDescription: (String) -> Unit,
    onChangeSelectedStartDay: (LocalDate) -> Unit,
    onChangeSelectedStartTime: (LocalTime) -> Unit,
    onChangeSelectedEndDay: (LocalDate) -> Unit,
    onChangeSelectedEndTime: (LocalTime) -> Unit,
    canSubmit: Boolean,
    onSubmit: () -> Unit,
) {

    val context = LocalContext.current

    val startDatePicker = DatePickerDialog(
        context, { _, year, month, day ->
            onChangeSelectedStartDay(LocalDate(year = year, monthNumber = month, dayOfMonth = day))
        }, selectedStartDay.year, selectedStartDay.monthNumber, selectedStartDay.dayOfMonth
    )

    val endDatePicker = DatePickerDialog(
        context, { _, year, month, day ->
            onChangeSelectedEndDay(LocalDate(year = year, monthNumber = month, dayOfMonth = day))
        }, selectedEndDay.year, selectedEndDay.monthNumber, selectedEndDay.dayOfMonth
    )

    val startTimePicker = TimePickerDialog(
        context, { _, hourOfDay, minute ->
            onChangeSelectedStartTime(LocalTime(hour = hourOfDay, minute = minute))
        }, selectedStartTime.hour, selectedStartTime.minute, true
    )

    val endTimePicker = TimePickerDialog(
        context, { _, hourOfDay, minute ->
            onChangeSelectedEndTime(LocalTime(hour = hourOfDay, minute = minute))
        }, selectedEndTime.hour, selectedEndTime.minute, true
    )

    if (loading) {
        IndeterminateCircularIndicator()
    }
    Column {
        Text("New Feed")
        Text(text = "Title:")
        TextField(value = enteredTitle, onValueChange = onChangeEnteredTitle)
        Text(text = "ShortDescription:")
        TextField(value = enteredShortDescription, onValueChange = onChangeEnteredShortDescription)
        Text(text = "LongDescription:")
        TextField(value = enteredLongDescription, onValueChange = onChangeEnteredLongDescription)
        Row {
            Text("Start: ")
            Button(onClick = { startDatePicker.show() }) {
                Row {
                    Text("$selectedStartDay")
                }
            }
            Button(onClick = { startTimePicker.show() }) {
                Row {
                    Text("$selectedStartTime")
                }
            }
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