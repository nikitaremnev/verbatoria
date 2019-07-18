package com.verbatoria.ui.dashboard.calendar.item

import com.verbatoria.business.dashboard.calendar.models.EmptyItemModel
import com.verbatoria.ui.common.ViewBinder

/**
 * @author n.remnev
 */

class EmptyItemBinder: ViewBinder<EmptyItemViewHolder, EmptyItemModel>() {

    override fun bind(view: EmptyItemViewHolder, data: EmptyItemModel, position: Int) {
        //empty
    }

}