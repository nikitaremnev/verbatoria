package com.verbatoria.ui.late_send

import com.remnev.verbatoria.R
import com.verbatoria.business.late_send.LateSendInteractor
import com.verbatoria.domain.late_send.model.LateSend
import com.verbatoria.domain.late_send.model.LateSendState
import com.verbatoria.ui.base.BasePresenter
import com.verbatoria.ui.late_send.item.LateReportViewHolder

/**
 * @author n.remnev
 */

class LateSendPresenter(
    private var lateSendInteractor: LateSendInteractor
) : BasePresenter<LateSendView>(), LateReportViewHolder.Callback, LateSendView.Callback {

    private val lateSendList: MutableList<LateSend> = mutableListOf()

    private var isLateReportsLoaded = false

    private var selectedForDeleteLateSendPosition = -1

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
        val lateSend = lateSendList[position]
        if (lateSend.state == LateSendState.HAS_NOTHING) {
            view?.showLateSendStateHint(R.string.late_send_state_nothing)
            return
        }
        else if (lateSend.state == LateSendState.HAS_DATA) {
            view?.showLateSendStateHint(R.string.late_send_state_has_data)
            return
        }
        view?.showProgress()
        lateSendInteractor
            .sendLateSend(lateSendList[position])
            .doOnComplete {
                this.lateSendList.removeAt(position)
                updateLateReportsList()
                view?.hideProgress()
            }
            .subscribe(
                { submitProgress ->
                    view?.showSubmitProgress(submitProgress.progressResourceId)
                },
                { error ->
                    view?.hideProgress()
                    view?.showErrorSnackbar(error.localizedMessage ?: "Send late send error occurred")
                    loadLateReports()
                }
            )
            .let(::addDisposable)
    }

    override fun onLateReportDeleteClicked(position: Int) {
        selectedForDeleteLateSendPosition = position
        val lateSend = lateSendList[selectedForDeleteLateSendPosition]
        view?.showDeleteConfirmationDialog(lateSend.reportId)
    }

    override fun onTestButtonClicked() {
        view?.showProgress()
        lateSendInteractor
            .sendLateSend("15348")
            .doOnComplete {
                view?.hideProgress()
            }
            .subscribe(
                { submitProgress ->
                    view?.showSubmitProgress(submitProgress.progressResourceId)
                },
                { error ->
                    view?.hideProgress()
                    view?.showErrorSnackbar(error.localizedMessage ?: "Send late send error occurred")
                    error.printStackTrace()
                }
            )
            .let(::addDisposable)
    }

    //endregion

    //region LateSendView.Callback

    override fun onDeleteConfirmed() {
        val lateSend = lateSendList[selectedForDeleteLateSendPosition]
        view?.showProgress()
        lateSendInteractor
            .deleteLateSend(lateSend)
            .subscribe(
                {
                    this.lateSendList.removeAt(selectedForDeleteLateSendPosition)
                    updateLateReportsList()
                    view?.hideProgress()
                },
                { error ->
                    view?.hideProgress()
                    view?.showErrorSnackbar(error.localizedMessage ?: "Delete late send error occurred")
                }
            )
            .let(::addDisposable)
    }

    override fun onNavigationClicked() {
        view?.finish()
    }

    //endregion

    private fun loadLateReports() {
        view?.showProgress()
        lateSendInteractor
            .findAllLateSend()
            .doAfterTerminate {
                isLateReportsLoaded = true
            }
            .subscribe(
                { lateSendList ->
                    this.lateSendList.clear()
                    this.lateSendList.addAll(lateSendList)
                    updateLateReportsList()
                    view?.hideProgress()
                },
                { error ->
                    view?.showLateSendListIsEmpty()
                    view?.hideProgress()
                    view?.showErrorSnackbar(error.localizedMessage ?: "Load late sends error occurred")
                }
            )
            .let(::addDisposable)
    }

    private fun updateLateReportsList() {
        if (lateSendList.isEmpty()) {
            view?.showLateSendListIsEmpty()
        } else {
            view?.updateLateSendList(lateSendList)
        }
    }

}
