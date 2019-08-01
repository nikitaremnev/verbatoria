package com.verbatoria.ui.dashboard

import android.util.Log
import com.remnev.verbatoria.R
import com.verbatoria.business.dashboard.DashboardInteractor
import com.verbatoria.ui.base.BasePresenter
import org.slf4j.LoggerFactory

/**
 * @author n.remnev
 */

class DashboardPresenter(
    private val dashboardInteractor: DashboardInteractor
) : BasePresenter<DashboardView>(), DashboardView.Callback {

    private val logger = LoggerFactory.getLogger("DashboardPresenter")

    private var selectedNavigationItemId: Int = R.id.navigation_info

    private var isBlocked: Boolean = false

    init {
        Log.e("test", "DashboardPresenter init")

        getIsBlocked()
    }

    override fun onAttachView(view: DashboardView) {
        super.onAttachView(view)
        openFragmentByItemId()
    }

    //region DashboardView.Callback

    override fun onBottomNavigationItemSelected(itemId: Int) {
        selectedNavigationItemId = itemId
        openFragmentByItemId()
    }

    //endregion

    private fun openFragmentByItemId() {
        Log.e("test", "DashboardPresenter openFragmentByItemId")
        if (isBlocked) {
            Log.e("test", "DashboardPresenter openFragmentByItemId isBlocked")

            view?.openBlocked()
        } else {
            Log.e("test", "DashboardPresenter openFragmentByItemId else")

            when (selectedNavigationItemId) {
                R.id.navigation_info -> {
                    Log.e("test", "DashboardPresenter openInfo")
                    view?.openInfo()
                }
                R.id.navigation_calendar -> {
                    Log.e("test", "DashboardPresenter openCalendar")
                    view?.openCalendar()
                }
                R.id.navigation_settings -> {
                    Log.e("test", "DashboardPresenter openSettings")

                    view?.openSettings()
                }
            }
        }
    }

    private fun getIsBlocked() {
        Log.e("test", "DashboardPresenter getIsBlocked")

        dashboardInteractor.isBlocked()
            .subscribe({ isBlocked ->
                this.isBlocked = isBlocked
                Log.e("test", "DashboardPresenter getIsBlocked isBlocked $isBlocked")

                if (isBlocked) {
                    view?.openBlocked()
                }
                updateInfo()
            }, { error ->
                logger.error("error while get is blocked occurred", error)
            })
            .let(::addDisposable)
    }

    private fun updateInfo() {
        Log.e("test", "DashboardPresenter updateInfo")
        dashboardInteractor.updateInfo()
            .subscribe({ isBlocked ->
                Log.e("test", "DashboardPresenter updateInfo isBlocked $isBlocked")
                this.isBlocked = isBlocked
                if (isBlocked) {
                    view?.openBlocked()
                }
            }, { error ->
                logger.error("error while get is blocked occurred", error)
            })
            .let(::addDisposable)
    }

}