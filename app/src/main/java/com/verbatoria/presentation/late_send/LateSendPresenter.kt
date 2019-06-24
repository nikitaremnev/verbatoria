package com.verbatoria.presentation.late_send

import com.verbatoria.business.late_send.LateSendInteractor
import com.verbatoria.business.late_send.models.LateReportModel
import com.verbatoria.business.session.SessionInteractor
import com.verbatoria.presentation.base.BasePresenter
import com.verbatoria.presentation.late_send.item.LateReportViewHolder

/**
 * @author n.remnev
 */

private const val NO_POSITION = -1

class LateSendPresenter(
    private var lateSendInteractor: LateSendInteractor,
    private var sessionInteractor: SessionInteractor
) : BasePresenter<LateSendView>(), LateReportViewHolder.Callback {

    private val lateReportsList: MutableList<LateReportModel> = mutableListOf()

    private var selectedPosition = NO_POSITION

    private var isLateReportsLoaded = false

    init {
        loadLateReports()
    }

    override fun onAttachView(view: LateSendView) {
        super.onAttachView(view)
        if (isLateReportsLoaded) {
            updateLateReportsList()
            view.hideProgress()
        } else {
            view.showProgress()
        }
    }

    //region LateReportViewHolder.Callback

    override fun onLateReportClicked(position: Int) {
        //empty
    }

    //endregion

    private fun loadLateReports() {
        view?.showProgress()
        lateSendInteractor
            .lateReports
            .subscribe(
                { lateReports ->
                    lateReportsList.addAll(lateReports)
                    updateLateReportsList()
                    view?.hideProgress()
                },
                { error ->
                    view?.showLateReportsIsEmpty()
                    view?.hideProgress()
                    view?.showErrorSnackbar(error.localizedMessage)
                }
            )
//            .let(::addDisposable)
    }

    private fun submitResults() {
        sessionInteractor
            .submitResults(lateReportsList[selectedPosition].reportFileName)
            .subscribe(
                {
                    finishSession()
                },
                { error ->
                    view?.hideProgress()
                    view?.showErrorSnackbar(error.localizedMessage)
                }
            )
        //            .let(::addDisposable)

    }

    private fun finishSession() {
        sessionInteractor
            .finishSession(lateReportsList[selectedPosition].sessionId)
            .subscribe(
                {
                    cleanUp()
                },
                { error ->
                    view?.hideProgress()
                    view?.showErrorSnackbar(error.localizedMessage)
                }
            )
        //            .let(::addDisposable)

    }

    private fun cleanUp() {
        sessionInteractor
            .cleanUp()
            .subscribe(
                {
                    lateReportsList.removeAt(selectedPosition)
                    view?.updateLateReportsList(lateReportsList)
                    if (lateReportsList.isEmpty()) {
                        view?.showLateReportsIsEmpty()
                    }
                    view?.hideProgress()
                },
                { error ->
                    view?.hideProgress()
                    view?.showErrorSnackbar(error.localizedMessage)
                }
            )
        //            .let(::addDisposable)
    }

    private fun updateLateReportsList() {
        if (lateReportsList.isEmpty()) {
            view?.showLateReportsIsEmpty()
        } else {
            view?.updateLateReportsList(lateReportsList)
        }
    }

}
