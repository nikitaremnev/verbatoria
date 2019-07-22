package com.verbatoria.business.dashboard.calendar

import android.util.Log
import com.verbatoria.business.dashboard.calendar.models.EventItemModel
import com.verbatoria.business.report.ReportStatus
import com.verbatoria.infrastructure.date.*
import com.verbatoria.infrastructure.retrofit.endpoints.dashboard.CalendarEndpoint
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import io.reactivex.Single
import java.util.*

/**
 * @author n.remnev
 */

interface CalendarInteractor {

    fun getEventsForDate(date: Date): Single<List<EventItemModel>>

}

class CalendarInteractorImpl(
    private val calendarEndpoint: CalendarEndpoint,
    private val schedulersFactory: RxSchedulersFactory
) : CalendarInteractor {

    override fun getEventsForDate(date: Date): Single<List<EventItemModel>> =
        Single.fromCallable {
            val eventsListDto = calendarEndpoint.getEvents(
                fromTime = date.toStartDay().formatToServerTime(),
                toTime = date.toEndDay().formatToServerTime()
            )
            eventsListDto.data.map { eventDto ->
                Log.e("test", "CalendarInteractor ${eventDto.report.status}")
                Log.e("test", "CalendarInteractor ${ReportStatus.valueOfWithDefault(eventDto.report.status)}")

                ReportStatus.valueOfWithDefault(eventDto.report.status)
                EventItemModel(
                    clientName = eventDto.child.name + ", " + eventDto.child.birthday.parseBirthdayFormat().getYearsForCurrentMoment(),
                    clientBirhtday = eventDto.child.birthday.parseBirthdayFormat(),
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