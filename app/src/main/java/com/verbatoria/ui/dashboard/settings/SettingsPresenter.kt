package com.verbatoria.ui.dashboard.settings

import com.verbatoria.business.dashboard.LocalesAvailable.ALBANIAN_LOCALE
import com.verbatoria.business.dashboard.LocalesAvailable.ARABIC_LOCALE
import com.verbatoria.business.dashboard.LocalesAvailable.BOSNIA_LOCALE
import com.verbatoria.business.dashboard.LocalesAvailable.BULGARIAN_LOCALE
import com.verbatoria.business.dashboard.LocalesAvailable.CROATIA_LOCALE
import com.verbatoria.business.dashboard.LocalesAvailable.ENGLISH_LOCALE
import com.verbatoria.business.dashboard.LocalesAvailable.GREECE_LOCALE
import com.verbatoria.business.dashboard.LocalesAvailable.HONG_KONG_LOCALE
import com.verbatoria.business.dashboard.LocalesAvailable.MACEDONIA_LOCALE
import com.verbatoria.business.dashboard.LocalesAvailable.MONGOLIAN_LOCALE
import com.verbatoria.business.dashboard.LocalesAvailable.RUSSIAN_LOCALE
import com.verbatoria.business.dashboard.LocalesAvailable.SLOVAKIA_LOCALE
import com.verbatoria.business.dashboard.LocalesAvailable.SLOVENIA_LOCALE
import com.verbatoria.business.dashboard.LocalesAvailable.SPAIN_LOCALE
import com.verbatoria.business.dashboard.LocalesAvailable.TURKEY_LOCALE
import com.verbatoria.business.dashboard.LocalesAvailable.UKRAINIAN_LOCALE
import com.verbatoria.domain.settings.SettingsManager.Companion.SETTINGS_ABOUT_APP_ID
import com.verbatoria.domain.settings.SettingsManager.Companion.SETTINGS_APP_LANGUAGE_ID
import com.verbatoria.domain.settings.SettingsManager.Companion.SETTINGS_EXIT_ID
import com.verbatoria.domain.settings.SettingsManager.Companion.SETTINGS_LATE_SEND_ID
import com.verbatoria.domain.settings.SettingsManager.Companion.SETTINGS_SCHEDULE_ID
import com.verbatoria.business.dashboard.settings.SettingsInteractor
import com.verbatoria.business.dashboard.settings.model.item.SettingsItemModel
import com.verbatoria.ui.base.BasePresenter
import com.verbatoria.ui.dashboard.settings.item.SettingsItemViewHolder
import org.slf4j.LoggerFactory

/**
 * @author n.remnev
 */

