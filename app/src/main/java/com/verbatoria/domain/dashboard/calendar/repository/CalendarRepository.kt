package com.verbatoria.domain.dashboard.calendar.repository

import android.content.SharedPreferences
import java.util.*

/**
 * @author n.remnev
 */

private const val LAST_SELECTED_DATE_KEY = "last_selected_date"

interface CalendarRepository {

    fun putLastSelectedDate(lastSelectedDate: Date)

    fun getLastSelectedDate(): Date

}

class CalendarRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : CalendarRepository {

    override fun putLastSelectedDate(lastSelectedDate: Date) {
        sharedPreferences.edit().apply {
            putLong(LAST_SELECTED_DATE_KEY, lastSelectedDate.time)
            apply()
        }
    }

    override fun getLastSelectedDate(): Date =
        Date(sharedPreferences.getLong(LAST_SELECTED_DATE_KEY, System.currentTimeMillis()))

}