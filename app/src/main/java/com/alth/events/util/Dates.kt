package com.alth.events.util

import com.alth.events.logging.impl.loggerFactory
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.until
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit
import kotlin.math.ceil

fun currentInstant(): Instant {
    return Clock.System.now()
}

fun getCurrentLocalDate(
    timeZone: TimeZone = TimeZone.currentSystemDefault(),
): LocalDate {
    return currentInstant().toLocalDateTime(timeZone).date
}

fun getCurrentMonth(
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): Month {
    return currentInstant()
        .toLocalDateTime(timeZone)
        .month
}

fun getCurrentYear(
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): Int {
    return currentInstant()
        .toLocalDateTime(timeZone)
        .year
}

fun getFirstDayOfLandingMonthYear(
    month: Month = getCurrentMonth(),
    year: Int = getCurrentYear()
): LocalDate {
    return LocalDate(year, month, 1)
}

fun getMondayBeforeOrOnFirstDayOfMonth(
    month: Month = getCurrentMonth(),
    year: Int = getCurrentYear(),
): LocalDate {
    val firstDay = getFirstDayOfLandingMonthYear(month, year)
    val subtractDays = 7 - getFirstDayOfLandingMonthYear(month, year).dayOfWeek.value
    return firstDay.minus(DatePeriod(days = subtractDays))
}

fun getNumberOfDays(month: Month, year: Int): Int {
    val firstDay = getFirstDayOfLandingMonthYear(month, year)
    val yearUpdated = if (month == Month.DECEMBER) {
        year + 1
    } else {
        year
    }

    val firstDayOfNextMonth = getFirstDayOfLandingMonthYear(month.plus(1), yearUpdated)

    loggerFactory.getLogger("Herehere").debug("${(firstDayOfNextMonth - firstDay).months}")

    return firstDay.until(firstDayOfNextMonth, DateTimeUnit.DAY)
}

fun getNumWeeksForCalendarView(
    month: Month,
    year: Int,
): Int {
    val numDaysOfTheMonth = getNumberOfDays(month, year)
    return ceil(numDaysOfTheMonth / 7.0).toInt()
}

fun getCompleteDateRangeForMonth(
    month: Month = getCurrentMonth(),
    year: Int = getCurrentYear(),
): List<LocalDate> {
    val firstDay = getMondayBeforeOrOnFirstDayOfMonth(month, year)
    val numDays = getNumWeeksForCalendarView(month, year) * 7
    return (0..<numDays).map { firstDay.plus(DatePeriod(days = it)) }
}

