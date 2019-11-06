package com.verbatoria.ui.late_send

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView
import com.remnev.verbatoria.BuildConfig
import com.remnev.verbatoria.R
import com.verbatoria.di.Injector
import com.verbatoria.di.late_send.LateSendComponent
import com.verbatoria.domain.late_send.model.LateSend
import com.verbatoria.infrastructure.extensions.show
import com.verbatoria.ui.base.BasePresenterActivity
import com.verbatoria.ui.base.BaseView
import com.verbatoria.ui.common.Adapter
import com.verbatoria.ui.common.dialog.ActivitySuggestDialog
import com.verbatoria.ui.common.dialog.ProgressDialog
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

/**
 * @author n.remnev
 */

private const val PROGRESS_DIALOG_TAG = "PROGRESS_DIALOG_TAG"
private const val DELETE_CONFIRMATION_DIALOG_TAG = "DELETE_CONFIRMATION_DIALOG_TAG"

interface LateSendView : BaseView {

    fun updateLateSendList(lateSendList: List<LateSend>)

    fun showLateSendListIsEmpty()

    fun showLateSendStateHint(hintResourceId: Int)

    fun showSubmitProgress(progressResourceId: Int)

    fun showDeleteConfirmationDialog(reportId: String)

    fun showProgress()

    fun hideProgress()

    fun finish()

    interface Callback {

        fun onDeleteConfirmed()

        fun onNavigationClicked()

        fun onTestButtonClicked()

    }

}

class LateSendActivity :
    BasePresenterActivity<LateSendView, LateSendPresenter, LateSendActivity, LateSendComponent>(),
    LateSendView, ActivitySuggestDialog.OnClickSuggestDialogListener {

    companion object {

        fun createIntent(context: Context): Intent =
            Intent(context, LateSendActivity::class.java)

    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var lateSendEmptyTextView: TextView
    private lateinit var toolbar: Toolbar
    private lateinit var floatingActionButton: FloatingActionButton

    @Inject
    lateinit var adapter: Adapter

    private var progressDialog: ProgressDialog? = null

    //region BasePresenterActivity

    override fun getLayoutResourceId(): Int = R.layout.activity_late_send

    override fun buildComponent(injector: Injector, savedState: Bundle?): LateSendComponent =
        injector.plusLateSendComponent()
            .build()

    override fun initViews(savedState: Bundle?) {
        recyclerView = findViewById(R.id.late_send_reports_recycler_view)
        lateSendEmptyTextView = findViewById(R.id.late_send_empty_text_view)

        toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setTitle(R.string.late_send_title)
        toolbar.setNavigationOnClickListener {
            presenter.onNavigationClicked()
        }

        floatingActionButton = findViewById(R.id.test_button)
        floatingActionButton.setOnClickListener {
            presenter.onTestButtonClicked()
        }

        if (BuildConfig.DEBUG) {
            floatingActionButton.show()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = adapter
    }

    //endregion

    //region LateSendView

    override fun updateLateSendList(lateSendList: List<LateSend>) {
        adapter.update(lateSendList)
    }

    override fun showLateSendListIsEmpty() {
        lateSendEmptyTextView.show()
    }

    override fun showLateSendStateHint(hintResourceId: Int) {
        showErrorSnackbar(getString(hintResourceId))
    }

    override fun showSubmitProgress(progressResourceId: Int) {
        showHintSnackbar(getString(progressResourceId))
    }

    override fun showDeleteConfirmationDialog(reportId: String) {
        ActivitySuggestDialog.build {
            title = getString(R.string.confirmation)
            message = getString(R.string.late_send_delete_confirmation, reportId)
            positiveTitleBtn = getString(R.string.delete)
            negativeTitleBtn = getString(R.string.cancel)
            cancelable = false
        }.show(supportFragmentManager, DELETE_CONFIRMATION_DIALOG_TAG)
    }

    override fun showProgress() {
        progressDialog = ProgressDialog()
        progressDialog?.show(supportFragmentManager, PROGRESS_DIALOG_TAG)
    }

    override fun hideProgress() {
        progressDialog?.dismiss()
    }

    //endregion

    //region ActivitySuggestDialog.OnClickSuggestDialogListener

    override fun onPositiveClicked(tag: String?) {
        presenter.onDeleteConfirmed()
    }

    override fun onNegativeClicked(tag: String?) {
        //empty
    }

    //endregion

}