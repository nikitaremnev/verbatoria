package com.verbatoria.ui.dashboard

import com.remnev.verbatoria.R
import com.verbatoria.ui.base.BasePresenter

/**
 * @author n.remnev
 */

class DashboardPresenter : BasePresenter<DashboardView>(), DashboardView.Callback {

    private var selectedNavigationItemId = R.id.navigation_info

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