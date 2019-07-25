package com.verbatoria.ui.event.item

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.remnev.verbatoria.R

/**
 * @author n.remnev
 */

interface EventDetailHeaderItemViewHolder {

    fun setHeader(headerStringResource: Int)

}

class EventDetailHeaderItemViewHolderImpl(
    view: View
) : RecyclerView.ViewHolder(view),
    EventDetailHeaderItemViewHolder {

    private val context: Context = view.context

    private val headerTextView: TextView = view.findViewById(R.id.header_text_view)

    override fun setHeader(headerStringResource: Int) {
        headerTextView.text = context.getString(headerStringResource)
    }

}