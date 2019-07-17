package com.verbatoria.business.dashboard.settings

import com.remnev.verbatoria.R
import com.verbatoria.business.dashboard.settings.model.SettingsItemModel

/**
 * @author n.remnev
 */

interface SettingsConfigurator {

    companion object {

        const val SETTINGS_SCHEDULE_ID = 0

        const val SETTINGS_LATE_SEND_ID = 1

        const val SETTINGS_CLEAR_DATABASE_ID = 2

        const val SETTINGS_APP_LANGUAGE_ID = 3

        const val SETTINGS_ABOUT_APP_ID = 4

        const val SETTINGS_EXIT_ID = 5

    }

    fun getSettingsItemModels(): List<SettingsItemModel>

}

class SettingsConfiguratorImpl : SettingsConfigurator {

    override fun getSettingsItemModels(): List<SettingsItemModel> =
        listOf(
            SettingsItemModel(
                id = SettingsConfigurator.SETTINGS_SCHEDULE_ID,
                titleResourceId = R.string.schedule_title,
                logoResourceId = R.drawable.ic_schedule
            ),
            SettingsItemModel(
                id = SettingsConfigurator.SETTINGS_LATE_SEND_ID,
                titleResourceId = R.string.late_send_title,
                logoResourceId = R.drawable.ic_report
            ),
            SettingsItemModel(
                id = SettingsConfigurator.SETTINGS_CLEAR_DATABASE_ID,
                titleResourceId = R.string.settings_item_clear,
                logoResourceId = R.drawable.ic_clear
            ),
            SettingsItemModel(
                id = SettingsConfigurator.SETTINGS_APP_LANGUAGE_ID,
                titleResourceId = R.string.settings_item_locale,
                logoResourceId = R.drawable.ic_location
            ),
            SettingsItemModel(
                id = SettingsConfigurator.SETTINGS_ABOUT_APP_ID,
                titleResourceId = R.string.settings_item_developer,
                logoResourceId = R.drawable.ic_developer_info
            ),
            SettingsItemModel(
                id = SettingsConfigurator.SETTINGS_EXIT_ID,
                titleResourceId = R.string.settings_item_quit,
                logoResourceId = R.drawable.ic_exit
            )
        )

}