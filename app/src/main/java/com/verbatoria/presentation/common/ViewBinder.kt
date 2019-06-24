package com.verbatoria.presentation.common

import android.support.v7.widget.RecyclerView

/**
 * @author n.remnev
 */

abstract class ViewBinder<in VH, in D> {

    fun bindView(view: RecyclerView.ViewHolder, data: Any, position: Int) {
        @Suppress("UNCHECKED_CAST")
        bind(view as VH, data as D, position)
    }

    fun detachViewHolder(view: RecyclerView.ViewHolder) {
        @Suppress("UNCHECKED_CAST")
        unbind(view as VH)
    }

    open fun unbind(view: VH) {

    }

    abstract fun bind(view: VH, data: D, position: Int)

}