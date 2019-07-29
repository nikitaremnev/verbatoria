package com.verbatoria.ui.schedule

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.cleveroad.adaptivetablelayout.LinkedAdaptiveTableAdapter
import com.cleveroad.adaptivetablelayout.ViewHolderImpl
import com.remnev.verbatoria.R
import com.verbatoria.ui.schedule.item.ScheduleColumnHeaderViewHolder
import com.verbatoria.ui.schedule.item.ScheduleItemViewHolder
import com.verbatoria.ui.schedule.item.ScheduleRowHeaderViewHolder
import com.verbatoria.ui.schedule.item.ScheduleTopLeftViewHolder

/**
 * @author n.remnev
 */

private const val ROWS_COUNT = 7
private const val COLUMN_COUNT = 11

class ScheduleAdapter(): LinkedAdaptiveTableAdapter<ViewHolderImpl>() {

    private val headerColumnHeight: Int = 0

    private val columnWidth: Int = 0

    private val rowHeight: Int = 0

    private val headerRowWidth: Int = 0

    //region create view holders

    override fun onCreateItemViewHolder(parent: ViewGroup): ViewHolderImpl =
        ScheduleItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_schedule_cell, parent, false))

    override fun onCreateColumnHeaderViewHolder(parent: ViewGroup): ViewHolderImpl =
        ScheduleColumnHeaderViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_schedule_column_header,
                parent,
                false
            ) as TextView
        )

    override fun onCreateRowHeaderViewHolder(parent: ViewGroup): ViewHolderImpl =
        ScheduleRowHeaderViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_schedule_row_header,
                parent,
                false
            ) as TextView
        )

    override fun onCreateLeftTopHeaderViewHolder(parent: ViewGroup): ViewHolderImpl =
        ScheduleTopLeftViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_schedule_top_left,
                parent,
                false
            )
        )

    //endregion

    //region bind view holders

    override fun onBindLeftTopHeaderViewHolder(viewHolder: ViewHolderImpl) {
        //empty
    }


    override fun onBindHeaderColumnViewHolder(viewHolder: ViewHolderImpl, column: Int) {

    }

    override fun onBindHeaderRowViewHolder(viewHolder: ViewHolderImpl, row: Int) {

    }

    override fun onBindViewHolder(viewHolder: ViewHolderImpl, row: Int, column: Int) {

    }

    override fun getHeaderRowWidth(): Int {

    }

    override fun getRowCount(): Int {

    }


    override fun getRowHeight(row: Int): Int {

    }

    override fun getColumnWidth(column: Int): Int {

    }

    override fun getHeaderColumnHeight(): Int {

    }

    override fun getColumnCount(): Int {

    }

}