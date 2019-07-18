package com.verbatoria.ui.dashboard.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.remnev.verbatoria.R
import com.verbatoria.di.dashboard.DashboardComponent
import com.verbatoria.di.dashboard.info.InfoComponent
import com.verbatoria.ui.base.BasePresenterFragment
import com.verbatoria.ui.base.BaseView
import com.verbatoria.ui.common.dialog.ProgressDialog

/**
 * @author n.remnev
 */

private const val PROGRESS_DIALOG_TAG = "PROGRESS_DIALOG_TAG"

interface InfoView : BaseView {

    fun showProgress()

    fun hideProgress()

    interface Callback {



    }

}

class InfoFragment :
    BasePresenterFragment<InfoView, DashboardComponent, InfoFragment, InfoComponent, InfoPresenter>(),
    InfoView {

    companion object {

        fun createFragment(): InfoFragment = InfoFragment()

    }

    private var progressDialog: ProgressDialog? = null

    override fun buildComponent(
        parentComponent: DashboardComponent,
        savedState: Bundle?
    ): InfoComponent =
        parentComponent.plusInfoComponent()
            .build()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_info, container, false)
        return rootView

    }

    //region SettingsView

    override fun showProgress() {
        progressDialog = ProgressDialog()
        progressDialog?.show(activity?.supportFragmentManager, PROGRESS_DIALOG_TAG)
    }

    override fun hideProgress() {
        progressDialog?.dismiss()
    }

    //endregion

}