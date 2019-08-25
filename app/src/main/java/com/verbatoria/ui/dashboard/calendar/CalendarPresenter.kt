package com.verbatoria.ui.dashboard.calendar

import com.verbatoria.business.dashboard.calendar.CalendarInteractor
import com.verbatoria.business.dashboard.calendar.models.item.EventItemModel
import com.verbatoria.infrastructure.extensions.*
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

    private var currentDate: Date = Date()

    private val eventsList: MutableList<EventItemModel> = mutableListOf()

    private var isLoadingEventsInProgress: Boolean = false

    init {
        getLastSelectedDate()
    }

    override fun onAttachView(view: CalendarView) {
        super.onAttachView(view)
        if (isLoadingEventsInProgress)
            view.showProgress()
        else
            setCalendarItems()
    }

    override fun onDetachView() {
        super.onDetachView()
        saveLastSelectedDate()
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

    override fun onCreateNewEventClicked() {
        view?.openCreateNewEvent()
    }

    //endregion

    //region EventItemViewHolder.Callback

    override fun onEventItemClicked(position: Int) {

    }

    //endregion

    private fun getLastSelectedDate() {
        calendarInteractor.getLastSelectedDate()
            .subscribe({ lastSelectedDate ->
                currentDate = lastSelectedDate
                setCurrentDate()
                getEvents()
            }, { error ->
                logger.error("error while get last selected event occurred", error)
            })
            .let(::addDisposable)
    }

    private fun saveLastSelectedDate() {
        calendarInteractor.saveLastSelectedDate(currentDate)
            .subscribe({
                //empty
            }, { error ->
                logger.error("error while saving last selected event occurred", error)
            })
            .let(::addDisposable)
    }

    private fun getEvents() {
        isLoadingEventsInProgress = true
        eventsList.clear()
        clearDisposables()
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
                logger.error("error while get events occurred", error)
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