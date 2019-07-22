package com.verbatoria.ui.dashboard.calendar

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.remnev.verbatoria.R
import com.verbatoria.business.dashboard.calendar.models.CalendarItemModel
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

    fun setCurrentDate(currentDate: String)

    fun setYesterdayCurrentDate()

    fun setTodayCurrentDate()

    fun setTomorrowCurrentDate()

    fun setCalendarItems(calendarItemModels: List<CalendarItemModel>)

    interface Callback {

        fun onPreviousDateClicked()

        fun onCurrentDateClicked()

        fun onNextDateClicked()

    }

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
    private lateinit var currentDateTextView: TextView
    private lateinit var previousDateImageView: ImageView
    private lateinit var nextDateImageView: ImageView

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
            previousDateImageView = findViewById(R.id.previous_date_image_view)
            currentDateTextView = findViewById(R.id.current_date_text_view)
            nextDateImageView = findViewById(R.id.next_date_image_view)
        }
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        previousDateImageView.setOnClickListener {
            presenter.onPreviousDateClicked()
        }
        currentDateTextView.setOnClickListener {
            presenter.onCurrentDateClicked()
        }
        nextDateImageView.setOnClickListener {
            presenter.onNextDateClicked()
        }

        return rootView
    }

    //region CalendarView

    override fun setCurrentDate(currentDate: String) {
        currentDateTextView.text = currentDate
    }

    override fun setYesterdayCurrentDate() {
        currentDateTextView.text = getString(R.string.calendar_report_yesterday)
    }

    override fun setTodayCurrentDate() {
        currentDateTextView.text = getString(R.string.calendar_report_today)
    }

    override fun setTomorrowCurrentDate() {
        currentDateTextView.text = getString(R.string.calendar_report_tomorrow)
    }

    override fun setCalendarItems(calendarItemModels: List<CalendarItemModel>) {
        adapter.update(calendarItemModels)
    }

    //endregion

}