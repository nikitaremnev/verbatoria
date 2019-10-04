package com.verbatoria.ui.dashboard.calendar

import android.app.DatePickerDialog
import android.widget.DatePicker
import com.verbatoria.business.dashboard.LocalesAvailable
import com.verbatoria.business.dashboard.calendar.CalendarInteractor
import com.verbatoria.business.dashboard.calendar.models.item.EventItemModel
import com.verbatoria.domain.dashboard.calendar.model.Event
import com.verbatoria.infrastructure.extensions.*
import com.verbatoria.ui.base.BasePresenter
import com.verbatoria.ui.dashboard.calendar.item.EventItemViewHolder
import com.verbatoria.ui.event.EventDetailMode
import org.slf4j.LoggerFactory
import java.util.*

/**
 * @author n.remnev
 */

class CalendarPresenter(
    private val calendarInteractor: CalendarInteractor
) : BasePresenter<CalendarView>(), CalendarView.Callback, EventItemViewHolder.Callback, DatePickerDialog.OnDateSetListener {

    private val logger = LoggerFactory.getLogger("CalendarPresenter")

    private var currentDate: Date = Date()

    private val eventsViewModelsList: MutableList<EventItemModel> = mutableListOf()

    private val eventsList: MutableList<Event> = mutableListOf()

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


    override fun onEventReturned() {
        getEvents()
    }

    override fun onPreviousDateClicked() {
        currentDate.minusDay()
        setCurrentDate()
        getEvents()
    }

    override fun onCurrentDateClicked() {
        view?.showDatePickerDialog(currentDate)
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
        if (position in 0..eventsList.size) {
            val event = eventsList[position]
            view?.openEventDetail(
                event,
                if (!event.report.isNew()) {
                    EventDetailMode.VIEW_ONLY
                } else if (event.child.hasAnythingExceptId()){
                    EventDetailMode.START
                } else {
                    EventDetailMode.CHILD_REQUIRED
                }
            )
        }
    }

    //endregion

    //region DatePickerDialog.OnDateSetListener

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        currentDate = Calendar.getInstance(Locale(LocalesAvailable.RUSSIAN_LOCALE)).apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }.time
        setCurrentDate()
        getEvents()
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
        eventsViewModelsList.clear()
        eventsList.clear()
        clearDisposables()
        view?.apply {
            hideEmptyEvents()
            hideEventsList()
            showProgress()
        }
        calendarInteractor.getEventsForDate(currentDate)
            .subscribe({ (events, eventsViewModels) ->
                eventsList.addAll(events)
                eventsViewModelsList.addAll(eventsViewModels)
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
        if (eventsViewModelsList.isEmpty()) {
            view?.apply {
                hideEventsList()
                showEmptyEvents()
            }
        } else {
            view?.apply {
                setCalendarItems(eventsViewModelsList)
                showEventsList()
            }
        }
    }

}