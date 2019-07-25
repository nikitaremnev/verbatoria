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

interface EventDetailHobbyItemViewHolder {


    interface Callback {



    }

}

class EventDetailHobbyItemViewHolderImpl(
    view: View,
    callback: EventDetailClientItemViewHolder.Callback
) : RecyclerView.ViewHolder(view),
    EventDetailHobbyItemViewHolder {

    private val context: Context = view.context

    private val titleTextView: TextView = view.findViewById(R.id.title_text_view)
    private val logoImageView: AppCompatImageView = view.findViewById(R.id.logo_image_view)

    init {
        view.setOnClickListener {

        }
    }

}