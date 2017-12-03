package com.verbatoria.presentation.schedule.view.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.cleveroad.adaptivetablelayout.LinkedAdaptiveTableAdapter;
import com.cleveroad.adaptivetablelayout.ViewHolderImpl;
import com.remnev.verbatoriamini.R;
import com.verbatoria.business.schedule.datasource.IScheduleDataSource;
import com.verbatoria.business.schedule.models.ScheduleItemModel;
import com.verbatoria.presentation.schedule.view.adapter.viewholder.ColumnHeaderViewHolder;
import com.verbatoria.presentation.schedule.view.adapter.viewholder.RowHeaderViewHolder;
import com.verbatoria.presentation.schedule.view.adapter.viewholder.ScheduleItemViewHolder;
import com.verbatoria.presentation.schedule.view.adapter.viewholder.ScheduleTopLeftViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author nikitaremnev
 */

public class ScheduleAdapter extends LinkedAdaptiveTableAdapter<ViewHolderImpl> {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM", Locale.ROOT);
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm", Locale.ROOT);

    private static String[] ROWS_NAMES;

    private final LayoutInflater mLayoutInflater;

    private final int mHeaderColumnHeight;
    private final int mColumnWidth;
    private final int mRowHeight;
    private final int mHeaderRowWidth;

    private IScheduleDataSource<String, Date, Date, ScheduleItemModel> mDataSource;

    public ScheduleAdapter(Context context, IScheduleDataSource<String, Date, Date, ScheduleItemModel> dataSource) {
        mLayoutInflater = LayoutInflater.from(context);
        Resources resources = context.getResources();
        mHeaderColumnHeight = resources.getDimensionPixelSize(R.dimen.column_header_height);
        mRowHeight = resources.getDimensionPixelSize(R.dimen.row_height);
        mHeaderRowWidth = resources.getDimensionPixelSize(R.dimen.row_header_width);
        mColumnWidth = resources.getDimensionPixelSize(R.dimen.column_width);
        ROWS_NAMES = context.getResources().getStringArray(R.array.rows_names);
        mDataSource = dataSource;
    }

    @Override
    public int getRowCount() {
        return mDataSource.getRowsCount();
    }

    @Override
    public int getColumnCount() {
        return mDataSource.getColumnsCount();
    }

    @NonNull
    @Override
    public ViewHolderImpl onCreateItemViewHolder(@NonNull ViewGroup parent) {
        return new ScheduleItemViewHolder(mLayoutInflater.inflate(R.layout.item_schedule_cell, parent, false));
    }

    @NonNull
    @Override
    public ViewHolderImpl onCreateColumnHeaderViewHolder(@NonNull ViewGroup parent) {
        return new ColumnHeaderViewHolder(mLayoutInflater.inflate(R.layout.item_column_header, parent, false));
    }

    @NonNull
    @Override
    public ViewHolderImpl onCreateRowHeaderViewHolder(@NonNull ViewGroup parent) {
        return new RowHeaderViewHolder(mLayoutInflater.inflate(R.layout.item_row_header, parent, false));
    }

    @NonNull
    @Override
    public ViewHolderImpl onCreateLeftTopHeaderViewHolder(@NonNull ViewGroup parent) {
        return new ScheduleTopLeftViewHolder(mLayoutInflater.inflate(R.layout.item_schedule_top_left, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderImpl viewHolder, int row, int column) {
        ScheduleItemModel scheduleItemModel = mDataSource.getItemData(row, column);
        ((ScheduleItemViewHolder) viewHolder).setChecked(scheduleItemModel.isSelected());
    }

    @Override
    public void onBindHeaderColumnViewHolder(@NonNull ViewHolderImpl viewHolder, int column) {
        ((ColumnHeaderViewHolder) viewHolder).setHeaderTitle(TIME_FORMAT.format(mDataSource.getColumnHeaderData(column)));
    }

    @Override
    public void onBindHeaderRowViewHolder(@NonNull ViewHolderImpl viewHolder, int row) {
        ((RowHeaderViewHolder) viewHolder).setHeaderTitle(
                String.format(ROWS_NAMES[row - 1], DATE_FORMAT.format(mDataSource.getRowHeaderData(row))));
    }

    @Override
    public void onBindLeftTopHeaderViewHolder(@NonNull ViewHolderImpl viewHolder) {

    }

    @Override
    public int getColumnWidth(int column) {
        return mColumnWidth;
    }

    @Override
    public int getHeaderColumnHeight() {
        return mHeaderColumnHeight;
    }

    @Override
    public int getRowHeight(int row) {
        return mRowHeight;
    }

    @Override
    public int getHeaderRowWidth() {
        return mHeaderRowWidth;
    }

    public void notifyItemChanged(int row, int column, boolean state) {
        mDataSource.getItemData(row, column).setSelected(state);
        notifyItemChanged(row, column);
    }
}
