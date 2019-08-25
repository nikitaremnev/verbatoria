package com.verbatoria.ui.event.item

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.remnev.verbatoria.R
import com.verbatoria.infrastructure.extensions.hide
import com.verbatoria.infrastructure.extensions.invisible
import com.verbatoria.infrastructure.extensions.show

/**
 * @author n.remnev
 */

interface EventDetailTimeItemViewHolder {

    fun setTime(time: String)

    fun showHint()

    interface Callback {

        fun onDateClicked()

    }

}

class EventDetailTimeItemViewHolderImpl(
    view: View,
    callback: EventDetailTimeItemViewHolder.Callback
) : RecyclerView.ViewHolder(view),
    EventDetailTimeItemViewHolder {

    private val dateTextView: TextView = view.findViewById(R.id.date_text_view)

    private val hintTextView: TextView = view.findViewById(R.id.hint_text_view)

    init {
        view.setOnClickListener {
            callback.onDateClicked()
        }
    }

    override fun setTime(time: String) {
        dateTextView.text = time
        hintTextView.hide()
        dateTextView.show()
    }

    override fun showHint() {
        dateTextView.invisible()
        hintTextView.show()
    }

}