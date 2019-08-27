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

interface EventDetailIncludeAttentionMemoryItemViewHolder {

    fun setAttentionMemoryIncluded()

    fun showLoading()

    fun hideLoading()

    interface Callback {

        fun onIncludeAttentionMemoryClicked()

    }


}

class EventDetailIncludeAttentionMemoryItemViewHolderImpl(
    view: View,
    callback: EventDetailIncludeAttentionMemoryItemViewHolder.Callback
) : RecyclerView.ViewHolder(view),
    EventDetailIncludeAttentionMemoryItemViewHolder {

    private val includeAttentionMemoryTextView: TextView = view.findViewById(R.id.include_attention_memory_text_view)
    private val alreadySentTextView: TextView = view.findViewById(R.id.already_sent_text_view)
    private val progressBar: ProgressBar = view.findViewById(R.id.loading_progress_bar)

    init {
        view.setOnClickListener {
            callback.onIncludeAttentionMemoryClicked()
        }
    }

    override fun setAttentionMemoryIncluded() {
        alreadySentTextView.setText(R.string.event_detail_include_attention_memory_complete)
        alreadySentTextView.show()
    }

    override fun showLoading() {
        includeAttentionMemoryTextView.invisible()
        progressBar.show()
    }

    override fun hideLoading() {
        progressBar.hide()
        includeAttentionMemoryTextView.show()
    }

}