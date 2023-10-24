package com.verbatoria.ui.schedule

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.remnev.verbatoria.R
import com.verbatoria.di.Injector
import com.verbatoria.di.schedule.ScheduleComponent
import com.verbatoria.domain.schedule.model.ScheduleDataSource
import com.verbatoria.infrastructure.extensions.*
import com.verbatoria.ui.base.BasePresenterActivity
import com.verbatoria.ui.base.BaseView
import com.verbatoria.ui.common.adaptivetablelayout.AdaptiveTableLayout
import com.verbatoria.ui.common.dialog.ActivitySuggestDialog
import com.verbatoria.ui.common.dialog.SelectionBottomSheetDialog

/**
 * @author n.remnev
 */

private const val SAVE_SCHEDULE_CONFIRMATION_DIALOG_TAG = "SAVE_SCHEDULE_CONFIRMATION_DIALOG_TAG"
private const val CLEAR_SCHEDULE_CONFIRMATION_DIALOG_TAG = "CLEAR_SCHEDULE_CONFIRMATION_DIALOG_TAG"
private const val SAVE_SCHEDULE_PROGRESS_DIALOG_TAG = "SAVE_SCHEDULE_PROGRESS_DIALOG_TAG"
private const val CLEAR_SCHEDULE_PROGRESS_DIALOG_TAG = "CLEAR_SCHEDULE_PROGRESS_DIALOG_TAG"
private const val LOAD_SCHEDULE_PROGRESS_DIALOG_TAG = "LOAD_SCHEDULE_PROGRESS_DIALOG_TAG"

interface ScheduleView : BaseView {

    fun setSchedule(scheduleDataSource: ScheduleDataSource)

    fun updateScheduleCellAfterClicked(row: Int, column: Int)

    fun updateScheduleAfterCleared()

    fun showSaveScheduleConfirmationDialog()

    fun showClearScheduleConfirmationDialog()

    fun showInitialLoadScheduleProgress()

    fun hideInitialLoadScheduleProgress()

    fun showSaveScheduleProgressDialog()

    fun hideSaveScheduleProgressDialog()

    fun showClearScheduleProgressDialog()

    fun hideClearScheduleProgressDialog()

    fun showLoadScheduleProgress()

    fun hideLoadScheduleProgress()

    fun showInitialLoadTryAgain()

    fun hideInitialLoadTryAgain()

    fun close()

    interface Callback {

        fun onClearScheduleClicked()

        fun onNextWeekClicked()

        fun onPreviousWeekClicked()

        fun onSaveScheduleClicked()

        fun onClearScheduleConfirmed()

        fun onNavigationClicked()

        fun onTryAgainClicked()

        fun onWeeksForwardSaveSelected(weeksForward: Int)

    }

}

