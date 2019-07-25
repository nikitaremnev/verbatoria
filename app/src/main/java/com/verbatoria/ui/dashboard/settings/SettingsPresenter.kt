package com.verbatoria.ui.dashboard.settings

import com.verbatoria.business.dashboard.LocalesAvailable.ENGLISH_LOCALE
import com.verbatoria.business.dashboard.LocalesAvailable.HONG_KONG_LOCALE
import com.verbatoria.business.dashboard.LocalesAvailable.RUSSIAN_LOCALE
import com.verbatoria.business.dashboard.LocalesAvailable.UKRAINIAN_LOCALE
import com.verbatoria.business.dashboard.settings.SettingsConfigurator.Companion.SETTINGS_ABOUT_APP_ID
import com.verbatoria.business.dashboard.settings.SettingsConfigurator.Companion.SETTINGS_APP_LANGUAGE_ID
import com.verbatoria.business.dashboard.settings.SettingsConfigurator.Companion.SETTINGS_CLEAR_DATABASE_ID
import com.verbatoria.business.dashboard.settings.SettingsConfigurator.Companion.SETTINGS_EXIT_ID
import com.verbatoria.business.dashboard.settings.SettingsConfigurator.Companion.SETTINGS_LATE_SEND_ID
import com.verbatoria.business.dashboard.settings.SettingsConfigurator.Companion.SETTINGS_SCHEDULE_ID
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

    //region SettingsView.Callback

    override fun onDatabaseClearConfirmed() {
        clearDatabase()
    }

    //endregion

    //region SettingsItemViewHolder.Callback

    override fun onSettingsItemClicked(position: Int) {
        when (settingsItemModels[position].id) {
            SETTINGS_SCHEDULE_ID -> view?.openSchedule()
            SETTINGS_LATE_SEND_ID -> view?.openLateSend()
            SETTINGS_CLEAR_DATABASE_ID -> view?.showClearDatabaseConfirmationDialog()
            SETTINGS_APP_LANGUAGE_ID -> getAppLanguagesAvailability()
            SETTINGS_ABOUT_APP_ID -> getAppAndAndroidVersions()
            SETTINGS_EXIT_ID -> logout()
        }
    }

    override fun onRussianLanguageSelected() {

    }

    override fun onEnglishLanguageSelected() {

    }

    override fun onHongKongLanguageSelected() {

    }

    override fun onUkrainianLanguageSelected() {

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
            .subscribe({ languagesMap ->
                view?.showAppLanguagesDialog(
                    languagesMap[RUSSIAN_LOCALE] ?: false,
                    languagesMap[ENGLISH_LOCALE] ?: false,
                    languagesMap[HONG_KONG_LOCALE] ?: false,
                    languagesMap[UKRAINIAN_LOCALE] ?: false
                )
            }, { error ->
                logger.error("get app languages availability error occurred", error)
            })
            .let(::addDisposable)
    }

    private fun clearDatabase() {
        view?.showProgress()
        settingsInteractor.clearDatabase()
            .subscribe({
                view?.hideProgress()
            }, { error ->
                logger.error("clear database error occurred", error)
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