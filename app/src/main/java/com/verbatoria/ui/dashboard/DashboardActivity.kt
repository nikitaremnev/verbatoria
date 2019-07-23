package com.verbatoria.ui.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import com.remnev.verbatoria.R
import com.verbatoria.di.Injector
import com.verbatoria.di.dashboard.DashboardComponent
import com.verbatoria.ui.base.BasePresenterActivity
import com.verbatoria.ui.base.BaseView
import com.verbatoria.ui.blocked.BlockedFragment
import com.verbatoria.ui.dashboard.calendar.CalendarFragment
import com.verbatoria.ui.dashboard.info.InfoFragment
import com.verbatoria.ui.dashboard.settings.SettingsFragment

/**
 * @author n.remnev
 */

interface DashboardView : BaseView {

    fun setInfoItemSelected()

    fun openInfo()

    fun openCalendar()

    fun openSettings()

    fun openBlocked()

    interface Callback {

        fun onBottomNavigationItemSelected(itemId: Int)

    }

}

class DashboardActivity :
    BasePresenterActivity<DashboardView, DashboardPresenter, DashboardActivity, DashboardComponent>(),
    DashboardView {

    companion object {

        fun createIntent(context: Context): Intent =
            Intent(context, DashboardActivity::class.java)

    }


    private lateinit var bottomNavigationView: BottomNavigationView

    override fun getLayoutResourceId(): Int = R.layout.activity_dashboard

    override fun buildComponent(injector: Injector, savedState: Bundle?): DashboardComponent =
        injector.plusDashboardComponent()
            .build()

    override fun initViews(savedState: Bundle?) {
        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            presenter.onBottomNavigationItemSelected(item.itemId)
            true
        }
    }

    //region DashboardView

    override fun setInfoItemSelected() {
        bottomNavigationView.selectedItemId = R.id.navigation_info
    }

    override fun openInfo() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragments_container, InfoFragment.createFragment())
        transaction.commit()
    }

    override fun openCalendar() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragments_container, CalendarFragment.createFragment())
        transaction.commit()
    }

    override fun openSettings() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragments_container, SettingsFragment.createFragment())
        transaction.commit()
    }

    override fun openBlocked() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragments_container, BlockedFragment.createFragment())
        transaction.commit()
    }

    //endregion

//    if (getIntent().hasExtra(EXTRA_FINISH_SESSION))
//    {
//        if (intent.getBooleanExtra(EXTRA_FINISH_SESSION, false)) {
//            val builder = AlertDialog.Builder(this)
//                .setMessage(getString(R.string.dashboard_session_finish))
//            builder.setNegativeButton(getString(R.string.ok), null)
//            builder.create().show()
//        } else {
//            val builder = AlertDialog.Builder(this)
//                .setTitle(getString(R.string.dashboard_session_finish_error_title))
//                .setIcon(R.drawable.ic_neurointerface_error)
//                .setMessage(getString(R.string.dashboard_session_finish_error_message))
//            builder.setNegativeButton(getString(R.string.all_understand), null)
//            builder.create().show()
//        }
//    }

}