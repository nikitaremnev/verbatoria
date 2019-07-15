package com.verbatoria.ui.dashboard.settings

import com.verbatoria.business.dashboard.settings.SettingsInteractor
import com.verbatoria.business.dashboard.settings.model.SettingsItemModel
import com.verbatoria.ui.base.BasePresenter
import com.verbatoria.ui.dashboard.settings.item.SettingsItemViewHolder

/**
 * @author n.remnev
 */

class SettingsPresenter(
    private val settingsInteractor: SettingsInteractor
) : BasePresenter<SettingsView>(), SettingsView.Callback, SettingsItemViewHolder.Callback {

    private var settingsItemModels: List<SettingsItemModel> = listOf()

    init {
        getSettings()
    }

    override fun onAttachView(view: SettingsView) {
        super.onAttachView(view)
        view.setSettingsItems(this.settingsItemModels)
    }

    //region SettingsView.Callback

    //endregion

    //region SettingsItemViewHolder.Callback

    override fun onSettingsItemClicked(position: Int) {
        //empty
    }

    //endregion

    private fun getSettings() {
        settingsInteractor.getSettings()
            .subscribe({ settingsItemModels ->
                this.settingsItemModels = settingsItemModels
                view?.setSettingsItems(this.settingsItemModels)
            }, { error ->
                error.printStackTrace()
            })
            .let(::addDisposable)
    }

}