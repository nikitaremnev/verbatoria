package com.verbatoria.ui.event.item

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.remnev.verbatoria.R

/**
 * @author n.remnev
 */

interface EventDetailArchimedesItemViewHolder {

    fun setArchimedesState(archimedesStateResourceId: Int)

    fun showArchimedesNotAllowedForVerbatolog()

}

class EventDetailArchimedesItemViewHolderImpl(
    view: View
) : RecyclerView.ViewHolder(view),
    EventDetailArchimedesItemViewHolder {

    private val context = view.context

    private val archimedesStateTextView: TextView = view.findViewById(R.id.archimedes_state_text_view)

    override fun setArchimedesState(archimedesStateResourceId: Int) {
        archimedesStateTextView.setText(archimedesStateResourceId)
    }

    override fun showArchimedesNotAllowedForVerbatolog() {
        val leftDrawable = ContextCompat.getDrawable(context, R.drawable.ic_archimedes_not_included)
        archimedesStateTextView.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null, null, null)
    }

}