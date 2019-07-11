package com.verbatoria.ui.late_send

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.remnev.verbatoria.R
import com.verbatoria.business.late_send.LateReportModel
import com.verbatoria.di.Injector
import com.verbatoria.di.late_send.LateSendComponent
import com.verbatoria.ui.base.BasePresenterActivity
import com.verbatoria.ui.base.BaseView
import com.verbatoria.ui.common.Adapter
import javax.inject.Inject

/**
 * @author n.remnev
 */

interface LateSendView : BaseView {

    fun updateLateReportsList(lateReportsList: MutableList<LateReportModel>)

    fun showLateReportsIsEmpty()

    fun showProgress()

    fun hideProgress()

}

class LateSendActivity :
    BasePresenterActivity<LateSendView, LateSendPresenter, LateSendActivity, LateSendComponent>(),
    LateSendView {

    companion object {

        fun createIntent(context: Context): Intent =
            Intent(context, LateSendActivity::class.java)

    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var noReportsTextView: TextView

    @Inject
    lateinit var adapter: Adapter

    //region BasePresenterActivity

    override fun getLayoutResourceId(): Int = R.layout.activity_late_send

    override fun buildComponent(injector: Injector, savedState: Bundle?): LateSendComponent =
        injector.plusLateSendComponent()
            .build()

    override fun initViews(savedState: Bundle?) {
        recyclerView = findViewById(R.id.late_send_reports_recycler_view)
        noReportsTextView = findViewById(R.id.no_reports_text_view)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = adapter
    }

    //endregion

    //region LateSendView

    override fun updateLateReportsList(lateReportsList: MutableList<LateReportModel>) {
        adapter.update(lateReportsList)
    }

    override fun showLateReportsIsEmpty() {
        noReportsTextView.visibility = View.VISIBLE
    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }

    //endregion

}