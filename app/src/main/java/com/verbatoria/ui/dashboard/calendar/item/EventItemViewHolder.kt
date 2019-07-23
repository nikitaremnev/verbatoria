package com.verbatoria.ui.dashboard.calendar.item

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.remnev.verbatoria.R
import com.verbatoria.infrastructure.extensions.capitalizeFirstLetter

/**
 * @author n.remnev
 */

interface EventItemViewHolder {

    fun setStatusLogoFromResourceId(statusLogoResourceId: Int)

    fun setStatusFromResourceId(statusTextResource: Int)

    fun setClientNameAndAge(name: String, age: Int)

    fun setReportId(reportId: String)

    fun setPeriod(period: String)

    interface Callback {

        fun onEventItemClicked(position: Int)

    }

}

class EventItemViewHolderImpl(
    view: View,
    callback: EventItemViewHolder.Callback
) : RecyclerView.ViewHolder(view), EventItemViewHolder {

    private val context = view.context
    private val statusImageView: ImageView = view.findViewById(R.id.status_image_view)
    private val statusTextView: TextView = view.findViewById(R.id.status_text_view)
    private val clientTextView: TextView = view.findViewById(R.id.client_text_view)
    private val reportIdTextView: TextView = view.findViewById(R.id.report_id_text_view)
    private val periodTextView: TextView = view.findViewById(R.id.time_period_text_view)

    init {
        view.setOnClickListener {
            callback.onEventItemClicked(adapterPosition)
        }
    }

    override fun setStatusLogoFromResourceId(statusLogoResourceId: Int) {
        statusImageView.setImageResource(statusLogoResourceId)
    }

    override fun setStatusFromResourceId(statusTextResource: Int) {
        statusTextView.text = context.getString(statusTextResource)
    }

    override fun setClientNameAndAge(name: String, age: Int) {
        clientTextView.text = context.getString(
            R.string.calendar_event_age_format,
            name.capitalizeFirstLetter(),
            age.toString()
        )
    }

    override fun setReportId(reportId: String) {
        reportIdTextView.text = reportId
    }

    override fun setPeriod(period: String) {
        periodTextView.text = period
    }

}