package com.verbatoria.ui.dashboard.calendar

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.remnev.verbatoria.R
import com.verbatoria.business.dashboard.calendar.models.item.EventItemModel
import com.verbatoria.di.dashboard.DashboardComponent
import com.verbatoria.di.dashboard.calendar.CalendarComponent
import com.verbatoria.domain.dashboard.calendar.Event
import com.verbatoria.infrastructure.extensions.hide
import com.verbatoria.infrastructure.extensions.show
import com.verbatoria.ui.base.BasePresenterFragment
import com.verbatoria.ui.base.BaseView
import com.verbatoria.ui.common.Adapter
import com.verbatoria.ui.event.EventDetailActivity
import com.verbatoria.ui.event.EventDetailMode
import javax.inject.Inject

/**
 * @author n.remnev
 */

private const val EVENT_DETAIL_REQUEST_CODE = 914

interface CalendarView : BaseView {

    fun setCurrentDate(currentDate: String)

    fun setYesterdayCurrentDate()

    fun setTodayCurrentDate()

    fun setTomorrowCurrentDate()

    fun setCalendarItems(eventsList: List<EventItemModel>)

    fun showEmptyEvents()

    fun hideEmptyEvents()

    fun showEventsList()

    fun hideEventsList()

    fun showProgress()

    fun hideProgress()

    fun openCreateNewEvent()

    fun openEventDetail(event: Event)

    interface Callback {

        fun onPreviousDateClicked()

        fun onCurrentDateClicked()

        fun onNextDateClicked()

        fun onCreateNewEventClicked()

        fun onEventReturned()

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
    private lateinit var loadingProgressBar: ProgressBar
    private lateinit var emptyTextView: TextView
    private lateinit var createNewEventFloatingActionButton: FloatingActionButton

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
            loadingProgressBar = findViewById(R.id.loading_progress_bar)
            emptyTextView = findViewById(R.id.empty_text_view)
            createNewEventFloatingActionButton = findViewById(R.id.create_new_event_floating_action_button)
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

        createNewEventFloatingActionButton.setOnClickListener {
            presenter.onCreateNewEventClicked()
        }

        return rootView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EVENT_DETAIL_REQUEST_CODE) {
            presenter.onEventReturned()
        }
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

    override fun setCalendarItems(eventsList: List<EventItemModel>) {
        adapter.update(eventsList)
    }

    override fun showEmptyEvents() {
        emptyTextView.show()
    }

    override fun hideEmptyEvents() {
        emptyTextView.hide()
    }

    override fun showEventsList() {
        recyclerView.show()
    }

    override fun hideEventsList() {
        recyclerView.hide()
    }

    override fun showProgress() {
        loadingProgressBar.show()
    }

    override fun hideProgress() {
        loadingProgressBar.hide()
    }

    override fun openCreateNewEvent() {
        activity?.let { activity ->
            startActivityForResult(EventDetailActivity.createIntent(activity, EventDetailMode.CREATE_NEW), EVENT_DETAIL_REQUEST_CODE)
        }
    }

    override fun openEventDetail(event: Event) {
        activity?.let { activity ->
            startActivityForResult(EventDetailActivity.createIntent(activity, EventDetailMode.START, event), EVENT_DETAIL_REQUEST_CODE)
        }
    }

    //endregion

}