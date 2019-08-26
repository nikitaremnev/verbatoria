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

interface EventDetailHobbyItemViewHolder {

    fun setHobbyIncludedShortStatus(statusStringResourceId: Int)

    fun setHobbyIncludedFullStatus(statusStringResourceId: Int)

    fun showLoading()

    fun hideLoading()

    interface Callback {

        fun onHobbyClicked()

    }

}

class EventDetailHobbyItemViewHolderImpl(
    view: View,
    callback: EventDetailHobbyItemViewHolder.Callback
) : RecyclerView.ViewHolder(view),
    EventDetailHobbyItemViewHolder {

    private val hobbyIncludedShortStatusTextView: TextView = view.findViewById(R.id.hobby_included_short_status_text_view)
    private val hobbyIncludedFullStatusTextView: TextView = view.findViewById(R.id.hobby_included_full_status_text_view)
    private val progressBar: ProgressBar = view.findViewById(R.id.loading_progress_bar)

    init {
        view.setOnClickListener {
            callback.onHobbyClicked()
        }
    }

    override fun setHobbyIncludedShortStatus(statusStringResourceId: Int) {
        hobbyIncludedShortStatusTextView.setText(statusStringResourceId)
    }

    override fun setHobbyIncludedFullStatus(statusStringResourceId: Int) {
        hobbyIncludedFullStatusTextView.setText(statusStringResourceId)
    }

    override fun showLoading() {
        hobbyIncludedShortStatusTextView.invisible()
        hobbyIncludedFullStatusTextView.invisible()
        progressBar.show()
    }

    override fun hideLoading() {
        hobbyIncludedShortStatusTextView.show()
        hobbyIncludedFullStatusTextView.show()
        progressBar.hide()
    }

}