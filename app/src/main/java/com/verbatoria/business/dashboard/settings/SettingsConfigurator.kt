package com.verbatoria.business.dashboard.settings

import com.remnev.verbatoria.R
import com.verbatoria.business.dashboard.settings.model.SettingsItemModel

/**
 * @author n.remnev
 */

interface SettingsConfigurator {

    fun getSettingsItemModels(): List<SettingsItemModel>

}

class SettingsConfiguratorImpl : SettingsConfigurator {

    override fun getSettingsItemModels(): List<SettingsItemModel> =
        listOf(
            SettingsItemModel(
                titleResourceId = R.string.late_send_title,
                logoResourceId = R.drawable.ic_report
            ),
            SettingsItemModel(
                titleResourceId = R.string.settings_item_developer,
                logoResourceId = R.drawable.ic_developer_info
            ),
            SettingsItemModel(
                titleResourceId = R.string.settings_item_quit,
                logoResourceId = R.drawable.ic_exit
            ),
            SettingsItemModel(
                titleResourceId = R.string.schedule_title,
                logoResourceId = R.drawable.ic_schedule
            ),
            SettingsItemModel(
                titleResourceId = R.string.settings_item_clear,
                logoResourceId = R.drawable.ic_clear
            ),
            SettingsItemModel(
                titleResourceId = R.string.settings_item_locale,
                logoResourceId = R.drawable.ic_location
            )
        )

}