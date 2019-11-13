package com.verbatoria.ui.schedule

import com.verbatoria.business.schedule.ScheduleInteractor
import com.verbatoria.domain.schedule.model.ScheduleDataSource
import com.verbatoria.ui.base.BasePresenter
import com.verbatoria.ui.common.adaptivetablelayout.OnItemClickListener

/**
 * @author n.remnev
 */

class SchedulePresenter(
    private val scheduleInteractor: ScheduleInteractor
) : BasePresenter<ScheduleView>(), ScheduleView.Callback, OnItemClickListener {

    private var scheduleDataSource: ScheduleDataSource? = null

    private var isLoadingSchedule: Boolean = false

    init {
        loadSchedule()
    }

    override fun onAttachView(view: ScheduleView) {
        super.onAttachView(view)
        if (isLoadingSchedule) {
            view.showInitialLoadScheduleProgress()
        } else {
            view.hideInitialLoadScheduleProgress()
            scheduleDataSource?.let { scheduleDataSource ->
                view.setSchedule(scheduleDataSource)
            } ?: run {
                view.hideInitialLoadTryAgain()
            }
        }
    }

    //region ScheduleView.Callback

    override fun onClearScheduleClicked() {
        view?.showClearScheduleConfirmationDialog()
    }

    override fun onNextWeekClicked() {
        view?.showLoadScheduleProgress()
        scheduleDataSource?.let { scheduleDataSource ->
            scheduleInteractor.getScheduleForNextWeek(scheduleDataSource)
                .doAfterTerminate {
                    view?.hideLoadScheduleProgress()
                }
                .subscribe({ scheduleDataSourceLoaded ->
                    this.scheduleDataSource = scheduleDataSourceLoaded
                    view?.setSchedule(scheduleDataSourceLoaded)
                }, { error ->
                    error.printStackTrace()
                    if (isViewVisible) {
                        view?.showErrorSnackbar(error.localizedMessage ?: "Internet connection error occurred")
                    } else {
                        addOperationToPending(Runnable {
                            view?.showErrorSnackbar(error.localizedMessage ?: "Internet connection error occurred")
                        })
                    }
                })
                .let(::addDisposable)
        }
    }

    override fun onPreviousWeekClicked() {
        view?.showLoadScheduleProgress()
        scheduleDataSource?.let { scheduleDataSource ->
            scheduleInteractor.getScheduleForPreviousWeek(scheduleDataSource)
                .doAfterTerminate {
                    view?.hideLoadScheduleProgress()
                }
                .subscribe({ scheduleDataSourceLoaded ->
                    this.scheduleDataSource = scheduleDataSourceLoaded
                    view?.setSchedule(scheduleDataSourceLoaded)
                }, { error ->
                    error.printStackTrace()
                    if (isViewVisible) {
                        view?.showErrorSnackbar(
                            error.localizedMessage ?: "Internet connection error occurred"
                        )
                    } else {
                        addOperationToPending(Runnable {
                            view?.showErrorSnackbar(
                                error.localizedMessage ?: "Internet connection error occurred"
                            )
                        })
                    }
                })
                .let(::addDisposable)
        }
    }

    override fun onSaveScheduleClicked() {
        view?.showSaveScheduleConfirmationDialog()
    }

    override fun onClearScheduleConfirmed() {
        scheduleDataSource?.let { scheduleDataSource ->
            view?.showClearScheduleProgressDialog()
            scheduleInteractor.clearSchedule(scheduleDataSource)
                .doAfterTerminate {
                    view?.hideClearScheduleProgressDialog()
                }
                .subscribe({
                    view?.updateScheduleAfterCleared()
                }, { error ->
                    error.printStackTrace()
                    if (isViewVisible) {
                        view?.showErrorSnackbar(error.localizedMessage ?: "Internet connection error occurred")
                    } else {
                        addOperationToPending(Runnable {
                            view?.showErrorSnackbar(error.localizedMessage ?: "Internet connection error occurred")

                        })
                    }
                })
                .let(::addDisposable)
        }
    }

    override fun onNavigationClicked() {
        view?.close()
    }

    override fun onTryAgainClicked() {
        loadSchedule()
    }

    override fun onWeeksForwardSaveSelected(weeksForward: Int) {
        scheduleDataSource?.let { scheduleDataSource ->
            view?.showSaveScheduleProgressDialog()
            scheduleInteractor.saveSchedule(scheduleDataSource, weeksForward)
                .doAfterTerminate {
                    view?.hideSaveScheduleProgressDialog()
                }
                .subscribe({
                    //empty
                }, { error ->
                    error.printStackTrace()
                    if (isViewVisible) {
                        view?.showErrorSnackbar(error.localizedMessage ?: "Internet connection error occurred")
                    } else {
                        addOperationToPending(Runnable {
                            view?.showErrorSnackbar(error.localizedMessage ?: "Internet connection error occurred")
                        })
                    }
                })
                .let(::addDisposable)
        }
    }

    //endregion

    //region AdaptiveTableLayout OnItemClickListener

    override fun onColumnHeaderClick(column: Int) {
        //empty
    }

    override fun onItemClick(row: Int, column: Int) {
        val scheduleCellItem = scheduleDataSource?.getScheduleCellItem(row, column)
        scheduleCellItem?.isSelected = !(scheduleCellItem?.isSelected ?: false)
        view?.updateScheduleCellAfterClicked(row, column)
    }

    override fun onLeftTopHeaderClick() {
        //empty
    }

    override fun onRowHeaderClick(row: Int) {
        //empty
    }

    //endregion

    private fun loadSchedule() {
        isLoadingSchedule = true
        view?.apply {
            showInitialLoadScheduleProgress()
            hideInitialLoadTryAgain()
        }

        scheduleInteractor.getSchedule()
            .subscribe({ scheduleDataSource ->
                this.scheduleDataSource = scheduleDataSource
                isLoadingSchedule = false
                this.view?.apply {
                    hideInitialLoadScheduleProgress()
                    setSchedule(scheduleDataSource)
                }
            }, { error ->
                isLoadingSchedule = false
                this.view?.apply {
                    hideInitialLoadScheduleProgress()
                    showInitialLoadTryAgain()
                    if (isViewVisible) {
                        showErrorSnackbar(error.localizedMessage ?: "Internet connection error occurred")
                    } else {
                        addOperationToPending(Runnable {
                            showErrorSnackbar(error.localizedMessage ?: "Internet connection error occurred")
                        })
                    }
                }
            })
            .let(::addDisposable)
    }

}