class SettingsPresenter(
    private val settingsInteractor: SettingsInteractor
) : BasePresenter<SettingsView>(), SettingsView.Callback, SettingsItemViewHolder.Callback {

    private val logger = LoggerFactory.getLogger("SettingsPresenter")

    private var settingsItemModels: MutableList<SettingsItemModel> = mutableListOf()

    private var isSettingsLoaded: Boolean = false

    init {
        getSettings()
    }

    override fun onAttachView(view: SettingsView) {
        super.onAttachView(view)
        if (isSettingsLoaded) {
            view.setSettingsItems(this.settingsItemModels)
        }
    }

    //region SettingsItemViewHolder.Callback

    override fun onSettingsItemClicked(position: Int) {
        when (settingsItemModels[position].id) {
            SETTINGS_SCHEDULE_ID -> view?.openSchedule()
            SETTINGS_LATE_SEND_ID -> view?.openLateSend()
            SETTINGS_APP_LANGUAGE_ID -> getAppLanguagesAvailability()
            SETTINGS_ABOUT_APP_ID -> getAppAndAndroidVersions()
            SETTINGS_EXIT_ID -> logout()
        }
    }

    override fun onRussianLanguageSelected() {
        setCurrentLocale(RUSSIAN_LOCALE)
    }

    override fun onEnglishLanguageSelected() {
        setCurrentLocale(ENGLISH_LOCALE)
    }

    override fun onHongKongLanguageSelected() {
        setCurrentLocale(HONG_KONG_LOCALE)
    }

    override fun onUkrainianLanguageSelected() {
        setCurrentLocale(UKRAINIAN_LOCALE)
    }

    override fun onBulgarianLanguageSelected() {
        setCurrentLocale(BULGARIAN_LOCALE)
    }

    override fun onTurkeyLanguageSelected() {
        setCurrentLocale(TURKEY_LOCALE)
    }

    override fun onArabicLanguageSelected() {
        setCurrentLocale(ARABIC_LOCALE)
    }

    override fun onBosnianLanguageSelected() {
        setCurrentLocale(BOSNIA_LOCALE)
    }

    override fun onGreeceLanguageSelected() {
        setCurrentLocale(GREECE_LOCALE)
    }

    override fun onMongolianLanguageSelected() {
        setCurrentLocale(MONGOLIAN_LOCALE)
    }

    override fun onMacedonianLanguageSelected() {
        setCurrentLocale(MACEDONIA_LOCALE)
    }

    override fun onSlovakianLanguageSelected() {
        setCurrentLocale(SLOVAKIA_LOCALE)
    }

    override fun onSpainLanguageSelected() {
        setCurrentLocale(SPAIN_LOCALE)
    }

    override fun onCroatianLanguageSelected() {
        setCurrentLocale(CROATIA_LOCALE)
    }

    override fun onSloveneLanguageSelected() {
        setCurrentLocale(SLOVENIA_LOCALE)
    }

    override fun onAlbanianLanguageSelected() {
        setCurrentLocale(ALBANIAN_LOCALE)
    }

    //endregion

    private fun getSettings() {
        isSettingsLoaded = false
        settingsInteractor.getSettings()
            .subscribe({ settingsItemModels ->
                this.settingsItemModels.addAll(settingsItemModels)
                view?.setSettingsItems(this.settingsItemModels)
                isSettingsLoaded = true
            }, { error ->
                logger.error("get settings error occurred", error)
                isSettingsLoaded = true
            })
            .let(::addDisposable)
    }

    private fun getAppAndAndroidVersions() {
        settingsInteractor.getAppAndAndroidVersions()
            .subscribe({ (appVersion, androidVersion) ->
                view?.showAboutAppDialog(appVersion, androidVersion)
            }, { error ->
                logger.error("get app android versions error occurred", error)
            })
            .let(::addDisposable)
    }

    private fun getAppLanguagesAvailability() {
        settingsInteractor.getAppLanguagesAvailability()
            .subscribe({ (languagesMap, currentLocale) ->
                view?.showAppLanguagesDialog(
                    languagesMap[RUSSIAN_LOCALE] ?: false,
                    languagesMap[ENGLISH_LOCALE] ?: false,
                    languagesMap[HONG_KONG_LOCALE] ?: false,
                    languagesMap[UKRAINIAN_LOCALE] ?: false,
                    languagesMap[BULGARIAN_LOCALE] ?: false,
                    languagesMap[TURKEY_LOCALE] ?: false,
                    languagesMap[ARABIC_LOCALE] ?: false,
                    languagesMap[BOSNIA_LOCALE] ?: false,
                    languagesMap[GREECE_LOCALE] ?: false,
                    languagesMap[MONGOLIAN_LOCALE] ?: false,
                    languagesMap[MACEDONIA_LOCALE] ?: false,
                    languagesMap[SLOVAKIA_LOCALE] ?: false,
                    languagesMap[SPAIN_LOCALE] ?: false,
                    languagesMap[CROATIA_LOCALE] ?: false,
                    languagesMap[SLOVENIA_LOCALE] ?: false,
                    languagesMap[ALBANIAN_LOCALE] ?: false,
                    currentLocale
                )
            }, { error ->
                logger.error("get app languages availability error occurred", error)
            })
            .let(::addDisposable)
    }

    private fun setCurrentLocale(selectedLocale: String) {
        view?.showProgress()
        settingsInteractor.updateCurrentLocale(selectedLocale)
            .subscribe({
                view?.hideProgress()
                if (selectedLocale == ARABIC_LOCALE) {
                    view?.setLocale(ENGLISH_LOCALE)
                } else {
                    view?.setLocale(selectedLocale)
                }
            }, { error ->
                logger.error("set current language error occurred", error)
                view?.hideProgress()
            })
            .let(::addDisposable)
    }

    private fun logout() {
        view?.showProgress()
        settingsInteractor.logout()
            .subscribe({
                view?.hideProgress()
                view?.openLogin()
            }, { error ->
                logger.error("clear database error occurred", error)
                view?.hideProgress()
            })
            .let(::addDisposable)
    }

}
