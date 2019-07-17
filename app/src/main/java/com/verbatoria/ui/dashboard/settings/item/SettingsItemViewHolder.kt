package com.verbatoria.ui.dashboard.settings.item

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.remnev.verbatoria.R

/**
 * @author n.remnev
 */

interface SettingsItemViewHolder {

    fun setTitle(titleResourceId: Int)

    fun setLogo(logoResourceId: Int)

    interface Callback {

        fun onSettingsItemClicked(position: Int)

    }

}

class SettingsItemViewHolderImpl(
    view: View,
    callback: SettingsItemViewHolder.Callback
) : RecyclerView.ViewHolder(view),
    SettingsItemViewHolder {

    private val context: Context = view.context

    private val titleTextView: TextView = view.findViewById(R.id.title_text_view)
    private val logoImageView: AppCompatImageView = view.findViewById(R.id.logo_image_view)

    init {
        view.setOnClickListener {
            callback.onSettingsItemClicked(adapterPosition)
        }
    }

    override fun setTitle(titleResourceId: Int) {
        titleTextView.text = context.getString(titleResourceId)
    }

    override fun setLogo(logoResourceId: Int) {
        logoImageView.setImageResource(logoResourceId)
    }

}