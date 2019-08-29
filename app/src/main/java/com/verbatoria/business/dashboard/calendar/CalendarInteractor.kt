package com.verbatoria.business.dashboard.calendar

import com.verbatoria.business.dashboard.calendar.models.item.EventItemModel
import com.verbatoria.domain.dashboard.calendar.manager.CalendarManager
import com.verbatoria.domain.dashboard.calendar.model.Event
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*

/**
 * @author n.remnev
 */

interface CalendarInteractor {

    fun getLastSelectedDate(): Single<Date>

    fun saveLastSelectedDate(selectedDate: Date): Completable

    fun getEventsForDate(date: Date): Single<Pair<List<Event>, List<EventItemModel>>>

}

class CalendarInteractorImpl(
    private val calendarManager: CalendarManager,
    private val schedulersFactory: RxSchedulersFactory
) : CalendarInteractor {

    override fun getLastSelectedDate(): Single<Date> =
        Single.fromCallable {
            calendarManager.getLastSelectedDate()
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun saveLastSelectedDate(selectedDate: Date): Completable =
        Completable.fromCallable {
            calendarManager.saveLastSelectedDate(selectedDate)
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun getEventsForDate(date: Date): Single<Pair<List<Event>, List<EventItemModel>>> =
        Single.fromCallable {
            val events = calendarManager.getEventsForDate(date)
            Pair(events, events.map { event ->
                EventItemModel(
                    clientName = event.child.name,
                    clientAge = event.child.age,
                    reportId = event.report.reportId,
                    status = event.report.status,
                    startDate = event.startDate,
                    endDate = event.endDate
                )
            })
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

}