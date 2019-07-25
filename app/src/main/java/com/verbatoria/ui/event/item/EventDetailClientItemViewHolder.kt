package com.verbatoria.ui.event.item

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.remnev.verbatoria.R

/**
 * @author n.remnev
 */

interface EventDetailClientItemViewHolder {

    fun setName(name: String)

    fun setPhone(phone: String)

    interface Callback {



    }

}

class EventDetailClientItemViewHolderImpl(
    view: View,
    callback: EventDetailClientItemViewHolder.Callback
) : RecyclerView.ViewHolder(view),
    EventDetailClientItemViewHolder {

    private val context: Context = view.context

    private val nameTextView: TextView = view.findViewById(R.id.name_text_view)
    private val phoneTextView: TextView = view.findViewById(R.id.phone_text_view)

    init {
        view.setOnClickListener {

        }
    }

    override fun setName(name: String) {
        nameTextView.text = name
    }

    override fun setPhone(phone: String) {
        phoneTextView.text = phone
    }

}