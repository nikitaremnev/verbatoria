package com.verbatoria.business.schedule

import com.verbatoria.domain.schedule.model.ScheduleDataSource
import com.verbatoria.domain.schedule.manager.ScheduleManager
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import io.reactivex.Completable
import io.reactivex.Single

/**
 * @author n.remnev
 */

interface ScheduleInteractor {

    fun getSchedule(): Single<ScheduleDataSource>

    fun getScheduleForNextWeek(currentScheduleDataSource: ScheduleDataSource): Single<ScheduleDataSource>

    fun getScheduleForPreviousWeek(currentScheduleDataSource: ScheduleDataSource): Single<ScheduleDataSource>

    fun saveSchedule(scheduleDataSource: ScheduleDataSource, weeksForward: Int): Completable

    fun clearSchedule(scheduleDataSource: ScheduleDataSource): Completable

}

class ScheduleInteractorImpl(
    private val scheduleManager: ScheduleManager,
    private val schedulersFactory: RxSchedulersFactory
) : ScheduleInteractor {

    override fun getSchedule(): Single<ScheduleDataSource> =
        Single.fromCallable {
            scheduleManager.loadSchedule()
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun getScheduleForNextWeek(currentScheduleDataSource: ScheduleDataSource): Single<ScheduleDataSource> =
        Single.fromCallable {
            scheduleManager.loadScheduleForNextWeek(currentScheduleDataSource)
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun getScheduleForPreviousWeek(currentScheduleDataSource: ScheduleDataSource): Single<ScheduleDataSource> =
        Single.fromCallable {
            scheduleManager.loadSchedulePreviousWeek(currentScheduleDataSource)
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun saveSchedule(scheduleDataSource: ScheduleDataSource, weeksForward: Int): Completable =
        Completable.fromCallable {
            scheduleManager.saveSchedule(scheduleDataSource, weeksForward)
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun clearSchedule(scheduleDataSource: ScheduleDataSource): Completable =
        Completable.fromCallable {
            scheduleManager.clearSchedule(scheduleDataSource)
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

}