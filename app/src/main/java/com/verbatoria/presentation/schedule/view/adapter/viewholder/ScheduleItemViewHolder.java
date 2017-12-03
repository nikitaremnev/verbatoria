package com.verbatoria.presentation.schedule.view.adapter.viewholder;

import android.view.View;

import com.cleveroad.adaptivetablelayout.ViewHolderImpl;
import com.remnev.verbatoriamini.R;

/**
 * View Holder для отображения ячейки события
 *
 * @author nikitaremnev
 */
public class ScheduleItemViewHolder extends ViewHolderImpl {

    private View mRootView;

    public ScheduleItemViewHolder(View itemView) {
        super(itemView);
        mRootView = itemView;
    }

    public void setChecked(boolean checked) {
        if (checked) {
            mRootView.setBackgroundResource(R.color.main_dark);
        } else {
            mRootView.setBackgroundResource(R.color.background);
        }
    }

}
