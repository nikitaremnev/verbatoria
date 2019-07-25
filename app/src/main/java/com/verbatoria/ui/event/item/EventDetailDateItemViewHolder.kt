package com.verbatoria.ui.event.item

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.remnev.verbatoria.R

/**
 * @author n.remnev
 */

interface EventDetailDateItemViewHolder {

    fun setDate(intervalDate: String)

    interface Callback {

        fun onDateClicked()

    }

}

class EventDetailDateItemViewHolderImpl(
    view: View,
    callback: EventDetailDateItemViewHolder.Callback
) : RecyclerView.ViewHolder(view),
    EventDetailDateItemViewHolder {

    private val dateTextView: TextView = view.findViewById(R.id.date_text_view)

    init {
        view.setOnClickListener {
            callback.onDateClicked()
        }
    }

    override fun setDate(intervalDate: String) {
        dateTextView.text = intervalDate
    }

}