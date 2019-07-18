package com.verbatoria.ui.dashboard.calendar

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.remnev.verbatoria.R
import com.verbatoria.business.dashboard.calendar.models.CalendarItemModel
import com.verbatoria.business.dashboard.settings.model.SettingsItemModel
import com.verbatoria.di.dashboard.DashboardComponent
import com.verbatoria.di.dashboard.calendar.CalendarComponent
import com.verbatoria.ui.base.BasePresenterFragment
import com.verbatoria.ui.base.BaseView
import com.verbatoria.ui.common.Adapter
import com.verbatoria.ui.common.dialog.ProgressDialog
import javax.inject.Inject

/**
 * @author n.remnev
 */

interface CalendarView : BaseView {

    fun setCalendarItems(calendarItemModels: List<CalendarItemModel>)

}

class CalendarFragment :
    BasePresenterFragment<CalendarView, DashboardComponent, CalendarFragment, CalendarComponent, CalendarPresenter>(),
    CalendarView {

    companion object {

        fun createFragment(): CalendarFragment = CalendarFragment()

    }

    @Inject
    lateinit var adapter: Adapter

    private lateinit var recyclerView: RecyclerView
    private var progressDialog: ProgressDialog? = null

    override fun buildComponent(
        parentComponent: DashboardComponent,
        savedState: Bundle?
    ): CalendarComponent =
        parentComponent.plusCalendarComponent()
            .build()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_calendar, container, false)
        rootView.apply {
            recyclerView = findViewById(R.id.calendar_recycler_view)
        }
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        return rootView
    }

    //region CalendarView

    override fun setCalendarItems(calendarItemModels: List<CalendarItemModel>) {
        adapter.update(calendarItemModels)
    }

    //endregion

}