package com.verbatoria.business.dashboard.calendar

import com.verbatoria.business.dashboard.calendar.models.CalendarItemModel
import com.verbatoria.infrastructure.date.formatToServerTime
import com.verbatoria.infrastructure.date.toEndDay
import com.verbatoria.infrastructure.date.toStartDay
import com.verbatoria.infrastructure.retrofit.endpoints.dashboard.CalendarEndpoint
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import io.reactivex.Single
import java.util.*

/**
 * @author n.remnev
 */

interface CalendarInteractor {

    fun getEventsForDate(date: Date): Single<List<CalendarItemModel>>

}

class CalendarInteractorImpl(
    private val calendarEndpoint: CalendarEndpoint,
    private val schedulersFactory: RxSchedulersFactory
) : CalendarInteractor {

    override fun getEventsForDate(date: Date): Single<List<CalendarItemModel>> =
        Single.fromCallable {
            calendarEndpoint.getEvents(
                fromTime = date.toStartDay().formatToServerTime(),
                toTime = date.toEndDay().formatToServerTime()
            )
            listOf<CalendarItemModel>()
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)


}