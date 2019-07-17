package com.verbatoria.ui.dashboard

import com.verbatoria.ui.base.BasePresenter

/**
 * @author n.remnev
 */

class DashboardPresenter : BasePresenter<DashboardView>(), DashboardView.Callback {

    override fun onAttachView(view: DashboardView) {
        super.onAttachView(view)
        view.openSettings()
    }

}