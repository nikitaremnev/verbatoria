package com.verbatoria.ui.dashboard.calendar

import com.verbatoria.business.dashboard.calendar.CalendarInteractor
import com.verbatoria.business.dashboard.calendar.models.EmptyItemModel
import com.verbatoria.ui.base.BasePresenter
import org.slf4j.LoggerFactory

/**
 * @author n.remnev
 */

class CalendarPresenter(
    private val calendarInteractor: CalendarInteractor
) : BasePresenter<CalendarView>() {

    private val logger = LoggerFactory.getLogger("CalendarPresenter")

    init {

    }

    override fun onAttachView(view: CalendarView) {
        super.onAttachView(view)
        view.setCalendarItems(listOf(EmptyItemModel()))
    }

}