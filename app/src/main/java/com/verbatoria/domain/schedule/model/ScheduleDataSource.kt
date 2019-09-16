package com.verbatoria.domain.schedule.model

import com.verbatoria.business.dashboard.LocalesAvailable
import com.verbatoria.domain.schedule.model.ScheduleDataSource.Companion.COLUMN_COUNT
import com.verbatoria.domain.schedule.model.ScheduleDataSource.Companion.ROWS_COUNT
import com.verbatoria.infrastructure.extensions.dropToStartOfTheDay
import com.verbatoria.infrastructure.extensions.formatToShortDate
import java.util.*

/**
 * @author n.remnev
 */

private const val START_HOUR = 8
private const val END_HOUR = 19
private const val WEEK_COUNT = 7
private const val FIRST_DAY_OF_WEEK = 1

interface ScheduleDataSource {

    companion object {

        const val ROWS_COUNT = 8

        const val COLUMN_COUNT = 12

    }

    fun getRowsCount(): Int

    fun getColumnsCount(): Int

    fun getScheduleCellItem(row: Int, column: Int): ScheduleCellItem?

    fun getFirstDayOfNextWeek(): Date

    fun getLastDayOfNextWeek(): Date

    fun getFirstDayOfCurrentWeek(): Date

    fun getLastDayOfCurrentWeek(): Date

    fun getFirstDayOfPreviousWeek(): Date

    fun getLastDayOfPreviousWeek(): Date

    fun getCurrentWeekTitle(): String

    fun getRowHeaderDate(row: Int): Date

    fun getColumnHeaderDate(column: Int): Date

    fun getSchedule(): Map<Int, List<ScheduleCellItem>>

    fun setWorkingInterval(date: Date)

    fun clear()

}

class ScheduleDataSourceImpl : ScheduleDataSource {

    private var originalCalendar: Calendar
    private var currentCalendar: Calendar = Calendar.getInstance(Locale(LocalesAvailable.RUSSIAN_LOCALE))

    private var scheduleItems: MutableMap<Int, List<ScheduleCellItem>> = hashMapOf()

    constructor() {
        originalCalendar = Calendar.getInstance(Locale(LocalesAvailable.RUSSIAN_LOCALE))
        originalCalendar.dropToStartOfTheDay()

        var dayOfWeekIndex = 1
        while (dayOfWeekIndex < ROWS_COUNT) {
            val dayGenerated = mutableListOf<ScheduleCellItem>()
            for (i in START_HOUR until END_HOUR) {
                dayGenerated.add(
                    ScheduleCellItem(
                        startHour = i,
                        isSelected = false
                    )
                )
            }
            scheduleItems[dayOfWeekIndex] = dayGenerated
            dayOfWeekIndex++
        }
    }

    constructor(calendar: Calendar) {
        originalCalendar = calendar
        originalCalendar.dropToStartOfTheDay()

        var dayOfWeekIndex = 1
        while (dayOfWeekIndex < ROWS_COUNT) {
            val dayGenerated = mutableListOf<ScheduleCellItem>()
            for (i in START_HOUR until END_HOUR) {
                dayGenerated.add(
                    ScheduleCellItem(
                        startHour = i,
                        isSelected = false
                    )
                )
            }
            scheduleItems[dayOfWeekIndex] = dayGenerated
            dayOfWeekIndex++
        }
    }

    //region ScheduleDataSource

    override fun getRowsCount(): Int = ROWS_COUNT

    override fun getColumnsCount(): Int = COLUMN_COUNT

    override fun getScheduleCellItem(row: Int, column: Int): ScheduleCellItem? =
        scheduleItems[row]?.get(column - 1)

    override fun getFirstDayOfNextWeek(): Date {
        originalCalendar.set(
            Calendar.DAY_OF_YEAR,
            originalCalendar.get(Calendar.DAY_OF_YEAR) + WEEK_COUNT
        )
        val result = getFirstDayOfCurrentWeek()
        originalCalendar.set(
            Calendar.DAY_OF_YEAR,
            originalCalendar.get(Calendar.DAY_OF_YEAR) - WEEK_COUNT
        )
        return result

    }

    override fun getLastDayOfNextWeek(): Date {
        originalCalendar.set(
            Calendar.DAY_OF_YEAR,
            originalCalendar.get(Calendar.DAY_OF_YEAR) + WEEK_COUNT
        )
        val result = getLastDayOfCurrentWeek()
        originalCalendar.set(
            Calendar.DAY_OF_YEAR,
            originalCalendar.get(Calendar.DAY_OF_YEAR) - WEEK_COUNT
        )
        return result
    }

