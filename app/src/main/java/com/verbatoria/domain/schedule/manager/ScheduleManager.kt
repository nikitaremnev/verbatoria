package com.verbatoria.domain.schedule.manager

import com.verbatoria.domain.schedule.model.ScheduleDataSource
import com.verbatoria.domain.schedule.model.ScheduleDataSourceImpl
import com.verbatoria.domain.schedule.model.TimeSlot
import com.verbatoria.infrastructure.extensions.*
import com.verbatoria.infrastructure.retrofit.endpoints.schedule.ScheduleEndpoint
import com.verbatoria.infrastructure.retrofit.endpoints.schedule.model.params.ClearScheduleParamsDto
import com.verbatoria.infrastructure.retrofit.endpoints.schedule.model.params.SaveScheduleParamsDto
import com.verbatoria.infrastructure.retrofit.endpoints.schedule.model.params.ScheduleItemParamsDto
import com.verbatoria.utils.LocaleHelper
import java.util.*

/**
 * @author n.remnev
 */

interface ScheduleManager {

    fun loadSchedule(): ScheduleDataSource

    fun loadScheduleForDay(date: Date): List<TimeSlot>

    fun loadScheduleForNextWeek(currentScheduleDataSource: ScheduleDataSource): ScheduleDataSource

    fun loadSchedulePreviousWeek(currentScheduleDataSource: ScheduleDataSource): ScheduleDataSource

    fun saveSchedule(scheduleDataSource: ScheduleDataSource, weeksForward: Int)

    fun clearSchedule(scheduleDataSource: ScheduleDataSource)

}

class ScheduleManagerImpl(
    private val scheduleEndpoint: ScheduleEndpoint
) : ScheduleManager {

    override fun loadSchedule(): ScheduleDataSource {
        val scheduleDataSource: ScheduleDataSource =
            ScheduleDataSourceImpl()
        return loadSchedule(scheduleDataSource)
    }

    override fun loadScheduleForDay(date: Date): List<TimeSlot> {
        val calendar = date.toCalendar()
        val scheduleResponse = scheduleEndpoint.getSchedule(
            fromTime = calendar.dropToStartOfTheDay().time.formatWithMillisecondsAndZeroOffset(),
            toTime = calendar.dropToEndOfTheDay().time.formatWithMillisecondsAndZeroOffset()
        )
        return scheduleResponse.scheduleItems.map { scheduleItemResponse ->
            TimeSlot(
                startTime = scheduleItemResponse.fromTime.parseServerFormat(),
                endTime = scheduleItemResponse.toTime.parseServerFormat()
            )
        }
    }

    override fun loadScheduleForNextWeek(currentScheduleDataSource: ScheduleDataSource): ScheduleDataSource {
        val firstDayOfNextWeek = currentScheduleDataSource.getFirstDayOfNextWeek()
        val scheduleDataSource: ScheduleDataSource =
            ScheduleDataSourceImpl(Calendar.getInstance().apply {
                time = firstDayOfNextWeek
            })
        return loadSchedule(scheduleDataSource)
    }

    override fun loadSchedulePreviousWeek(currentScheduleDataSource: ScheduleDataSource): ScheduleDataSource {
        val firstDayOfPreviousWeek = currentScheduleDataSource.getFirstDayOfPreviousWeek()
        val scheduleDataSource: ScheduleDataSource =
            ScheduleDataSourceImpl(Calendar.getInstance().apply {
                time = firstDayOfPreviousWeek
            })
        return loadSchedule(scheduleDataSource)
    }

    override fun saveSchedule(scheduleDataSource: ScheduleDataSource, weeksForward: Int) {
        val currentSchedule = scheduleDataSource.getSchedule()
        val scheduleItemsToSave = mutableListOf<ScheduleItemParamsDto>()

        val calendar = Calendar.getInstance(Locale(LocaleHelper.LOCALE_RU)).apply {
            time = scheduleDataSource.getFirstDayOfCurrentWeek()
        }

        val currentScheduleKeysSorted = currentSchedule.keys.sorted()

        for (index in currentScheduleKeysSorted) {
            calendar.dropToStartOfTheDay()
            val subItems = currentSchedule[index]
            subItems?.forEach { scheduleCellItem ->
                if (scheduleCellItem.isSelected) {
                    for (week in 0..weeksForward) {
                        calendar.plusDays(DAYS_IN_WEEK * week)
                        calendar.setHour(scheduleCellItem.startHour)
                        val startDate = calendar.time
                        calendar.setHour(scheduleCellItem.startHour + 1)
                        val endDate = calendar.time
                        scheduleItemsToSave.add(
                            ScheduleItemParamsDto(
                                fromTime = startDate.formatWithMillisecondsAndZeroOffset(),
                                toTime = endDate.formatWithMillisecondsAndZeroOffset()
                            )
                        )
                        calendar.minusDays(DAYS_IN_WEEK * week)
                    }
                }
            }
            calendar.timeInMillis += MILLISECONDS_IN_DAY
        }

        scheduleEndpoint.clearSchedule(
            ClearScheduleParamsDto(
                fromTime = scheduleDataSource.getFirstDayOfCurrentWeek().formatWithMillisecondsAndZeroOffset(),
                toTime = scheduleDataSource.getLastDayOfCurrentWeek()
                    .toCalendar().apply {
                        plusDays(DAYS_IN_WEEK * weeksForward)
                    }
                    .time
                    .formatWithMillisecondsAndZeroOffset()
            )
        )

        scheduleEndpoint.saveSchedule(
            SaveScheduleParamsDto(
                scheduleItems = scheduleItemsToSave
            )
        )
    }

    override fun clearSchedule(scheduleDataSource: ScheduleDataSource) {
        scheduleEndpoint.clearSchedule(
            ClearScheduleParamsDto(
                fromTime = scheduleDataSource.getFirstDayOfCurrentWeek().formatWithMillisecondsAndZeroOffset(),
                toTime = scheduleDataSource.getLastDayOfCurrentWeek().formatWithMillisecondsAndZeroOffset()
            )
        )
        scheduleDataSource.clear()
    }

    private fun loadSchedule(scheduleDataSource: ScheduleDataSource): ScheduleDataSource {
        val scheduleResponse = scheduleEndpoint.getSchedule(
            fromTime = scheduleDataSource.getFirstDayOfCurrentWeek().formatWithMillisecondsAndZeroOffset(),
            toTime = scheduleDataSource.getLastDayOfCurrentWeek().formatWithMillisecondsAndZeroOffset()
        )
        scheduleResponse.scheduleItems.forEach { scheduleItemResponse ->
            scheduleDataSource.setWorkingInterval(scheduleItemResponse.fromTime.parseServerFormat())
        }
        return scheduleDataSource
    }

}