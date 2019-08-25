package com.verbatoria.ui.event

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.remnev.verbatoria.R
import com.verbatoria.domain.child.Child
import com.verbatoria.business.event.models.item.EventDetailItem
import com.verbatoria.domain.client.Client
import com.verbatoria.di.Injector
import com.verbatoria.di.event.EventDetailComponent
import com.verbatoria.domain.dashboard.calendar.Event
import com.verbatoria.ui.base.BasePresenterActivity
import com.verbatoria.ui.base.BaseView
import com.verbatoria.ui.child.ChildActivity
import com.verbatoria.ui.client.ClientActivity
import com.verbatoria.ui.common.Adapter
import com.verbatoria.ui.common.dialog.SelectionBottomSheetDialog
import com.verbatoria.utils.LocaleHelper.LOCALE_RU
import java.util.*
import javax.inject.Inject

/**
 * @author n.remnev
 */

private const val CLIENT_REQUEST_CODE = 912
private const val CHILD_REQUEST_CODE = 913

private const val EVENT_DETAIL_MODE_EXTRA = "event_detail_mode_extra"
private const val EVENT_EXTRA = "event_extra"

private const val INTERVALS_SELECTION_DIALOG_TAG = "INTERVALS_SELECTION_DIALOG_TAG"

interface EventDetailView : BaseView {

    fun setTitle(titleResourceId: Int)

    fun setEventDetailItems(eventDetailItems: List<EventDetailItem>)

    fun openClient(eventDetailMode: EventDetailMode, client: Client?)

    fun openChild(eventDetailMode: EventDetailMode, child: Child?, clientId: String)

    fun updateEventDetailItem(position: Int)

    fun showFillClientFirstError()

    fun showDatePickerDialog()

    fun showIntervalSelectionDialog(availableIntervals: ArrayList<String>)

    fun close()

    interface Callback {

        fun onIntervalSelected(position: Int)

        fun onClientReturned(client: Client?)

        fun onChildReturned(child: Child?)

        fun onNavigationClicked()

    }

}

class EventDetailActivity : BasePresenterActivity<EventDetailView, EventDetailPresenter, EventDetailActivity, EventDetailComponent>(),
    EventDetailView, SelectionBottomSheetDialog.OnSelectedItemListener {

    companion object {

        fun createIntent(
            context: Context,
            eventDetailMode: EventDetailMode,
            event: Event? = null
        ): Intent =
            Intent(context, EventDetailActivity::class.java)
                .putExtra(EVENT_DETAIL_MODE_EXTRA, eventDetailMode.ordinal)
                .putExtra(EVENT_EXTRA, event)

    }

    @Inject
    lateinit var adapter: Adapter

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView

    //region BasePresenterActivity

    override fun getLayoutResourceId(): Int = R.layout.activity_event_detail

    override fun buildComponent(injector: Injector, savedState: Bundle?): EventDetailComponent =
        injector.plusEventDetailComponent()
            .eventDetailMode(intent.getIntExtra(EVENT_DETAIL_MODE_EXTRA, EventDetailMode.CREATE_NEW.ordinal))
            .event(intent.getParcelableExtra(EVENT_EXTRA))
            .build()

    override fun initViews(savedState: Bundle?) {
        toolbar = findViewById(R.id.toolbar)
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener {
            presenter.onNavigationClicked()
        }
    }

    //endregion

    //region BasePresenterActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CLIENT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            presenter.onClientReturned(data?.getParcelableExtra(ClientActivity.CLIENT_EXTRA))
        }
        if (requestCode == CHILD_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            presenter.onChildReturned(data?.getParcelableExtra(ChildActivity.CHILD_EXTRA))
        }
    }

    //endregion

    //region EventDetailView

    override fun setTitle(titleResourceId: Int) {
        toolbar.title = getString(titleResourceId)
    }

    override fun setEventDetailItems(eventDetailItems: List<EventDetailItem>) {
        adapter.update(eventDetailItems)
    }

    override fun openClient(eventDetailMode: EventDetailMode, client: Client?) {
        startActivityForResult(ClientActivity.createIntent(this, eventDetailMode, client), CLIENT_REQUEST_CODE)
    }

    override fun openChild(eventDetailMode: EventDetailMode, child: Child?, clientId: String) {
        startActivityForResult(ChildActivity.createIntent(this, eventDetailMode, child, clientId), CHILD_REQUEST_CODE)
    }

    override fun updateEventDetailItem(position: Int) {
        adapter.update(position)
    }

    override fun showFillClientFirstError() {
        showErrorSnackbar(getString(R.string.child_first_fill_client_error))
    }

    override fun showDatePickerDialog() {
        val now = Calendar.getInstance(Locale(LOCALE_RU))
        val datePickerDialog = DatePickerDialog(
            this,
            presenter,
            now.get(Calendar.YEAR),
            now.get(Calendar.MONTH),
            now.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    override fun showIntervalSelectionDialog(availableIntervals: ArrayList<String>) {
        SelectionBottomSheetDialog.build {
            titleText = getString(R.string.event_detail_intervals_selection_dialog_title)
            itemsArray = availableIntervals
        }
            .show(supportFragmentManager, INTERVALS_SELECTION_DIALOG_TAG)
    }

    override fun close() {
        finish()
    }

    //endregion

    //region SelectionBottomSheetDialog.OnSelectedItemListener

    override fun onSelectionDialogItemSelected(tag: String?, position: Int) {
        if (tag == INTERVALS_SELECTION_DIALOG_TAG) {
            presenter.onIntervalSelected(position)
        }
    }

    //endregion

}