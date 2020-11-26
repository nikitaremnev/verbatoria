package com.verbatoria.infrastructure.extensions

import com.verbatoria.business.dashboard.LocalesAvailable
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/***
 * @author n.remnev
 */

const val FORMAT_WITH_MILLISECONDS_AND_ZERO_OFFSET = "yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ"
const val SERVER_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ"
const val DATE_WITH_FULL_MONTH_FORMAT = "d MMMM"
const val CLIENT_BIRTHDAY_FORMAT = "yyyy-MM-dd"
const val FORMAT_TIME_SHORT = "HH:mm"
const val FORMAT_TIME_TIMER = "mm:ss"
const val FORMAT_DATE_SHORT = "dd.MM"
const val FORMAT_DATE_MONTH_AND_TIME_SHORT = "d MMMM HH:mm"
const val DAYS_IN_WEEK = 7
const val MILLISECONDS_IN_SECOND = 1000
const val MILLISECONDS_IN_DAY = MILLISECONDS_IN_SECOND * 60 * 60 * 24L
const val MILLISECONDS_IN_YEAR = MILLISECONDS_IN_SECOND * 60 * 60 * 24 * 365L

fun String.parseWithMillisecondsAndZeroOffset(): Date {
    val formatter = SimpleDateFormat(FORMAT_WITH_MILLISECONDS_AND_ZERO_OFFSET, getLocale())
    return formatter.parse(this)
}

fun String.parseBirthdayFormat(): Date {
    val formatter = SimpleDateFormat(CLIENT_BIRTHDAY_FORMAT, getLocale())
    return formatter.parse(this)
}

fun String.parseServerFormat(): Date {
    val formatter = SimpleDateFormat(SERVER_DATETIME_FORMAT, getLocale())
    return formatter.parse(this)
}

fun Calendar.dropToStartOfTheDay(): Calendar {
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
    return this
}

fun Calendar.dropToEndOfTheDay(): Calendar {
    set(Calendar.HOUR_OF_DAY, 23)
    set(Calendar.MINUTE, 59)
    set(Calendar.SECOND, 59)
    set(Calendar.MILLISECOND, 999)
    return this
}

fun Calendar.setHour(hour: Int) {
    set(Calendar.HOUR_OF_DAY, hour)
}

fun Int.birthdayDateFromAge(): Date {
    val calendar = Calendar.getInstance(getLocale())
    calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - this)
    calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - DAYS_IN_WEEK)
    return calendar.time
}

fun Date.formatWithMillisecondsAndZeroOffset(): String {
    val formatter = SimpleDateFormat(FORMAT_WITH_MILLISECONDS_AND_ZERO_OFFSET, getLocale())
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

fun Date.formatToShortDate(): String =
    format(
        this,
        FORMAT_DATE_SHORT
    )

fun Date.formatToTimerTime(): String =
    format(
        this,
        FORMAT_TIME_TIMER
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

fun Calendar.plusDays(days: Int) {
    set(Calendar.DAY_OF_YEAR, get(Calendar.DAY_OF_YEAR) + days)
}

fun Calendar.minusDays(days: Int) {
    set(Calendar.DAY_OF_YEAR, get(Calendar.DAY_OF_YEAR) - days)
}

fun Long.millisecondsToSeconds(): Int =
    TimeUnit.MILLISECONDS.toSeconds(this).toInt()

private fun format(date: Date, pattern: String): String {
    val formatter = SimpleDateFormat(pattern, getLocale())
    return formatter.format(date)
}

fun Date.toCalendar() = createCalendarFromDate(this)

fun createCalendarFromDate(date: Date = createDate()): Calendar =
    createCalendar().apply { time = date }

fun createDate(): Date = createCalendar().time

fun createCalendar(): Calendar = Calendar.getInstance(getLocale())

private fun getLocale(): Locale {
    val defaultLocale = Locale.getDefault()
    if (defaultLocale.language == LocalesAvailable.ARABIC_LOCALE) {
        return Locale.ENGLISH
    }
    return defaultLocale
}