package com.verbatoria.ui.dashboard.settings.item

import com.verbatoria.business.dashboard.settings.model.SettingsItemModel
import com.verbatoria.ui.common.ViewBinder

/**
 * @author n.remnev
 */

class SettingsItemBinder: ViewBinder<SettingsItemViewHolder, SettingsItemModel>() {

    override fun bind(view: SettingsItemViewHolder, data: SettingsItemModel, position: Int) {
        view.setTitle(data.titleResourceId)
        view.setLogo(data.logoResourceId)
    }

}