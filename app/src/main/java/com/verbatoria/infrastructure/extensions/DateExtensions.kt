package com.verbatoria.infrastructure.extensions

import java.text.SimpleDateFormat
import java.util.*

/***
 * @author n.remnev
 */

const val FORMAT_WITH_MILLISECONDS_AND_ZERO_OFFSET = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
const val SERVER_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ"
const val DATE_WITH_FULL_MONTH_FORMAT = "d MMMM"
const val CLIENT_BIRTHDAY_FORMAT = "yyyy-MM-dd"
const val FORMAT_TIME_SHORT = "HH:mm"
const val FORMAT_DATE_MONTH_AND_TIME_SHORT = "d MMMM HH:mm"
const val MILLISECONDS_IN_DAY = 1000 * 60 * 60 * 24
const val MILLISECONDS_IN_YEAR = 1000 * 60 * 60 * 24 * 365L

fun String.parseWithMillisecondsAndZeroOffset(): Date {
    val formatter = SimpleDateFormat(FORMAT_WITH_MILLISECONDS_AND_ZERO_OFFSET, Locale.getDefault())
    return formatter.parse(this)
}

fun String.parseBirthdayFormat(): Date {
    val formatter = SimpleDateFormat(CLIENT_BIRTHDAY_FORMAT, Locale.getDefault())
    return formatter.parse(this)
}

fun String.parseServerFormat(): Date {
    val formatter = SimpleDateFormat(SERVER_DATETIME_FORMAT, Locale.getDefault())
    return formatter.parse(this)
}

fun Date.formatWithMillisecondsAndZeroOffset(): String {
    val formatter = SimpleDateFormat(FORMAT_WITH_MILLISECONDS_AND_ZERO_OFFSET, Locale.getDefault())
    return formatter.format(this)
}

fun Date.formatToDateWithFullMonth(): String =
    format(
        this,
        DATE_WITH_FULL_MONTH_FORMAT
    )

fun Date.formatToServerTime(): String =
    format(
        this,
        SERVER_DATETIME_FORMAT
    )

fun Date.formatToTime(): String =
    format(
        this,
        FORMAT_TIME_SHORT
    )

fun Date.formatToDateMonthAndTime(): String =
    format(
        this,
        FORMAT_DATE_MONTH_AND_TIME_SHORT
    )

fun Date.plusDay() {
    time += MILLISECONDS_IN_DAY
}

fun Date.minusDay() {
    time -= MILLISECONDS_IN_DAY
}

fun Date.getYearsForCurrentMoment(): Int =
    ((System.currentTimeMillis() - time) / MILLISECONDS_IN_YEAR).toInt()

fun Date.toStartDay(): Date {
    val calendar = toCalendar()
        .apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
    time = calendar.time.time
    return this
}

fun Date.toEndDay(): Date {
    val calendar = toCalendar()
        .apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }

    time = calendar.time.time

    return this
}


fun Date.isToday(): Boolean {
    val thisCalendar = toCalendar()
    val calendar = createCalendar()
    return (calendar.get(Calendar.YEAR) == thisCalendar.get(Calendar.YEAR)
            && calendar.get(Calendar.DAY_OF_YEAR) == thisCalendar.get(Calendar.DAY_OF_YEAR))
}

fun Date.isTomorrow(): Boolean {
    val thisCalendar = toCalendar()
    val calendar = createCalendar()
    calendar.add(Calendar.DAY_OF_MONTH, 1)
    return (calendar.get(Calendar.YEAR) == thisCalendar.get(Calendar.YEAR)
            && calendar.get(Calendar.DAY_OF_YEAR) == thisCalendar.get(Calendar.DAY_OF_YEAR))
}

fun Date.isYesterday(): Boolean {
    val thisCalendar = toCalendar()
    val calendar = createCalendar()
    calendar.add(Calendar.DAY_OF_MONTH, -1)
    return (calendar.get(Calendar.YEAR) == thisCalendar.get(Calendar.YEAR)
            && calendar.get(Calendar.DAY_OF_YEAR) == thisCalendar.get(Calendar.DAY_OF_YEAR))
}

private fun format(date: Date, pattern: String): String {
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return formatter.format(date)
}

private fun parse(time: String, pattern: String): Date {
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return formatter.parse(time)
}

fun Date.toCalendar() = createCalendarFromDate(this)

fun createCalendarFromDate(date: Date = createDate()): Calendar =
    createCalendar().apply { time = date }

fun createDate(): Date = createCalendar().time

fun createCalendar(): Calendar = Calendar.getInstance()
