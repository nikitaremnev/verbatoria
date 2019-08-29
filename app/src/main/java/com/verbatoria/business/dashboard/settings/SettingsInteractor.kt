package com.verbatoria.business.dashboard.settings

import android.os.Build
import com.remnev.verbatoria.BuildConfig
import com.verbatoria.business.dashboard.settings.model.item.SettingsItemModel
import com.verbatoria.domain.dashboard.settings.SettingsRepository
import com.verbatoria.domain.session.manager.SessionManager
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import io.reactivex.Completable
import io.reactivex.Single
import com.verbatoria.business.dashboard.LocalesAvailable.ENGLISH_LOCALE
import com.verbatoria.business.dashboard.LocalesAvailable.HONG_KONG_LOCALE
import com.verbatoria.business.dashboard.LocalesAvailable.RUSSIAN_LOCALE
import com.verbatoria.business.dashboard.LocalesAvailable.UKRAINIAN_LOCALE

/**
 * @author n.remnev
 */

interface SettingsInteractor {

    fun getSettings(): Single<List<SettingsItemModel>>

    fun getAppAndAndroidVersions(): Single<Pair<String, String>>

    fun getAppLanguagesAvailability(): Single<Map<String, Boolean>>

    fun clearDatabase(): Completable

    fun logout(): Completable

}

class SettingsInteractorImpl(
    private val sessionManager: SessionManager,
    private val settingsRepository: SettingsRepository,
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

    override fun getAppLanguagesAvailability(): Single<Map<String, Boolean>> =
        Single.fromCallable {
            val localesAvailable = settingsRepository.getLocales()
            mapOf(
                Pair(RUSSIAN_LOCALE, localesAvailable.contains(RUSSIAN_LOCALE)),
                Pair(ENGLISH_LOCALE, localesAvailable.contains(ENGLISH_LOCALE)),
                Pair(HONG_KONG_LOCALE, localesAvailable.contains(HONG_KONG_LOCALE)),
                Pair(UKRAINIAN_LOCALE, localesAvailable.contains(UKRAINIAN_LOCALE))
            )
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun clearDatabase(): Completable =
        Completable.fromCallable {
            Thread.sleep(5000)
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun logout(): Completable =
        Completable.fromCallable {
            sessionManager.closeSession("logout")
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

}