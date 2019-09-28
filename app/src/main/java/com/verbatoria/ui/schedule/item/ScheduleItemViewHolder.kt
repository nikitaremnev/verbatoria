package com.verbatoria.ui.schedule.item

import android.view.View
import com.remnev.verbatoria.R
import com.verbatoria.ui.common.adaptivetablelayout.ViewHolderImpl

/**
 * @author n.remnev
 */

class ScheduleItemViewHolder(
    private val backgroundView: View
) : ViewHolderImpl(backgroundView) {

    fun setChecked(isChecked: Boolean) {
        if (isChecked) {
            backgroundView.setBackgroundResource(R.color.main)
        } else {
            backgroundView.setBackgroundResource(R.color.background)
        }
    }

}