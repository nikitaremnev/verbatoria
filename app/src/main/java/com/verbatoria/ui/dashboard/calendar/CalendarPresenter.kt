package com.verbatoria.ui.dashboard.calendar

import com.verbatoria.business.dashboard.calendar.CalendarInteractor
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

    private val eventsList: MutableList<EventItemModel> = mutableListOf()

    private var isLoadingEventsInProgress: Boolean = false

    init {
        getEvents()
    }

    override fun onAttachView(view: CalendarView) {
        super.onAttachView(view)
        setCurrentDate()
        if (isLoadingEventsInProgress)
            view.showProgress()
        else
            setCalendarItems()
    }

    //region CalendarView.Callback

    override fun onPreviousDateClicked() {
        currentDate.minusDay()
        setCurrentDate()
        getEvents()
    }

    override fun onCurrentDateClicked() {

    }

    override fun onNextDateClicked() {
        currentDate.plusDay()
        setCurrentDate()
        getEvents()
    }

    //endregion

    //region EventItemViewHolder.Callback

    override fun onEventItemClicked(position: Int) {

    }

    //endregion

    private fun getEvents() {
        isLoadingEventsInProgress = true
        eventsList.clear()
        view?.apply {
            hideEmptyEvents()
            hideEventsList()
            showProgress()
        }
        calendarInteractor.getEventsForDate(currentDate)
            .subscribe({ calendarItemModels ->
                eventsList.addAll(calendarItemModels)
                view?.apply {
                    hideProgress()
                    setCalendarItems()
                }
                isLoadingEventsInProgress = false
            }, { error ->
                error.printStackTrace()
                view?.apply {
                    hideProgress()
                    showEmptyEvents()
                }
                isLoadingEventsInProgress = false
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

    private fun setCalendarItems() {
        if (eventsList.isEmpty()) {
            view?.apply {
                hideEventsList()
                showEmptyEvents()
            }
        } else {
            view?.apply {
                setCalendarItems(eventsList)
                showEventsList()
            }
        }
    }

}