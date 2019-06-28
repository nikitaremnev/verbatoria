package com.verbatoria.presentation.late_send

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.remnev.verbatoria.R
import com.verbatoria.business.late_send.LateReportModel
import com.verbatoria.di.common.Injector
import com.verbatoria.di.late_send.LateSendComponent
import com.verbatoria.presentation.base.BasePresenterActivity
import com.verbatoria.presentation.base.BaseView
import com.verbatoria.presentation.common.Adapter
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