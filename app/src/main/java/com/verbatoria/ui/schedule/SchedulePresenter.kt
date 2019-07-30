package com.verbatoria.ui.schedule

import com.verbatoria.business.schedule.interactor.ScheduleInteractor
import com.verbatoria.domain.schedule.ScheduleDataSourceImpl
import com.verbatoria.ui.base.BasePresenter

/**
 * @author n.remnev
 */

class SchedulePresenter(
    private val scheduleInteractor: ScheduleInteractor
) : BasePresenter<ScheduleView>(), ScheduleView.Callback {

    init {

    }

    override fun onAttachView(view: ScheduleView) {
        super.onAttachView(view)
        view.setSchedule(ScheduleDataSourceImpl())
//        scheduleInteractor.getSchedule()
//            .subscribe({ scheduleDataSource ->
//
//            }, { error ->
//                error.printStackTrace()
//            })
//            .let(::addDisposable)
    }

    //region ScheduleView.Callback

    override fun onNavigationClicked() {
        view?.close()
    }

    //endregion

}