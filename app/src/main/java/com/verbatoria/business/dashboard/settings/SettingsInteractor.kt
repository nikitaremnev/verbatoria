package com.verbatoria.business.dashboard.settings

import android.os.Build
import com.remnev.verbatoria.BuildConfig
import com.verbatoria.business.dashboard.settings.model.SettingsItemModel
import com.verbatoria.domain.dashboard.settings.SettingsRepository
import com.verbatoria.domain.session.SessionManager
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import io.reactivex.Completable
import io.reactivex.Single

/**
 * @author n.remnev
 */

private val RUSSIAN_LOCALE = "ru"
private val ENGLISH_LOCALE = "en"
private val HONG_KONG_LOCALE = "ch"

interface SettingsInteractor {

    fun getSettings(): Single<List<SettingsItemModel>>

    fun getAppAndAndroidVersions(): Single<Pair<String, String>>

    fun getAppLanguagesAvailability(): Single<Triple<Boolean, Boolean, Boolean>>

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

    override fun getAppLanguagesAvailability(): Single<Triple<Boolean, Boolean, Boolean>> =
        Single.fromCallable {
            val localesAvailable = settingsRepository.getLocales()
            Triple(
                localesAvailable.contains(RUSSIAN_LOCALE),
                localesAvailable.contains(ENGLISH_LOCALE),
                localesAvailable.contains(HONG_KONG_LOCALE)
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