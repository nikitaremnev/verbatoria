package com.verbatoria.ui.dashboard.calendar

import com.verbatoria.business.dashboard.calendar.CalendarInteractor
import com.verbatoria.business.dashboard.calendar.models.CalendarItemModel
import com.verbatoria.business.dashboard.calendar.models.EventItemModel
import com.verbatoria.infrastructure.date.*
import com.verbatoria.ui.base.BasePresenter
import com.verbatoria.ui.dashboard.calendar.item.EventItemViewHolder
import org.slf4j.LoggerFactory
import java.util.*

/**
 * @author n.remnev
 */

class CalendarPresenter(
    private val calendarInteractor: CalendarInteractor
) : BasePresenter<CalendarView>(), CalendarView.Callback, EventItemViewHolder.Callback {

    private val logger = LoggerFactory.getLogger("CalendarPresenter")

    private val currentDate: Date = Date()

    private val calendarItems: MutableList<CalendarItemModel> = mutableListOf()

    init {
        getEvents(currentDate)
    }

    override fun onAttachView(view: CalendarView) {
        super.onAttachView(view)
        setCurrentDate()
        view.setCalendarItems(calendarItems)
    }

    //region CalendarView.Callback

    override fun onPreviousDateClicked() {
        currentDate.minusDay()
        setCurrentDate()
    }

    override fun onCurrentDateClicked() {

    }

    override fun onNextDateClicked() {
        currentDate.plusDay()
        setCurrentDate()
    }

    //endregion

    //region EventItemViewHolder.Callback

    override fun onEventItemClicked(position: Int) {

    }

    //endregion

    private fun getEvents(date: Date) {
        calendarInteractor.getEventsForDate(date)
            .subscribe({ calendarItemModels ->
                calendarItems.clear()
                calendarItems.addAll(calendarItemModels)
                view?.setCalendarItems(calendarItemModels)
            }, { error ->
                error.printStackTrace()
            })
            .let(::addDisposable)
    }

    private fun setCurrentDate() {
        when {
            currentDate.isYesterday() ->
                view?.setYesterdayCurrentDate()
            currentDate.isToday() ->
                view?.setTodayCurrentDate()
            currentDate.isTomorrow() ->
                view?.setTomorrowCurrentDate()
            else ->
                view?.setCurrentDate(currentDate.formatToDateWithFullMonth())
        }
    }

}