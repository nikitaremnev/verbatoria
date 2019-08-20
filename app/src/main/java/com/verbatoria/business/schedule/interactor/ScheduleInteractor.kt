package com.verbatoria.business.schedule.interactor

import com.verbatoria.domain.schedule.ScheduleDataSource
import com.verbatoria.domain.schedule.ScheduleDataSourceImpl
import com.verbatoria.infrastructure.extensions.*
import com.verbatoria.infrastructure.retrofit.endpoints.schedule.ScheduleEndpoint
import com.verbatoria.infrastructure.retrofit.endpoints.schedule.model.params.ClearScheduleParamsDto
import com.verbatoria.infrastructure.retrofit.endpoints.schedule.model.params.SaveScheduleParamsDto
import com.verbatoria.infrastructure.retrofit.endpoints.schedule.model.params.ScheduleItemParamsDto
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import com.verbatoria.utils.LocaleHelper.LOCALE_RU
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*

/**
 * @author n.remnev
 */

interface ScheduleInteractor {

    fun getSchedule(): Single<ScheduleDataSource>

    fun saveSchedule(scheduleDataSource: ScheduleDataSource): Completable

    fun clearSchedule(scheduleDataSource: ScheduleDataSource): Completable

}

class ScheduleInteractorImpl(
    private val scheduleEndpoint: ScheduleEndpoint,
    private val schedulersFactory: RxSchedulersFactory
) : ScheduleInteractor {

    override fun getSchedule(): Single<ScheduleDataSource> =
        Single.fromCallable {
            val scheduleDataSource: ScheduleDataSource = ScheduleDataSourceImpl()
            val scheduleResponse = scheduleEndpoint.getSchedule(
                fromTime = scheduleDataSource.getFirstDayOfCurrentWeek().formatWithMillisecondsAndZeroOffset(),
                toTime = scheduleDataSource.getLastDayOfCurrentWeek().formatWithMillisecondsAndZeroOffset()
            )
            scheduleResponse.scheduleItems.forEach { scheduleItemResponse ->
                scheduleDataSource.setWorkingInterval(scheduleItemResponse.fromTime.parseServerFormat())
            }
            scheduleDataSource
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun saveSchedule(scheduleDataSource: ScheduleDataSource): Completable =
        Completable.fromCallable {
            val currentSchedule = scheduleDataSource.getSchedule()
            val scheduleItemsToSave = ArrayList<ScheduleItemParamsDto>()

            val calendar = Calendar.getInstance(Locale(LOCALE_RU)).apply {
                time = scheduleDataSource.getFirstDayOfCurrentWeek()
            }

            val currentScheduleKeysSorted = currentSchedule.keys.sorted()

            for (index in currentScheduleKeysSorted) {
                calendar.dropToStartOfTheDay()
                val subItems = currentSchedule[index]
                subItems?.forEach { scheduleCellItem ->
                    if (scheduleCellItem.isSelected) {
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
                    }
                }
                calendar.timeInMillis += MILLISECONDS_IN_DAY
            }

            clearScheduleForCurrentWeek(scheduleDataSource)

            scheduleEndpoint.saveSchedule(
                SaveScheduleParamsDto(
                    scheduleItems = scheduleItemsToSave
                )
            )
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun clearSchedule(scheduleDataSource: ScheduleDataSource): Completable =
        Completable.fromCallable {
            clearScheduleForCurrentWeek(scheduleDataSource)
            scheduleDataSource.clear()
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    private fun clearScheduleForCurrentWeek(scheduleDataSource: ScheduleDataSource) {
        scheduleEndpoint.clearSchedule(
            ClearScheduleParamsDto(
                fromTime = scheduleDataSource.getFirstDayOfCurrentWeek().formatWithMillisecondsAndZeroOffset(),
                toTime = scheduleDataSource.getLastDayOfCurrentWeek().formatWithMillisecondsAndZeroOffset()
            )
        )
    }

}