    override fun getFirstDayOfCurrentWeek(): Date {
        currentCalendar.time = originalCalendar.time
        val firstDayOfWeek = getFirstDayOfWeek()
        currentCalendar.dropToStartOfTheDay()
        currentCalendar.set(
            Calendar.DAY_OF_YEAR,
            currentCalendar.get(Calendar.DAY_OF_YEAR) - (getCurrentDayOfWeek() - firstDayOfWeek)
        )
        return currentCalendar.time
    }

    override fun getLastDayOfCurrentWeek(): Date {
        currentCalendar.time = originalCalendar.time
        val firstDayOfWeek = getFirstDayOfWeek()
        currentCalendar.dropToStartOfTheDay()
        currentCalendar.set(
            Calendar.DAY_OF_YEAR,
            currentCalendar.get(Calendar.DAY_OF_YEAR) - (getCurrentDayOfWeek() - firstDayOfWeek - WEEK_COUNT)
        )
        return currentCalendar.time
    }

    override fun getFirstDayOfPreviousWeek(): Date {
        originalCalendar.set(
            Calendar.DAY_OF_YEAR,
            originalCalendar.get(Calendar.DAY_OF_YEAR) - WEEK_COUNT
        )
        val result = getFirstDayOfCurrentWeek()
        originalCalendar.set(
            Calendar.DAY_OF_YEAR,
            originalCalendar.get(Calendar.DAY_OF_YEAR) + WEEK_COUNT
        )
        return result
    }

    override fun getLastDayOfPreviousWeek(): Date {
        originalCalendar.set(
            Calendar.DAY_OF_YEAR,
            originalCalendar.get(Calendar.DAY_OF_YEAR) - WEEK_COUNT
        )
        val result = getLastDayOfCurrentWeek()
        originalCalendar.set(
            Calendar.DAY_OF_YEAR,
            originalCalendar.get(Calendar.DAY_OF_YEAR) + WEEK_COUNT
        )
        return result
    }

    override fun getCurrentWeekTitle(): String =
        getRowHeaderDate(FIRST_DAY_OF_WEEK).formatToShortDate() + " - " +
                getRowHeaderDate(ROWS_COUNT - 1).formatToShortDate()

    override fun getRowHeaderDate(row: Int): Date {
        currentCalendar.time = originalCalendar.time
        val firstDayOfWeek = getFirstDayOfWeek()
        currentCalendar.set(
            Calendar.DAY_OF_YEAR,
            currentCalendar.get(Calendar.DAY_OF_YEAR) - (getCurrentDayOfWeek() - firstDayOfWeek - (row - 1))
        )
        return currentCalendar.time
    }

    override fun getColumnHeaderDate(column: Int): Date {
        currentCalendar.set(Calendar.HOUR_OF_DAY, START_HOUR + column - 1)
        currentCalendar.set(Calendar.MINUTE, 0)
        return currentCalendar.time
    }

    override fun getSchedule(): Map<Int, List<ScheduleCellItem>> =
        scheduleItems

    override fun setWorkingInterval(date: Date) {
        currentCalendar = Calendar.getInstance(Locale(LocalesAvailable.RUSSIAN_LOCALE))
        currentCalendar.time = date
        val currentHour = currentCalendar.get(Calendar.HOUR_OF_DAY)
        if (isHourInBorders(currentHour)) {
            scheduleItems[getCurrentDayOfWeek()]?.let { scheduleCellItemsList ->
                scheduleCellItemsList[currentHour - START_HOUR].isSelected = true
            }
        }
    }

    override fun clear() {
        var dayOfWeekIndex = FIRST_DAY_OF_WEEK
        while (dayOfWeekIndex < ROWS_COUNT) {
            scheduleItems[dayOfWeekIndex]?.let { scheduleItem ->
                for (i in scheduleItem.indices) {
                    scheduleItem[i].isSelected = false
                }
            }
            dayOfWeekIndex++
        }
    }

    //endregion

    private fun getFirstDayOfWeek(): Int = currentCalendar.firstDayOfWeek - 1

    private fun getCurrentDayOfWeek(): Int =
        if (currentCalendar.get(Calendar.DAY_OF_WEEK) == FIRST_DAY_OF_WEEK)
            WEEK_COUNT
        else
            currentCalendar.get(Calendar.DAY_OF_WEEK) - 1

    private fun isHourInBorders(hour: Int): Boolean = hour in START_HOUR until END_HOUR

}