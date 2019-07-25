package com.verbatoria.ui.event.item

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import com.remnev.verbatoria.R

/**
 * @author n.remnev
 */

interface EventDetailSubmitItemViewHolder {

    fun setCreateNewEventTitle()

    fun setEditEventTitle()

    fun setStartEventTitle()

    interface Callback {

        fun onSubmitButtonClicked()

    }

}

class EventDetailSubmitItemViewHolderImpl(
    view: View,
    callback: EventDetailSubmitItemViewHolder.Callback
) : RecyclerView.ViewHolder(view),
    EventDetailSubmitItemViewHolder {

    private val context: Context = view.context

    private val submitButton: Button = view.findViewById(R.id.submit_button)

    init {
        submitButton.setOnClickListener {
            callback.onSubmitButtonClicked()
        }
    }

    override fun setCreateNewEventTitle() {
        submitButton.setText(context.getString(R.string.event_detail_create_new_event))
    }

    override fun setEditEventTitle() {

    }

    override fun setStartEventTitle() {

    }

}