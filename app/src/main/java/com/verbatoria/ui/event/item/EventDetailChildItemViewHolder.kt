package com.verbatoria.ui.event.item

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.remnev.verbatoria.R

/**
 * @author n.remnev
 */

interface EventDetailChildItemViewHolder {

    fun setName(name: String)

    fun setAge(age: Int)

    interface Callback {



    }

}

class EventDetailChildItemViewHolderImpl(
    view: View,
    callback: EventDetailClientItemViewHolder.Callback
) : RecyclerView.ViewHolder(view),
    EventDetailChildItemViewHolder {

    private val context: Context = view.context

    private val nameTextView: TextView = view.findViewById(R.id.name_text_view)
    private val ageTextView: TextView = view.findViewById(R.id.age_text_view)

    init {
        view.setOnClickListener {

        }
    }

    override fun setName(name: String) {
        nameTextView.text = name
    }

    override fun setAge(age: Int) {
        ageTextView.text = age.toString()
    }

}