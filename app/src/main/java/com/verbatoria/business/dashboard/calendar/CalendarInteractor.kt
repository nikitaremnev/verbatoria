package com.verbatoria.business.dashboard.calendar

import com.verbatoria.business.dashboard.calendar.models.item.EventItemModel
import com.verbatoria.business.report.ReportStatus
import com.verbatoria.domain.dashboard.calendar.CalendarRepository
import com.verbatoria.infrastructure.extensions.*
import com.verbatoria.infrastructure.retrofit.endpoints.dashboard.CalendarEndpoint
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

    fun getEventsForDate(date: Date): Single<List<EventItemModel>>

}

class CalendarInteractorImpl(
    private val calendarEndpoint: CalendarEndpoint,
    private val calendarRepository: CalendarRepository,
    private val schedulersFactory: RxSchedulersFactory
) : CalendarInteractor {

    override fun getLastSelectedDate(): Single<Date> =
        Single.fromCallable {
            calendarRepository.getSelectedDate()
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun saveLastSelectedDate(selectedDate: Date): Completable =
        Completable.fromCallable {
            calendarRepository.putSelectedDate(selectedDate)
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun getEventsForDate(date: Date): Single<List<EventItemModel>> =
        Single.fromCallable {
            val eventsListDto = calendarEndpoint.getEvents(
                fromTime = date.toStartDay().formatToServerTime(),
                toTime = date.toEndDay().formatToServerTime()
            )
            eventsListDto.data.map { eventDto ->
                ReportStatus.valueOfWithDefault(eventDto.report.status)
                EventItemModel(
                    clientName = eventDto.child.name,
                    clientAge = eventDto.child.birthday.parseBirthdayFormat().getYearsForCurrentMoment(),
                    reportId = eventDto.report.reportId,
                    status = ReportStatus.valueOfWithDefault(eventDto.report.status),
                    startDate = eventDto.startAt.parseServerFormat(),
                    endDate = eventDto.endAt.parseServerFormat()
                )
            }
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)


}