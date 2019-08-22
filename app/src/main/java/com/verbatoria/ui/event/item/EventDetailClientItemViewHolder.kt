package com.verbatoria.ui.event.item

import android.content.Context
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

interface EventDetailClientItemViewHolder {

    fun setName(name: String)

    fun setPhone(phone: String)

    fun showHint()

    interface Callback {

        fun onClientClicked()

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

    private val hintTextView: TextView = view.findViewById(R.id.hint_text_view)

    init {
        view.setOnClickListener {
            callback.onClientClicked()
        }
    }

    override fun setName(name: String) {
        nameTextView.text = name
        hintTextView.hide()
        nameTextView.show()
    }

    override fun setPhone(phone: String) {
        phoneTextView.text = phone
        hintTextView.hide()
        phoneTextView.show()
    }

    override fun showHint() {
        phoneTextView.invisible()
        nameTextView.invisible()
        hintTextView.show()
    }

}