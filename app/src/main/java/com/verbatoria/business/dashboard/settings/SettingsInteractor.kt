package com.verbatoria.business.dashboard.settings

import android.os.Build
import com.remnev.verbatoria.BuildConfig
import com.verbatoria.business.dashboard.LocalesAvailable.ARABIC_LOCALE
import com.verbatoria.business.dashboard.LocalesAvailable.BOSNIAN_LOCALE_FROM_SERVER
import com.verbatoria.business.dashboard.LocalesAvailable.BOSNIA_LOCALE
import com.verbatoria.business.dashboard.LocalesAvailable.BULGARIAN_LOCALE
import com.verbatoria.business.dashboard.settings.model.item.SettingsItemModel
import com.verbatoria.domain.session.manager.SessionManager
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import io.reactivex.Completable
import io.reactivex.Single
import com.verbatoria.business.dashboard.LocalesAvailable.ENGLISH_LOCALE
import com.verbatoria.business.dashboard.LocalesAvailable.GREECE_LOCALE
import com.verbatoria.business.dashboard.LocalesAvailable.HONG_KONG_LOCALE
import com.verbatoria.business.dashboard.LocalesAvailable.HONG_KONG_LOCALE_FROM_SERVER
import com.verbatoria.business.dashboard.LocalesAvailable.MONGOLIAN_LOCALE
import com.verbatoria.business.dashboard.LocalesAvailable.RUSSIAN_LOCALE
import com.verbatoria.business.dashboard.LocalesAvailable.TURKEY_LOCALE
import com.verbatoria.business.dashboard.LocalesAvailable.UKRAINE_LOCALE_FROM_SERVER
import com.verbatoria.business.dashboard.LocalesAvailable.UKRAINIAN_LOCALE
import com.verbatoria.domain.settings.SettingsManager

/**
 * @author n.remnev
 */

interface SettingsInteractor {

    fun getSettings(): Single<List<SettingsItemModel>>

    fun getAppAndAndroidVersions(): Single<Pair<String, String>>

    fun getAppLanguagesAvailability(): Single<Pair<Map<String, Boolean>, String>>

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

    override fun getAppLanguagesAvailability(): Single<Pair<Map<String, Boolean>, String>> =
        Single.fromCallable {
            val localesAvailable = settingsManager.getLocales()
            val currentLocale = settingsManager.getCurrentLocale()
            Pair(mapOf(
                    Pair(RUSSIAN_LOCALE, localesAvailable.contains(RUSSIAN_LOCALE)),
                    Pair(ENGLISH_LOCALE, localesAvailable.contains(ENGLISH_LOCALE)),
                    Pair(HONG_KONG_LOCALE, localesAvailable.contains(HONG_KONG_LOCALE_FROM_SERVER)),
                    Pair(UKRAINIAN_LOCALE, localesAvailable.contains(UKRAINE_LOCALE_FROM_SERVER)),
                    Pair(BULGARIAN_LOCALE, localesAvailable.contains(BULGARIAN_LOCALE)),
                    Pair(TURKEY_LOCALE, localesAvailable.contains(TURKEY_LOCALE)),
                    Pair(ARABIC_LOCALE, localesAvailable.contains(ARABIC_LOCALE)),
                    Pair(BOSNIA_LOCALE, localesAvailable.contains(BOSNIA_LOCALE)),
                    Pair(BOSNIA_LOCALE, localesAvailable.contains(BOSNIAN_LOCALE_FROM_SERVER)),
                    Pair(GREECE_LOCALE, localesAvailable.contains(GREECE_LOCALE)),
                    Pair(MONGOLIAN_LOCALE, localesAvailable.contains(MONGOLIAN_LOCALE))
            ), currentLocale)
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun updateCurrentLocale(locale: String): Completable =
        Completable.fromCallable {
            when (locale) {
                HONG_KONG_LOCALE -> {
                    settingsManager.updateCurrentLocale(HONG_KONG_LOCALE_FROM_SERVER)
                }
                UKRAINIAN_LOCALE -> {
                    settingsManager.updateCurrentLocale(UKRAINE_LOCALE_FROM_SERVER)
                }
                BOSNIA_LOCALE -> {
                    settingsManager.updateCurrentLocale(BOSNIAN_LOCALE_FROM_SERVER)
                }
                GREECE_LOCALE -> {
                    settingsManager.updateCurrentLocale(GREECE_LOCALE)
                }
                MONGOLIAN_LOCALE -> {
                    settingsManager.updateCurrentLocale(MONGOLIAN_LOCALE)
                }
                else -> {
                    settingsManager.updateCurrentLocale(locale)
                }
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
