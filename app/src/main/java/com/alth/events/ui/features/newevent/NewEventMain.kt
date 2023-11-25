package com.alth.events.ui.features.newevent

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alth.events.ui.features.newevent.viewmodels.NewEventUiState
import com.alth.events.ui.features.newevent.viewmodels.NewEventViewModel
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

@Composable
fun NewEventMain(
    vm: NewEventViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {
    val uiState by vm.uiState.collectAsState()
    NewEventMainNoViewModel(
        uiState = uiState,
        onChangeStartDate = vm::changeStartDate,
        onChangeEndDate = vm::changeEndDate,
        navigateBack = {},
    )
}

@Composable
fun NewEventMainNoViewModel(
    uiState: NewEventUiState,
    onChangeStartDate: (Instant) -> Unit,
    onChangeEndDate: (Instant) -> Unit,
    navigateBack: () -> Unit,
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        DatePickSection(
            changeStartDateTime = onChangeStartDate,
            changeEndDateTime = onChangeEndDate,
            selectedStartDay = uiState.startDate,
            selectedStartTime = uiState.startTime,
            selectedEndDay = uiState.endDate,
            selectedEndTime = uiState.endTime,
        )
    }
}

@Composable
fun DatePickSection(
    changeStartDateTime: (Instant) -> Unit,
    changeEndDateTime: (Instant) -> Unit,
    selectedStartDay: LocalDate,
    selectedStartTime: LocalTime,
    selectedEndDay: LocalDate,
    selectedEndTime: LocalTime,
) {
    /**
     * TODO - Everything about this is stupid and awful
     */
    val timeZone = TimeZone.currentSystemDefault()

    fun onStartDayChange(day: LocalDate) {
        val newLocalDateTime = LocalDateTime(day, selectedStartTime)
        val newInstant = newLocalDateTime.toInstant(timeZone)
        changeStartDateTime(newInstant)
    }

    fun onEndDayChange(day: LocalDate) {
        val newLocalDateTime = LocalDateTime(day, selectedEndTime)
        val newInstant = newLocalDateTime.toInstant(timeZone)
        changeEndDateTime(newInstant)
    }

    fun onStartTimeChange(time: LocalTime) {
        val newLocalDateTime = LocalDateTime(selectedStartDay, time)
        val newInstant = newLocalDateTime.toInstant(timeZone)
        changeStartDateTime(newInstant)
    }

    fun onEndTimeChange(time: LocalTime) {
        val newLocalDateTime = LocalDateTime(selectedEndDay, time)
        val newInstant = newLocalDateTime.toInstant(timeZone)
        changeEndDateTime(newInstant)
    }

    Row {
        Column(modifier = Modifier.weight(1f)) {
            Row(modifier = Modifier.weight(1f)) {
                Text("Start: ")
            }
            Row(modifier = Modifier.weight(1f)) {
                Text("End: ")
            }
        }
        Column(modifier = Modifier.weight(4f)) {
            Row(modifier = Modifier.weight(1f)) {
                DatePicker(enteredDate = selectedStartDay, onChangeDate = ::onStartDayChange)
            }
            Row(modifier = Modifier.weight(1f)) {
                DatePicker(enteredDate = selectedEndDay, onChangeDate = ::onEndDayChange)
            }
        }
        Column(modifier = Modifier.weight(2f)) {
            Row(modifier = Modifier.weight(1f)) {
                TimePicker(
                    enteredTime = selectedStartTime,
                    onChangeTime = ::onStartTimeChange,
                )
            }
            Row(modifier = Modifier.weight(1f)) {
                TimePicker(
                    enteredTime = selectedEndTime,
                    onChangeTime = ::onEndTimeChange,
                )
            }
        }
    }
}

@Composable
fun DatePicker(
    enteredDate: LocalDate,
    onChangeDate: (LocalDate) -> Unit,
) {
    val context = LocalContext.current
    val startDatePicker = DatePickerDialog(
        context, { _, year, month, day ->
            onChangeDate(LocalDate(year = year, monthNumber = month, dayOfMonth = day))
        }, enteredDate.year, enteredDate.monthNumber, enteredDate.dayOfMonth
    )
    Button(onClick = { startDatePicker.show() }) {
        Row {
            Text("$enteredDate")
        }
    }
}

@Composable
fun TimePicker(
    enteredTime: LocalTime,
    onChangeTime: (LocalTime) -> Unit,
) {
    val context = LocalContext.current
    val startTimePicker = TimePickerDialog(
        context, { _, hourOfDay, minute ->
            onChangeTime(LocalTime(hour = hourOfDay, minute = minute))
        }, enteredTime.hour, enteredTime.minute, true
    )
    Button(onClick = { startTimePicker.show() }) {
        Row {
            Text("$enteredTime")
        }
    }
}

@Preview
@Composable
fun PreviewNewEvent(

) {
    NewEventMainNoViewModel(
        uiState = NewEventUiState(
            "",
            "",
            "",
            Clock.System.now(),
            Clock.System.now(),
            loading = false
        ),
        onChangeStartDate = {},
        onChangeEndDate = {},
        navigateBack = {},
    )
}