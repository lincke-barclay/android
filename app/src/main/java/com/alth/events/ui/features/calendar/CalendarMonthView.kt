package com.alth.events.ui.features.calendar

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alth.events.logging.impl.loggerFactory
import com.alth.events.util.getCompleteDateRangeForMonth
import com.alth.events.util.getCurrentLocalDate
import com.alth.events.util.getCurrentMonth
import com.alth.events.util.getCurrentYear
import com.alth.events.util.getNumWeeksForCalendarView
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month

@Composable
fun CalendarMonthView(
    landingMonth: Month = getCurrentMonth(),
    landingYear: Int = getCurrentYear(),
    modifier: Modifier = Modifier,
    contentOnDay: @Composable (LocalDate) -> Unit,
) {

    val rows = getNumWeeksForCalendarView(landingMonth, landingYear)
    loggerFactory.getLogger("DateTime").debug(rows.toString())
    val days = getCompleteDateRangeForMonth(landingMonth, landingYear)
    loggerFactory.getLogger("DateTime").debug(days.toString())

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        (0..<rows).forEach { week ->
            Row(
                modifier = Modifier
                    .weight(1f)
            ) {
                (0..<7).forEach { day ->
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        DateView(
                            date = days[week * 7 + day],
                            modifier = Modifier.fillMaxSize(),
                            contentOnDay,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DateView(
    date: LocalDate,
    modifier: Modifier = Modifier,
    content: @Composable (LocalDate) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .border(width = 0.5.dp, color = Color.Black),
    ) {
        Text(
            modifier = Modifier
                .padding(16.dp)
                .then(
                    if (date == getCurrentLocalDate()) {
                        Modifier.drawBehind {
                            drawCircle(
                                color = Color.Red,
                                radius = this.size.height / 2
                            )
                        }
                    } else {
                        Modifier
                    }
                ),
            text = date.dayOfMonth.toString(),
            color = Color.White,
        )
        content(date)
    }
}
