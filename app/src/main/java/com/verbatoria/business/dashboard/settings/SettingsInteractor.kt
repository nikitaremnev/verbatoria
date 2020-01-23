package com.verbatoria.business.dashboard.settings

import android.os.Build
import com.remnev.verbatoria.BuildConfig
import com.verbatoria.business.dashboard.LocalesAvailable.BULGARIAN_LOCALE
import com.verbatoria.business.dashboard.settings.model.item.SettingsItemModel
import com.verbatoria.domain.session.manager.SessionManager
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import io.reactivex.Completable
import io.reactivex.Single
import com.verbatoria.business.dashboard.LocalesAvailable.ENGLISH_LOCALE
import com.verbatoria.business.dashboard.LocalesAvailable.HONG_KONG_LOCALE
import com.verbatoria.business.dashboard.LocalesAvailable.RUSSIAN_LOCALE
import com.verbatoria.business.dashboard.LocalesAvailable.UKRAINIAN_LOCALE
import com.verbatoria.domain.settings.SettingsManager

/**
 * @author n.remnev
 */

private const val HONG_KONG_LOCALE_FROM_SERVER = "zh-CN"

interface SettingsInteractor {

    fun getSettings(): Single<List<SettingsItemModel>>

    fun getAppAndAndroidVersions(): Single<Pair<String, String>>

    fun getAppLanguagesAvailability(): Single<Map<String, Boolean>>

    fun updateCurrentLocale(locale: String): Completable

    fun logout(): Completable

}

class SettingsInteractorImpl(
    private val sessionManager: SessionManager,
    private val settingsManager: SettingsManager,
    private val schedulersFactory: RxSchedulersFactory
) : SettingsInteractor {

    override fun getSettings(): Single<List<SettingsItemModel>> =
        Single.fromCallable {
            settingsManager.getSettingsItemModels()
        }
            .subscribeOn(schedulersFactory.database)
            .observeOn(schedulersFactory.main)

    override fun getAppAndAndroidVersions(): Single<Pair<String, String>> =
        Single.fromCallable {
            Pair(BuildConfig.VERSION_NAME, Build.VERSION.RELEASE)
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun getAppLanguagesAvailability(): Single<Map<String, Boolean>> =
        Single.fromCallable {
            val localesAvailable = settingsManager.getLocales()
            mapOf(
                Pair(RUSSIAN_LOCALE, localesAvailable.contains(RUSSIAN_LOCALE)),
                Pair(ENGLISH_LOCALE, localesAvailable.contains(ENGLISH_LOCALE)),
                Pair(HONG_KONG_LOCALE, localesAvailable.contains(HONG_KONG_LOCALE_FROM_SERVER)),
                Pair(UKRAINIAN_LOCALE, localesAvailable.contains(UKRAINIAN_LOCALE)),
                Pair(BULGARIAN_LOCALE, localesAvailable.contains(BULGARIAN_LOCALE))
            )
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun updateCurrentLocale(locale: String): Completable =
        Completable.fromCallable {
            if (locale == HONG_KONG_LOCALE) {
                settingsManager.updateCurrentLocale(HONG_KONG_LOCALE_FROM_SERVER)
            } else {
                settingsManager.updateCurrentLocale(locale)
            }
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