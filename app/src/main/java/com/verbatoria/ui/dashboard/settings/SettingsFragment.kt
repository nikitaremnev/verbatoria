package com.verbatoria.ui.dashboard.settings

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.remnev.verbatoria.R
import com.verbatoria.business.dashboard.settings.model.SettingsItemModel
import com.verbatoria.di.dashboard.DashboardComponent
import com.verbatoria.di.dashboard.settings.SettingsComponent
import com.verbatoria.ui.base.BasePresenterFragment
import com.verbatoria.ui.base.BaseView
import com.verbatoria.ui.common.Adapter
import javax.inject.Inject

/**
 * @author n.remnev
 */

interface SettingsView : BaseView {

    fun setSettingsItems(settingsItemModels: List<SettingsItemModel>)

    interface Callback {


    }

}

class SettingsFragment :
    BasePresenterFragment<SettingsView, DashboardComponent, SettingsFragment, SettingsComponent, SettingsPresenter>(),
    SettingsView {

    companion object {

        fun createFragment(): SettingsFragment = SettingsFragment()

    }

    @Inject
    lateinit var adapter: Adapter

    private lateinit var recyclerView: RecyclerView

    override fun buildComponent(
        parentComponent: DashboardComponent,
        savedState: Bundle?
    ): SettingsComponent =
        parentComponent.plusSettingsComponent()
            .build()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_settings, container, false).apply {
            recyclerView = this as RecyclerView
            recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = adapter
        }

    //region SettingsView

    override fun setSettingsItems(settingsItemModels: List<SettingsItemModel>) {
        Log.e("test", "SettingsFragment setSettingsItems")
        adapter.update(settingsItemModels)
    }

    //endregion

}