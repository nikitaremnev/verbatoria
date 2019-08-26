package com.verbatoria.ui.event.item

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.remnev.verbatoria.R

/**
 * @author n.remnev
 */

interface EventDetailReportItemViewHolder {

    fun setReportId(reportId: String)

    fun setReportStatus(reportStatusLogoResourceId: Int, reportStatusStringResourceId: Int)

    interface Callback {

        fun onReportClicked()

    }

}

class EventDetailReportItemViewHolderImpl(
    view: View,
    callback: EventDetailReportItemViewHolder.Callback
) : RecyclerView.ViewHolder(view),
    EventDetailReportItemViewHolder {

    private val context = view.context

    private val reportIdTextView: TextView = view.findViewById(R.id.report_id_text_view)
    private val reportStatusTextView: TextView = view.findViewById(R.id.report_status_text_view)

    init {
        view.setOnClickListener {
            callback.onReportClicked()
        }
    }

    override fun setReportId(reportId: String) {
        reportIdTextView.text = reportId
    }

    override fun setReportStatus(reportStatusLogoResourceId: Int, reportStatusStringResourceId: Int) {
        val leftDrawable = ContextCompat.getDrawable(context, reportStatusLogoResourceId)
        reportStatusTextView.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null, null, null)
        reportStatusTextView.setText(reportStatusStringResourceId)
    }

}