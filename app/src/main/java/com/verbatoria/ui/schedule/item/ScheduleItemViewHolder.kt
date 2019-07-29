package com.verbatoria.ui.schedule.item

import android.view.View
import com.cleveroad.adaptivetablelayout.ViewHolderImpl
import com.remnev.verbatoria.R

/**
 * @author n.remnev
 */

class ScheduleItemViewHolder(
    private val backgroundView: View
) : ViewHolderImpl(backgroundView) {

    fun setChecked(isChecked: Boolean) {
        if (isChecked) {
            backgroundView.setBackgroundResource(R.color.main_dark)
        } else {
            backgroundView.setBackgroundResource(R.color.background)
        }
    }

}