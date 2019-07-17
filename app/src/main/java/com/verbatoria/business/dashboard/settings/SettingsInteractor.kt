package com.verbatoria.business.dashboard.settings

import android.os.Build
import com.remnev.verbatoria.BuildConfig
import com.verbatoria.business.dashboard.settings.model.SettingsItemModel
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import io.reactivex.Single

/**
 * @author n.remnev
 */

interface SettingsInteractor {

    fun getSettings(): Single<List<SettingsItemModel>>

    fun getAppAndAndroidVersions(): Single<Pair<String, String>>

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

    override fun getAppAndAndroidVersions(): Single<Pair<String, String>> =
        Single.fromCallable {
            Pair(BuildConfig.VERSION_NAME, Build.VERSION.RELEASE)
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

}