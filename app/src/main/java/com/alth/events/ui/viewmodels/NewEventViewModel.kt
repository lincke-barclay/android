package com.alth.events.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alth.events.exceptions.IllegalOperationException
import com.alth.events.networking.models.events.egress.POSTEventRequestDTO
import com.alth.events.networking.sources.NetworkEventDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.util.Calendar
import javax.inject.Inject

data class NewEventUiState(
    val enteredTitle: String,
    val enteredShortDescription: String,
    val enteredLongDescription: String,
    val enteredStartDate: Instant,
    val enteredEndDate: Instant,
    val loading: Boolean,
) {
    val canSubmit
        get() = enteredTitle.isNotEmpty() &&
                enteredLongDescription.isNotEmpty() &&
                enteredLongDescription.isNotEmpty()

    private val timeZone = TimeZone.currentSystemDefault()
    private val startDateTime get() = enteredStartDate.toLocalDateTime(timeZone)
    private val endDateTime get() = enteredEndDate.toLocalDateTime(timeZone)

    val startDate
        get() = LocalDate(
            year = startDateTime.year,
            monthNumber = startDateTime.monthNumber,
            dayOfMonth = startDateTime.dayOfMonth,
        )
    val endDate
        get() = LocalDate(
            year = endDateTime.year,
            monthNumber = endDateTime.monthNumber,
            dayOfMonth = endDateTime.dayOfMonth,
        )

    val startTime get() = startDateTime.time
    val endTime get() = endDateTime.time

    companion object {
        fun initial(): NewEventUiState {
            // Screw you java dates
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.HOUR, 1)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            val defaultStartDate = Instant.fromEpochMilliseconds(calendar.time.time)
            calendar.add(Calendar.HOUR, 1)
            val defaultEndDate = Instant.fromEpochMilliseconds(calendar.time.time)
            return NewEventUiState("", "", "", defaultStartDate, defaultEndDate, false)
        }
    }
}

@HiltViewModel
class NewEventViewModel @Inject constructor(
    private val networkEventDataSource: NetworkEventDataSource,
) : ViewModel() {
    private val _uiState = MutableStateFlow(NewEventUiState.initial())
    val uiState = _uiState.asStateFlow()

    fun submit(onComplete: () -> Unit) {
        if (uiState.value.canSubmit) {
            viewModelScope.launch {
                _uiState.value = uiState.value.copy(loading = true)
                networkEventDataSource.createEvent(uiState.value.toPOSTEventRequest())
                _uiState.value = uiState.value.copy(loading = false)
                onComplete()
            }
        } else {
            throw IllegalOperationException(
                "Can't call submit when data is" +
                        "empty! Check that the ui guards submit on canSubmit!"
            )
        }
    }

    fun changeTitle(newTitle: String) {
        _uiState.value = uiState.value.copy(enteredTitle = newTitle)
    }

    fun changeShortDesc(newShortDesc: String) {
        _uiState.value = uiState.value.copy(enteredShortDescription = newShortDesc)
    }

    fun changeLongDesc(newLongDesc: String) {
        _uiState.value = uiState.value.copy(enteredLongDescription = newLongDesc)
    }

    fun changeStartDate(date: Instant) {
        val difference = date - uiState.value.enteredStartDate
        val newEndDate = uiState.value.enteredEndDate.plus(difference)
        Log.d("START DATE", date.toString())
        Log.d("End Date", uiState.value.enteredEndDate.toString())
        _uiState.value = uiState.value.copy(
            enteredStartDate = date,
            enteredEndDate = newEndDate
        )
    }

    fun changeEndDate(date: Instant) {
        Log.d("END DATE", date.toString())
        Log.d("START DATE", uiState.value.enteredStartDate.toString())
        if (date < uiState.value.enteredStartDate) {
            TODO("Handle when entered date is less than start date")
        }
        _uiState.value = uiState.value.copy(enteredEndDate = date)
    }
}

fun NewEventUiState.toPOSTEventRequest() = POSTEventRequestDTO(
    title = enteredTitle,
    shortDescription = enteredShortDescription,
    longDescription = enteredLongDescription,
    startingDateTime = enteredStartDate,
    endingDateTime = enteredEndDate,
)
