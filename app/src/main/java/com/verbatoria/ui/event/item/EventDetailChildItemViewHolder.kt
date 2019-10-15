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

interface EventDetailChildItemViewHolder {

    fun setName(name: String)

    fun setAge(age: Int)

    fun showHint()

    interface Callback {

        fun onChildClicked()

    }

}

class EventDetailChildItemViewHolderImpl(
    view: View,
    callback: EventDetailChildItemViewHolder.Callback
) : RecyclerView.ViewHolder(view),
    EventDetailChildItemViewHolder {

    private val nameTextView: TextView = view.findViewById(R.id.name_text_view)
    private val ageTextView: TextView = view.findViewById(R.id.age_text_view)

    private val hintTextView: TextView = view.findViewById(R.id.hint_text_view)

    init {
        view.setOnClickListener {
            callback.onChildClicked()
        }
    }

    override fun setName(name: String) {
        nameTextView.text = name
        hintTextView.hide()
        nameTextView.show()
    }

    override fun setAge(age: Int) {
        ageTextView.text = age.toString()
        hintTextView.hide()
        ageTextView.show()
    }

    override fun showHint() {
        ageTextView.invisible()
        nameTextView.invisible()
        hintTextView.show()
    }

}