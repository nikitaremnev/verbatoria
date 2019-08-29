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
        scheduleInteractor.getSchedule()
            .doAfterTerminate {
                isLoadingSchedule = false
                view?.hideInitialLoadScheduleProgress()
            }
            .doOnSubscribe {
                isLoadingSchedule = true
            }
            .subscribe({ scheduleDataSource ->
                this.scheduleDataSource = scheduleDataSource
                view?.setSchedule(scheduleDataSource)
            }, { error ->
                error.printStackTrace()
                view?.showErrorSnackbar(error.localizedMessage)
            })
            .let(::addDisposable)
    }

    override fun onAttachView(view: ScheduleView) {
        super.onAttachView(view)
        if (isLoadingSchedule) {
            view.showInitialLoadScheduleProgress()
        } else {
            view.hideInitialLoadScheduleProgress()
            scheduleDataSource?.let { scheduleDataSource ->
                view.setSchedule(scheduleDataSource)
            }
        }
    }

    //region ScheduleView.Callback

    override fun onClearScheduleClicked() {
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
                    view?.showErrorSnackbar(error.localizedMessage)
                })
                .let(::addDisposable)
        }
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
                    view?.showErrorSnackbar(error.localizedMessage)
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
                    view?.showErrorSnackbar(error.localizedMessage)
                })
                .let(::addDisposable)
        }
    }

    override fun onSaveScheduleClicked() {
        view?.showSaveScheduleConfirmationDialog()
    }

    override fun onNavigationClicked() {
        view?.close()
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
                    view?.showErrorSnackbar(error.localizedMessage)
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

}