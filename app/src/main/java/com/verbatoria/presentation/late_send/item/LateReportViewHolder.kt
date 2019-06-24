package com.verbatoria.presentation.late_send.item

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.remnev.verbatoria.R

/**
 * @author n.remnev
 */

interface LateReportViewHolder {

    fun setTitle(title: String)

    fun setSubtitle(subtitle: String)

    interface Callback {

        fun onLateReportClicked(position: Int)

    }

}

class LateReportViewHolderImpl(
    rootView: View,
    callback: LateReportViewHolder.Callback
) : RecyclerView.ViewHolder(rootView), LateReportViewHolder {

    private val titleTextView: TextView = rootView.findViewById(R.id.field_title)
    private val subtitleTextView: TextView = rootView.findViewById(R.id.field_title)
    private val logoImageView: ImageView = rootView.findViewById(R.id.field_image_view)

    init {
        logoImageView.setImageResource(R.drawable.ic_late_report)
        rootView.setOnClickListener {
            callback.onLateReportClicked(adapterPosition)
        }
    }

    //region LateReportViewHolder

    override fun setTitle(title: String) {
        titleTextView.text = title
    }

    override fun setSubtitle(subtitle: String) {
        subtitleTextView.text = subtitle
    }

    //endregion

}