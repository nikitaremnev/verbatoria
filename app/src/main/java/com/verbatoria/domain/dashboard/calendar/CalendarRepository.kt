package com.verbatoria.domain.dashboard.calendar

import android.content.SharedPreferences
import java.util.*

/**
 * @author n.remnev
 */

private const val SELECTED_DATE_KEY = "selected_date"

interface CalendarRepository {

    fun putSelectedDate(selectedDate: Date)

    fun getSelectedDate(): Date

}

class CalendarRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : CalendarRepository {

    override fun putSelectedDate(selectedDate: Date) {
        sharedPreferences.edit().apply {
            putLong(SELECTED_DATE_KEY, selectedDate.time)
            apply()
        }
    }

    override fun getSelectedDate(): Date =
        Date(sharedPreferences.getLong(SELECTED_DATE_KEY, System.currentTimeMillis()))

}