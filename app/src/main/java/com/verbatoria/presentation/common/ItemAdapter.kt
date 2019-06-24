package com.verbatoria.presentation.common

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * @author n.remnev
 */

class ItemAdapter<in VH, in D>(
    val isType: (Any) -> Boolean,
    val createViewHolder: (ViewGroup) -> RecyclerView.ViewHolder,
    val viewBinder: ViewBinder<VH, D>
)