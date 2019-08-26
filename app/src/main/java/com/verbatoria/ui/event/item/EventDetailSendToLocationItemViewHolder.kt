package com.verbatoria.ui.event.item

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.remnev.verbatoria.R
import com.verbatoria.infrastructure.extensions.hide
import com.verbatoria.infrastructure.extensions.invisible
import com.verbatoria.infrastructure.extensions.show

/**
 * @author n.remnev
 */

interface EventDetailSendToLocationItemViewHolder {

    fun setAlreadySent()

    fun showLoading()

    fun hideLoading()

    interface Callback {

        fun onSendToLocationClicked()

    }

}

class EventDetailSendToLocationItemViewHolderImpl(
    view: View,
    callback: EventDetailSendToLocationItemViewHolder.Callback
) : RecyclerView.ViewHolder(view),
    EventDetailSendToLocationItemViewHolder {

    private val sendToLocationTextView: TextView = view.findViewById(R.id.send_to_location_text_view)
    private val alreadySentTextView: TextView = view.findViewById(R.id.already_sent_text_view)
    private val progressBar: ProgressBar = view.findViewById(R.id.loading_progress_bar)

    init {
        view.setOnClickListener {
            callback.onSendToLocationClicked()
        }
    }

    override fun setAlreadySent() {
        alreadySentTextView.setText(R.string.event_confirm_send_report_to_location_success)
    }

    override fun showLoading() {
        sendToLocationTextView.invisible()
        alreadySentTextView.invisible()
        progressBar.show()
    }

    override fun hideLoading() {
        progressBar.hide()
        sendToLocationTextView.show()
        alreadySentTextView.show()
    }

}