package com.verbatoria.ui.dashboard

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
        if (isBlocked) {
            view?.openBlocked()
        } else {
            when (selectedNavigationItemId) {
                R.id.navigation_info -> {
                    view?.openInfo()
                }
                R.id.navigation_calendar -> {
                    view?.openCalendar()
                }
                R.id.navigation_settings -> {
                    view?.openSettings()
                }
            }
        }
    }

    private fun getIsBlocked() {
        dashboardInteractor.isBlocked()
            .subscribe({ isBlocked ->
                this.isBlocked = isBlocked
                view?.openBlocked()
                if (isBlocked) {
                    updateInfo()
                }
            }, { error ->
                logger.error("error while get is blocked occurred", error)
            })
            .let(::addDisposable)
    }

    private fun updateInfo() {
        dashboardInteractor.updateInfo()
            .subscribe({ isBlocked ->
                this.isBlocked = isBlocked
                if (isBlocked) {
                    view?.apply {
                        openBlocked()
                    }
                } else {
                    view?.apply {
                        setInfoItemSelected()
                        openInfo()
                    }
                }
            }, { error ->
                logger.error("error while get is blocked occurred", error)
            })
            .let(::addDisposable)
    }

}