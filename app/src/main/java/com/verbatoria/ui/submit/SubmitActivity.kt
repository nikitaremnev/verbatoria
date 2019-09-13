package com.verbatoria.ui.submit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import com.remnev.verbatoria.R
import com.verbatoria.di.Injector
import com.verbatoria.di.submit.SubmitComponent
import com.verbatoria.ui.base.BasePresenterActivity
import com.verbatoria.ui.base.BaseView
import com.verbatoria.ui.common.dialog.FragmentSuggestDialog

/**
 * @author nikitaremnev
 */

private const val EVENT_ID_EXTRA = "event_id_extra"

private const val SUCCESS_DIALOG_TAG = "SUCCESS_DIALOG_TAG"
private const val ERROR_DIALOG_TAG = "ERROR_DIALOG_TAG"

interface SubmitView : BaseView {

    fun setStatus(status: Int)

    fun showSuccessDialog()

    fun showErrorDialog()

    fun close()

    interface Callback {

        fun onResultDialogDismissed()

    }

}

class SubmitActivity : BasePresenterActivity<SubmitView, SubmitPresenter, SubmitActivity, SubmitComponent>(),
    SubmitView, FragmentSuggestDialog.OnClickSuggestDialogListener, FragmentSuggestDialog.OnCancelSuggestDialogListener {

    companion object {

        fun createIntent(
            context: Context,
            eventId: String
        ): Intent =
            Intent(context, SubmitActivity::class.java)
                .putExtra(EVENT_ID_EXTRA, eventId)

    }


    private lateinit var progressBar: ProgressBar
    private lateinit var statusTextView: TextView

    //region BasePresenterActivity

    override fun getLayoutResourceId(): Int = R.layout.activity_submit_new

    override fun buildComponent(injector: Injector, savedState: Bundle?): SubmitComponent =
        injector.plusSubmitComponent()
            .eventId(intent.getStringExtra(EVENT_ID_EXTRA))
            .build()

    override fun initViews(savedState: Bundle?) {
        progressBar = findViewById(R.id.progress_bar)
        statusTextView = findViewById(R.id.status_text_view)
    }

    //endregion

    //region SubmitView

    override fun setStatus(status: Int) {
        statusTextView.setText(status)
    }

    override fun showSuccessDialog() {
        FragmentSuggestDialog.build {
            title = getString(R.string.submit_finished)
            message = getString(R.string.submit_finished_message)
            cancelable = true
            positiveTitleBtn = getString(R.string.ok)
        }.show(supportFragmentManager, SUCCESS_DIALOG_TAG)
    }

    override fun showErrorDialog() {
        FragmentSuggestDialog.build {
            title = getString(R.string.submit_error)
            message = getString(R.string.submit_error_message)
            cancelable = true
            positiveTitleBtn = getString(R.string.ok)
        }.show(supportFragmentManager, ERROR_DIALOG_TAG)
    }

    override fun close() {
        finish()
    }

    //endregion

    //region FragmentSuggestDialog.OnClickSuggestDialogListener

    override fun onPositiveClicked(tag: String?) {
        dialogDismissed()
    }

    override fun onNegativeClicked(tag: String?) {
        dialogDismissed()
    }

    //endregion

    //region FragmentSuggestDialog.OnCancelSuggestDialogListener

    override fun onCancelDialog(tag: String?) {
        dialogDismissed()
    }

    //endregion

    private fun dialogDismissed() {
        presenter.onResultDialogDismissed()
    }

}
