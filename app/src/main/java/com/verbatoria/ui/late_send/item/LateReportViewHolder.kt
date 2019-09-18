package com.verbatoria.ui.late_send.item

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.remnev.verbatoria.R
import com.verbatoria.infrastructure.extensions.capitalizeFirstLetter

/**
 * @author n.remnev
 */

interface LateReportViewHolder {

    fun setChildNameAndAge(name: String, age: Int)

    fun setState(stateResourceId: Int)

    fun setPeriod(period: String)

    fun setReportId(reportId: String)

    interface Callback {

        fun onLateReportClicked(position: Int)

        fun onLateReportDeleteClicked(position: Int)

    }

}

class LateReportViewHolderImpl(
    rootView: View,
    callback: LateReportViewHolder.Callback
) : RecyclerView.ViewHolder(rootView), LateReportViewHolder {

    private val context: Context = rootView.context
    private val childTextView: TextView = rootView.findViewById(R.id.child_text_view)
    private val stateTextView: TextView = rootView.findViewById(R.id.state_text_view)
    private val timePeriodTextView: TextView = rootView.findViewById(R.id.time_period_text_view)
    private val reportIdTextView: TextView = rootView.findViewById(R.id.report_id_text_view)
    private val deleteImageView: ImageView = rootView.findViewById(R.id.delete_image_view)

    init {
        rootView.setOnClickListener {
            callback.onLateReportClicked(adapterPosition)
        }
        deleteImageView.setOnClickListener {
            callback.onLateReportDeleteClicked(adapterPosition)
        }
    }

    //region LateReportViewHolder

    override fun setChildNameAndAge(name: String, age: Int) {
        childTextView.text = context.getString(
            R.string.calendar_event_age_format,
            name.capitalizeFirstLetter(),
            age.toString()
        )
    }

    override fun setState(stateResourceId: Int) {
        stateTextView.text = context.getString(stateResourceId)
    }

    override fun setPeriod(period: String) {
        timePeriodTextView.text = period
    }

    override fun setReportId(reportId: String) {
        reportIdTextView.text = reportId
    }

    //endregion

}