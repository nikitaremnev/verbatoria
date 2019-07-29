package com.verbatoria.ui.schedule.item

import android.widget.TextView
import com.cleveroad.adaptivetablelayout.ViewHolderImpl

/**
 * @author n.remnev
 */

class ScheduleRowHeaderViewHolder(
    private val headerTitleTextView: TextView
) : ViewHolderImpl(headerTitleTextView) {

    fun setHeaderTitle(title: String) {
        headerTitleTextView.text = title
    }

}