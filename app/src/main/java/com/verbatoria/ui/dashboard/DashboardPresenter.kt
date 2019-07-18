package com.verbatoria.ui.dashboard

import com.remnev.verbatoria.R
import com.verbatoria.ui.base.BasePresenter

/**
 * @author n.remnev
 */

class DashboardPresenter : BasePresenter<DashboardView>(), DashboardView.Callback {

    override fun onAttachView(view: DashboardView) {
        super.onAttachView(view)
        view.openSettings()
    }

    //region DashboardView.Callback

    override fun onBottomNavigationItemSelected(itemId: Int) {
        when (itemId) {
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

    //endregion

}