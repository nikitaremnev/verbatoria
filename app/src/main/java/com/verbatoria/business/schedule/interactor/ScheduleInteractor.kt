package com.verbatoria.business.schedule.interactor

import com.verbatoria.domain.schedule.ScheduleDataSource
import com.verbatoria.domain.schedule.ScheduleDataSourceImpl
import com.verbatoria.infrastructure.extensions.formatWithMillisecondsAndZeroOffset
import com.verbatoria.infrastructure.retrofit.endpoints.schedule.ScheduleEndpoint
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import io.reactivex.Single

/**
 * @author n.remnev
 */

interface ScheduleInteractor {

    fun getSchedule(): Single<ScheduleDataSource>

}

class ScheduleInteractorImpl(
    private val scheduleEndpoint: ScheduleEndpoint,
    private val schedulersFactory: RxSchedulersFactory
) : ScheduleInteractor {

    override fun getSchedule(): Single<ScheduleDataSource> =
        Single.fromCallable {
            val scheduleDataSource: ScheduleDataSource = ScheduleDataSourceImpl()
            scheduleEndpoint.getSchedule(
                fromTime = scheduleDataSource.getFirstDayOfCurrentWeek().formatWithMillisecondsAndZeroOffset(),
                toTime = scheduleDataSource.getLastDayOfCurrentWeek().formatWithMillisecondsAndZeroOffset()
            )
            scheduleDataSource
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

}