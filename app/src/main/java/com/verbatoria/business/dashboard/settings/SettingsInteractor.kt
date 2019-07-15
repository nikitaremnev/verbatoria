package com.verbatoria.business.dashboard.settings

import com.verbatoria.business.dashboard.settings.model.SettingsItemModel
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import io.reactivex.Single

/**
 * @author n.remnev
 */

interface SettingsInteractor {

    fun getSettings(): Single<List<SettingsItemModel>>

}

class SettingsInteractorImpl(
    private val settingsConfigurator: SettingsConfigurator,
    private val schedulersFactory: RxSchedulersFactory
) : SettingsInteractor {

    override fun getSettings(): Single<List<SettingsItemModel>> =
        Single.fromCallable {
            settingsConfigurator.getSettingsItemModels()
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

}