package com.verbatoria.ui.event

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.remnev.verbatoria.R
import com.verbatoria.domain.child.model.Child
import com.verbatoria.business.event.models.item.EventDetailItem
import com.verbatoria.domain.client.model.Client
import com.verbatoria.di.Injector
import com.verbatoria.di.event.EventDetailComponent
import com.verbatoria.domain.dashboard.calendar.model.Event
import com.verbatoria.ui.base.BasePresenterActivity
import com.verbatoria.ui.base.BaseView
import com.verbatoria.ui.child.ChildActivity
import com.verbatoria.ui.client.ClientActivity
import com.verbatoria.ui.common.Adapter
import com.verbatoria.ui.common.dialog.SelectionBottomSheetDialog
import com.verbatoria.ui.common.dialog.ActivitySuggestDialog
import com.verbatoria.ui.common.dialog.ProgressDialog
import com.verbatoria.ui.questionnaire.QuestionnaireActivity
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
private const val INCLUDE_HOBBY_CONFIRMATION_DIALOG_TAG = "INCLUDE_HOBBY_CONFIRMATION_DIALOG_TAG"
private const val SEND_TO_LOCATION_CONFIRMATION_DIALOG_TAG = "SEND_TO_LOCATION_CONFIRMATION_DIALOG_TAG"
private const val INCLUDE_ATTENTION_MEMORY_CONFIRMATION_DIALOG_TAG = "INCLUDE_ATTENTION_MEMORY_CONFIRMATION_DIALOG_TAG"
private const val DELETE_EVENT_CONFIRMATION_DIALOG_TAG = "DELETE_EVENT_CONFIRMATION_DIALOG_TAG"
private const val PROGRESS_DIALOG_TAG = "PROGRESS_DIALOG_TAG"

interface EventDetailView : BaseView {

    fun setTitle(titleResourceId: Int)

    fun setEventDetailItems(eventDetailItems: List<EventDetailItem>)

    fun openClient(eventDetailMode: EventDetailMode, client: Client?)

    fun openChild(eventDetailMode: EventDetailMode, child: Child?, clientId: String)

    fun updateEventDetailItem(position: Int)

    fun showFillClientFirstError()

    fun showDatePickerDialog()

    fun showIntervalSelectionDialog(availableIntervals: ArrayList<String>)

    fun showIntervalsEmptyError()

    fun showReportHint(reportHintStringResourceId: Int)

    fun showIncludeHobbyConfirmationDialog()

    fun showSendToLocationConfirmationDialog()

    fun showIncludeAttentionMemoryConfirmationDialog()

    fun showDeleteEventConfirmationDialog()

    fun showDeleteMenuItem()

    fun hideDeleteMenuItem()

    fun showProgress()

    fun hideProgress()

    fun openStartSession(eventId: String)

    fun close()

    interface Callback {

        fun onDeleteEventConfirmed()

        fun onIncludeAttentionMemoryConfirmed()

        fun onIncludeHobbyConfirmed()

        fun onSendToLocationConfirmed()

        fun onDeleteEventClicked()

        fun onIntervalSelected(position: Int)

        fun onClientReturned(client: Client?)

        fun onChildReturned(child: Child?)

        fun onNavigationClicked()

    }

}

class EventDetailActivity : BasePresenterActivity<EventDetailView, EventDetailPresenter, EventDetailActivity, EventDetailComponent>(),
    EventDetailView, SelectionBottomSheetDialog.OnSelectedItemListener, ActivitySuggestDialog.OnClickSuggestDialogListener {

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
    private lateinit var deleteMenuItem: MenuItem

    private var progressDialog: ProgressDialog? = null

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
        toolbar.inflateMenu(R.menu.menu_event_detail)
        deleteMenuItem = toolbar.menu.findItem(R.id.action_delete) ?: throw IllegalStateException("on create options menu inflating error occcurred")
        deleteMenuItem.setOnMenuItemClickListener {
            presenter.onDeleteEventClicked()
            true
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

    override fun showIntervalsEmptyError() {
        showWarningSnackbar(getString(R.string.event_detail_time_intervals_empty))
    }

    override fun showReportHint(reportHintStringResourceId: Int) {
        showHintSnackbar(getString(reportHintStringResourceId))
    }

    override fun showIncludeHobbyConfirmationDialog() {
        ActivitySuggestDialog.build {
            title = getString(R.string.event_detail_include_hobby_confirmation_dialog_title)
            message = getString(R.string.event_detail_include_hobby_confirmation_dialog_message)
            positiveTitleBtn = getString(R.string.event_detail_include_hobby_confirmation_dialog_include)
            negativeTitleBtn = getString(R.string.cancel)
        }.show(supportFragmentManager, INCLUDE_HOBBY_CONFIRMATION_DIALOG_TAG)
    }

    override fun showSendToLocationConfirmationDialog() {
        ActivitySuggestDialog.build {
            title = getString(R.string.event_detail_send_to_location)
            message = getString(R.string.event_detail_send_to_location_message)
            positiveTitleBtn = getString(R.string.event_detail_send_to_location_send)
            negativeTitleBtn = getString(R.string.cancel)
        }.show(supportFragmentManager, SEND_TO_LOCATION_CONFIRMATION_DIALOG_TAG)
    }

    override fun showIncludeAttentionMemoryConfirmationDialog() {
        ActivitySuggestDialog.build {
            title = getString(R.string.confirmation)
            message = getString(R.string.event_detail_include_attention_memory_confirmation_dialog_message)
            positiveTitleBtn = getString(R.string.include)
            negativeTitleBtn = getString(R.string.cancel)
        }.show(supportFragmentManager, INCLUDE_ATTENTION_MEMORY_CONFIRMATION_DIALOG_TAG)
    }

    override fun showDeleteEventConfirmationDialog() {
        ActivitySuggestDialog.build {
            title = getString(R.string.confirmation)
            message = getString(R.string.event_detail_delete_event_confirmation_dialog_message)
            positiveTitleBtn = getString(R.string.delete)
            negativeTitleBtn = getString(R.string.cancel)
        }.show(supportFragmentManager, DELETE_EVENT_CONFIRMATION_DIALOG_TAG)
    }

    override fun showDeleteMenuItem() {
        deleteMenuItem.isVisible = true
    }

    override fun hideDeleteMenuItem() {
        deleteMenuItem.isVisible = false
    }

    override fun showProgress() {
        progressDialog = ProgressDialog()
        progressDialog?.show(supportFragmentManager, PROGRESS_DIALOG_TAG)
    }

    override fun hideProgress() {
        progressDialog?.dismiss()
    }

    override fun openStartSession(eventId: String) {
        startActivity(QuestionnaireActivity.createIntent(this, eventId))
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

    //region ActivitySuggestDialog.OnClickSuggestDialogListener

    override fun onPositiveClicked(tag: String?) {
        if (tag == INCLUDE_HOBBY_CONFIRMATION_DIALOG_TAG) {
            presenter.onIncludeHobbyConfirmed()
        }
        if (tag == SEND_TO_LOCATION_CONFIRMATION_DIALOG_TAG) {
            presenter.onSendToLocationConfirmed()
        }
        if (tag == INCLUDE_ATTENTION_MEMORY_CONFIRMATION_DIALOG_TAG) {
            presenter.onIncludeAttentionMemoryConfirmed()
        }
        if (tag == DELETE_EVENT_CONFIRMATION_DIALOG_TAG) {
            presenter.onDeleteEventConfirmed()
        }
    }

    override fun onNegativeClicked(tag: String?) {
        //empty
    }

    //endregion

}