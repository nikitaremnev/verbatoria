package com.verbatoria.presentation.schedule.view.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import com.cleveroad.adaptivetablelayout.ViewHolderImpl;

/**
 * View Holder для отображения ячейки события
 *
 * @author nikitaremnev
 */
public class RowHeaderViewHolder extends ViewHolderImpl {

    private TextView mRootView;

    public RowHeaderViewHolder(View itemView) {
        super(itemView);
        mRootView = (TextView) itemView;
    }

    public void setHeaderTitle(String title) {
        mRootView.setText(title);
    }

}
