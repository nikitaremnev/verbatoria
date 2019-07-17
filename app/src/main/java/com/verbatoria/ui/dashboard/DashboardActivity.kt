package com.verbatoria.ui.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.widget.EditText
import com.remnev.verbatoria.R
import com.verbatoria.di.Injector
import com.verbatoria.di.dashboard.DashboardComponent
import com.verbatoria.ui.base.BasePresenterActivity
import com.verbatoria.ui.base.BaseView
import com.verbatoria.ui.dashboard.settings.SettingsFragment

/**
 * @author n.remnev
 */

interface DashboardView : BaseView {

    fun openSettings()

    interface Callback {


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
    }

    //region DashboardView

    override fun openSettings() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragments_container, SettingsFragment.createFragment())
        transaction.commit()
    }

    //endregion

}