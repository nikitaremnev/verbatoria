package com.verbatoria.ui.schedule.view.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import com.cleveroad.adaptivetablelayout.ViewHolderImpl;

/**
 * View Holder для отображения ячейки события
 *
 * @author nikitaremnev
 */
public class ColumnHeaderViewHolder extends ViewHolderImpl {

    private TextView mRootView;

    public ColumnHeaderViewHolder(View itemView) {
        super(itemView);
        mRootView = (TextView) itemView;
    }

    public void setHeaderTitle(String title) {
        mRootView.setText(title);
    }

}