class ScheduleActivity :
    BasePresenterActivity<ScheduleView, SchedulePresenter, ScheduleActivity, ScheduleComponent>(),
    ScheduleView, SelectionBottomSheetDialog.OnSelectedItemListener, ActivitySuggestDialog.OnClickSuggestDialogListener {

    companion object {

        fun createIntent(context: Context): Intent =
            Intent(context, ScheduleActivity::class.java)

    }

    private lateinit var toolbar: Toolbar
    private lateinit var progressBar: ProgressBar
    private lateinit var loadingErrorTextView: TextView
    private lateinit var tryAgainButton: Button
    private lateinit var adaptiveTableLayout: AdaptiveTableLayout
    private var scheduleAdapter: ScheduleAdapter? = null

    //region BasePresenterActivity

    override fun getLayoutResourceId(): Int = R.layout.activity_schedule

    override fun buildComponent(injector: Injector, savedState: Bundle?): ScheduleComponent =
        injector.plusScheduleComponent()
            .build()

    override fun initViews(savedState: Bundle?) {
        toolbar = findViewById(R.id.toolbar)
        progressBar = findViewById(R.id.schedule_progress_bar)
        loadingErrorTextView = findViewById(R.id.schedule_loading_error_text_view)
        tryAgainButton = findViewById(R.id.schedule_loading_error_try_again_button)
        tryAgainButton.setOnClickListener {
            presenter.onTryAgainClicked()
        }

        adaptiveTableLayout = findViewById(R.id.schedule_adaptive_table_layout)

        toolbar.setTitle(R.string.schedule_title)
        toolbar.inflateMenu(R.menu.menu_toolbar_schedule)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener {
            presenter.onNavigationClicked()
        }
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_clear -> {
                    presenter.onClearScheduleClicked()
                    true
                }
                R.id.action_previous_week -> {
                    presenter.onPreviousWeekClicked()
                    true
                }
                R.id.action_next_week -> {
                    presenter.onNextWeekClicked()
                    true
                }
                R.id.action_save -> {
                    presenter.onSaveScheduleClicked()
                    true
                }
                else -> false
            }
        }
    }

    //endregion

    //region ScheduleView

    override fun setSchedule(scheduleDataSource: ScheduleDataSource) {
        adaptiveTableLayout.addOnLayoutChangeListener { _, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            scheduleAdapter?.apply {
                updateWidthAndHeight(
                    width = right - left,
                    height = bottom - top
                )
            }
            adaptiveTableLayout.setAdapter(scheduleAdapter)
            scheduleAdapter?.notifyDataSetChanged()
        }

        scheduleAdapter = ScheduleAdapter(this, adaptiveTableLayout.width, adaptiveTableLayout.height, scheduleDataSource)
        scheduleAdapter?.onItemClickListener = presenter
        adaptiveTableLayout.setAdapter(scheduleAdapter)
        scheduleAdapter?.notifyDataSetChanged()
    }

    override fun updateScheduleCellAfterClicked(row: Int, column: Int) {
        scheduleAdapter?.notifyItemChanged(row, column)
    }

    override fun updateScheduleAfterCleared() {
        scheduleAdapter?.notifyDataSetChanged()
    }

    override fun showSaveScheduleConfirmationDialog() {
        SelectionBottomSheetDialog.build {
            titleText = getString(R.string.schedule_save_confirmation_title)
            itemsArray = arrayListOf(
                getString(R.string.schedule_only_this_week),
                getString(R.string.schedule_save_1_week),
                getString(R.string.schedule_save_2_weeks),
                getString(R.string.schedule_save_3_weeks),
                getString(R.string.schedule_save_4_weeks),
                getString(R.string.schedule_save_5_weeks)
            )
        }
            .show(supportFragmentManager, SAVE_SCHEDULE_CONFIRMATION_DIALOG_TAG)
    }

    override fun showClearScheduleConfirmationDialog() {
        ActivitySuggestDialog.build {
            title = getString(R.string.confirmation)
            message = getString(R.string.schedule_clear_confirmation)
            positiveTitleBtn = getString(R.string.delete)
            negativeTitleBtn = getString(R.string.cancel)
        }.show(supportFragmentManager, CLEAR_SCHEDULE_CONFIRMATION_DIALOG_TAG)
    }

    override fun showInitialLoadScheduleProgress() {
        progressBar.show()
    }

    override fun hideInitialLoadScheduleProgress() {
        progressBar.hide()
    }

    override fun showSaveScheduleProgressDialog() {
        showProgressDialogFragment(R.string.schedule_save, supportFragmentManager, SAVE_SCHEDULE_PROGRESS_DIALOG_TAG)
    }

    override fun hideSaveScheduleProgressDialog() {
        hideProgressDialogFragment(supportFragmentManager, SAVE_SCHEDULE_PROGRESS_DIALOG_TAG)
    }

    override fun showClearScheduleProgressDialog() {
        showProgressDialogFragment(R.string.schedule_clear, supportFragmentManager, CLEAR_SCHEDULE_PROGRESS_DIALOG_TAG)
    }

    override fun hideClearScheduleProgressDialog() {
        hideProgressDialogFragment(supportFragmentManager, CLEAR_SCHEDULE_PROGRESS_DIALOG_TAG)
    }

    override fun showLoadScheduleProgress() {
        showProgressDialogFragment(R.string.schedule_load, supportFragmentManager, LOAD_SCHEDULE_PROGRESS_DIALOG_TAG)
    }

    override fun hideLoadScheduleProgress() {
        hideProgressDialogFragment(supportFragmentManager, LOAD_SCHEDULE_PROGRESS_DIALOG_TAG)
    }

    override fun showInitialLoadTryAgain() {
        loadingErrorTextView.visibility = View.VISIBLE
        tryAgainButton.visibility = View.VISIBLE

        toolbar.menu.findItem(R.id.action_clear).isVisible = false
        toolbar.menu.findItem(R.id.action_previous_week).isVisible = false
        toolbar.menu.findItem(R.id.action_next_week).isVisible = false
        toolbar.menu.findItem(R.id.action_save).isVisible = false
    }

    override fun hideInitialLoadTryAgain() {
        loadingErrorTextView.visibility = View.GONE
        tryAgainButton.visibility = View.GONE

        toolbar.menu.findItem(R.id.action_clear).isVisible = true
        toolbar.menu.findItem(R.id.action_previous_week).isVisible = true
        toolbar.menu.findItem(R.id.action_next_week).isVisible = true
        toolbar.menu.findItem(R.id.action_save).isVisible = true
    }

    override fun close() {
        finish()
    }

    //endregion

    //region ActivitySuggestDialog.OnClickSuggestDialogListener

    override fun onPositiveClicked(tag: String?) {
        presenter.onClearScheduleConfirmed()
    }

    override fun onNegativeClicked(tag: String?) {
        //empty
    }

    //endregion

    //region SelectionBottomSheetDialog.OnSelectedItemListener

    override fun onSelectionDialogItemSelected(tag: String?, position: Int) {
        presenter.onWeeksForwardSaveSelected(position)
    }

    //endregion

}