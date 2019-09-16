package com.verbatoria.domain.settings

import com.remnev.verbatoria.R
import com.verbatoria.business.dashboard.settings.model.item.SettingsItemModel
import com.verbatoria.domain.dashboard.info.manager.InfoManager
import com.verbatoria.domain.dashboard.settings.SettingsRepository
import com.verbatoria.infrastructure.retrofit.endpoints.settings.SettingsEndpoint
import com.verbatoria.infrastructure.retrofit.endpoints.settings.model.params.SetLocaltionLanguageParamsDto

/**
 * @author n.remnev
 */

interface SettingsManager {

    companion object {

        const val SETTINGS_SCHEDULE_ID = 0

        const val SETTINGS_LATE_SEND_ID = 1

        const val SETTINGS_APP_LANGUAGE_ID = 3

        const val SETTINGS_ABOUT_APP_ID = 4

        const val SETTINGS_EXIT_ID = 5

    }

    fun getSettingsItemModels(): List<SettingsItemModel>

    fun getLocales(): List<String>

    fun updateCurrentLocale(locale: String)

}

class SettingsManagerImpl(
    private val settingsRepository: SettingsRepository,
    private val infoManager: InfoManager,
    private val settingsEndpoint: SettingsEndpoint
) : SettingsManager {

    override fun getSettingsItemModels(): List<SettingsItemModel> =
        listOf(
            SettingsItemModel(
                id = SettingsManager.SETTINGS_SCHEDULE_ID,
                titleResourceId = R.string.schedule_title,
                logoResourceId = R.drawable.ic_schedule
            ),
            SettingsItemModel(
                id = SettingsManager.SETTINGS_LATE_SEND_ID,
                titleResourceId = R.string.late_send_title,
                logoResourceId = R.drawable.ic_report
            ),
            SettingsItemModel(
                id = SettingsManager.SETTINGS_APP_LANGUAGE_ID,
                titleResourceId = R.string.settings_item_locale,
                logoResourceId = R.drawable.ic_location
            ),
            SettingsItemModel(
                id = SettingsManager.SETTINGS_ABOUT_APP_ID,
                titleResourceId = R.string.settings_item_developer,
                logoResourceId = R.drawable.ic_developer_info
            ),
            SettingsItemModel(
                id = SettingsManager.SETTINGS_EXIT_ID,
                titleResourceId = R.string.settings_item_quit,
                logoResourceId = R.drawable.ic_exit
            )
        )

    override fun getLocales(): List<String> =
        settingsRepository.getLocales()

    override fun updateCurrentLocale(locale: String) {
        settingsEndpoint.setLocationLanguage(infoManager.getLocationId(), SetLocaltionLanguageParamsDto(locale))
        settingsRepository.putCurrentLocale(locale)
    }

}