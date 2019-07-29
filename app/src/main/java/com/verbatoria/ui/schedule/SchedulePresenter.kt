package com.verbatoria.ui.schedule

import com.verbatoria.ui.base.BasePresenter

/**
 * @author n.remnev
 */

class SchedulePresenter : BasePresenter<ScheduleView>(), ScheduleView.Callback {

    init {

    }

    override fun onAttachView(view: ScheduleView) {
        super.onAttachView(view)

    }

    //region ScheduleView.Callback

    override fun onNavigationClicked() {
        view?.close()
    }

    //endregion

}