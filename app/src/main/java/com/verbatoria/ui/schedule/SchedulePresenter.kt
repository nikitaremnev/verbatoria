package com.verbatoria.ui.schedule

import android.util.Log
import com.verbatoria.business.schedule.interactor.ScheduleInteractor
import com.verbatoria.domain.schedule.ScheduleDataSource
import com.verbatoria.ui.base.BasePresenter
import com.verbatoria.ui.common.adaptivetablelayout.OnItemClickListener

/**
 * @author n.remnev
 */

class SchedulePresenter(
    private val scheduleInteractor: ScheduleInteractor
) : BasePresenter<ScheduleView>(), ScheduleView.Callback, OnItemClickListener {

    private var scheduleDataSource: ScheduleDataSource? = null

    init {

    }

    override fun onAttachView(view: ScheduleView) {
        super.onAttachView(view)
        scheduleInteractor.getSchedule()
            .subscribe({ scheduleDataSource ->
                this.scheduleDataSource = scheduleDataSource
                view.setSchedule(scheduleDataSource)
            }, { error ->
                error.printStackTrace()
            })
            .let(::addDisposable)
    }

    //region ScheduleView.Callback

    override fun onNavigationClicked() {
        view?.close()
    }

    //endregion

    //region AdaptiveTableLayout OnItemClickListener

    override fun onColumnHeaderClick(column: Int) {
        //empty
    }

    override fun onItemClick(row: Int, column: Int) {
        Log.e("test", "SchedulePresenter row $row column $column")
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

//private val TAG = SchedulePresenter::class.java.simpleName
//
//private var mScheduleInteractor: IScheduleInteractor
//private var mScheduleView: IScheduleView? = null
//
//private var mScheduleDataSource: IScheduleDataSource<*, *, *, *>? = null
//
//fun SchedulePresenter(scheduleInteractor: IScheduleInteractor): ??? {
//    mScheduleInteractor = scheduleInteractor
//}
//
//protected override fun onStart() {
//    super.onStart()
//    mScheduleView!!.showProgress()
//    mScheduleInteractor.schedule
//        .doOnComplete { mScheduleView!!.hideProgress() }
//        .subscribe(
//            Consumer<IScheduleDataSource> { this.handleScheduleReceived(it) },
//            Consumer<Throwable> { this.handleScheduleError(it) })
//}
//
//override fun bindView(scheduleView: IScheduleView) {
//    mScheduleView = scheduleView
//}
//
//override fun unbindView() {
//    mScheduleView = null
//}
//
//override fun onItemClicked(row: Int, column: Int) {
//    val scheduleItemModel = mScheduleDataSource!!.getItemData(row, column) as ScheduleItemModel
//    scheduleItemModel.isSelected = !scheduleItemModel.isSelected
//    mScheduleView!!.notifyItemChanged(row, column, scheduleItemModel.isSelected)
//}
//
//override fun onClearScheduleClicked() {
//    mScheduleView!!.showScheduleClearConfirmation()
//}
//
//override fun onNextWeekClicked() {
//    mScheduleView!!.showProgress()
//    mScheduleInteractor.getScheduleNextWeek(mScheduleDataSource)
//        .doOnComplete { mScheduleView!!.hideProgress() }
//        .subscribe(
//            Consumer<IScheduleDataSource> { this.handleScheduleReceived(it) },
//            Consumer<Throwable> { this.handleScheduleError(it) })
//}
//
//override fun onPreviousWeekClicked() {
//    mScheduleView!!.showProgress()
//    mScheduleInteractor.getSchedulePreviousWeek(mScheduleDataSource)
//        .doOnComplete { mScheduleView!!.hideProgress() }
//        .subscribe(
//            Consumer<IScheduleDataSource> { this.handleScheduleReceived(it) },
//            Consumer<Throwable> { this.handleScheduleError(it) })
//}
//
//override fun onSaveScheduleClicked() {
//    mScheduleView!!.confirmSaveSchedule()
//}
//
//override fun clearSchedule() {
//    mScheduleView!!.showProgress()
//    mScheduleInteractor
//        .deleteSchedule(mScheduleDataSource, 0)
//        .doOnComplete { mScheduleView!!.hideProgress() }
//        .subscribe(
//            Consumer<Int> { this.handleScheduleCleared(it) },
//            Consumer<Throwable> { this.handleScheduleError(it) })
//}
//
//override fun saveSchedule(weeksForwardCount: Int) {
//    mScheduleView!!.showProgress()
//    mScheduleInteractor.deleteSchedule(mScheduleDataSource, weeksForwardCount)
//        .subscribe(
//            Consumer<Int> { this.handleScheduleDeleted(it) },
//            Consumer<Throwable> { this.handleScheduleError(it) })
//}
//
//override fun saveState(outState: Bundle) {
//
//}
//
//override fun restoreState(savedInstanceState: Bundle) {
//
//}
//
//private fun handleScheduleReceived(scheduleDataSource: IScheduleDataSource<*, *, *, *>) {
//    mScheduleDataSource = scheduleDataSource
//    mScheduleView!!.setUpAdapter(scheduleDataSource)
//    mScheduleView!!.showScheduleLoaded(mScheduleDataSource!!.weekTitle)
//}
//
//private fun handleScheduleDeleted(weeksForwardCount: Int) {
//    mScheduleView!!.showProgress()
//    mScheduleInteractor.saveSchedule(mScheduleDataSource, weeksForwardCount)
//        .doOnComplete { mScheduleView!!.hideProgress() }
//        .subscribe(
//            Consumer<IScheduleDataSource> { this.handleScheduleSaved(it) },
//            Consumer<Throwable> { this.handleScheduleError(it) })
//}
//
//private fun handleScheduleCleared(weeksForwardCount: Int) {
//    mScheduleDataSource!!.clearSchedule()
//    mScheduleView!!.notifyScheduleCleared()
//    mScheduleView!!.showScheduleSaved()
//}
//
//private fun handleScheduleSaved(scheduleDataSource: IScheduleDataSource<*, *, *, *>) {
//    mScheduleDataSource = scheduleDataSource
//    mScheduleView!!.setUpAdapter(scheduleDataSource)
//    mScheduleView!!.showScheduleSaved()
//}
//
//private fun handleScheduleError(throwable: Throwable) {
//    mScheduleView!!.showError(throwable.localizedMessage)
//    throwable.printStackTrace()
//}