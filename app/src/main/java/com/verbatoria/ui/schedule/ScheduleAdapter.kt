package com.verbatoria.ui.schedule

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.remnev.verbatoria.R
import com.verbatoria.domain.schedule.ScheduleDataSource
import com.verbatoria.infrastructure.extensions.formatToShortDate
import com.verbatoria.infrastructure.extensions.formatToTime
import com.verbatoria.ui.common.adaptivetablelayout.LinkedAdaptiveTableAdapter
import com.verbatoria.ui.common.adaptivetablelayout.ViewHolderImpl
import com.verbatoria.ui.schedule.item.ScheduleColumnHeaderViewHolder
import com.verbatoria.ui.schedule.item.ScheduleItemViewHolder
import com.verbatoria.ui.schedule.item.ScheduleRowHeaderViewHolder
import com.verbatoria.ui.schedule.item.ScheduleTopLeftViewHolder

/**
 * @author n.remnev
 */

private const val ROWS_COUNT = 7
private const val COLUMN_COUNT = 11

class ScheduleAdapter(
    context: Context,
    width: Int,
    height: Int,
    private val scheduleDataSource: ScheduleDataSource
): LinkedAdaptiveTableAdapter<ViewHolderImpl>() {

    private val headerColumnHeight: Int = context.resources.getDimensionPixelSize(R.dimen.column_header_height)

    private val rowHeight: Int = (height - headerColumnHeight) / ROWS_COUNT

    private val headerRowWidth: Int = context.resources.getDimensionPixelSize(R.dimen.row_header_width)

    private val columnWidth: Int = (width - headerRowWidth) / COLUMN_COUNT

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    private val rowsNames: Array<String> = context.resources.getStringArray(R.array.rows_names)

    //region create view holders

    override fun onCreateItemViewHolder(parent: ViewGroup): ViewHolderImpl =
        ScheduleItemViewHolder(layoutInflater.inflate(R.layout.item_schedule_cell, parent, false))

    override fun onCreateColumnHeaderViewHolder(parent: ViewGroup): ViewHolderImpl =
        ScheduleColumnHeaderViewHolder(layoutInflater.inflate(R.layout.item_schedule_column_header, parent, false) as TextView)

    override fun onCreateRowHeaderViewHolder(parent: ViewGroup): ViewHolderImpl =
        ScheduleRowHeaderViewHolder(layoutInflater.inflate(R.layout.item_schedule_row_header, parent, false) as TextView)

    override fun onCreateLeftTopHeaderViewHolder(parent: ViewGroup): ViewHolderImpl =
        ScheduleTopLeftViewHolder(layoutInflater.inflate(R.layout.item_schedule_top_left, parent, false))

    //endregion

    //region bind view holders

    override fun onBindLeftTopHeaderViewHolder(viewHolder: ViewHolderImpl) {
        //empty
    }


    override fun onBindHeaderColumnViewHolder(viewHolder: ViewHolderImpl, column: Int) {
        (viewHolder as ScheduleColumnHeaderViewHolder).setHeaderTitle(
            scheduleDataSource.getColumnHeaderDate(column).formatToTime()
        )
    }

    override fun onBindHeaderRowViewHolder(viewHolder: ViewHolderImpl, row: Int) {
        (viewHolder as ScheduleRowHeaderViewHolder).setHeaderTitle(
            String.format(rowsNames[row - 1], scheduleDataSource.getRowHeaderDate(row).formatToShortDate())
        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolderImpl, row: Int, column: Int) {
        (viewHolder as ScheduleItemViewHolder).setChecked(scheduleDataSource.getScheduleCellItem(row, column)?.isSelected ?: false)
    }

    override fun getHeaderRowWidth(): Int = headerRowWidth

    override fun getRowHeight(row: Int): Int  = rowHeight

    override fun getColumnWidth(column: Int): Int = columnWidth

    override fun getHeaderColumnHeight(): Int = headerColumnHeight

    override fun getRowCount(): Int = ScheduleDataSource.ROWS_COUNT

    override fun getColumnCount(): Int = ScheduleDataSource.COLUMN_COUNT

}