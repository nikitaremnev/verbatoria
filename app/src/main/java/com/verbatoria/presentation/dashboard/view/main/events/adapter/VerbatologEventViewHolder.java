package com.verbatoria.presentation.dashboard.view.main.events.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.remnev.verbatoriamini.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * View Holder для отображения ячейки события
 *
 * @author nikitaremnev
 */
public class VerbatologEventViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.child_name_text_view)
    public TextView mChildNameTextView;

    @BindView(R.id.age_text_view)
    public TextView mAgeTextView;

    @BindView(R.id.time_period_text_view)
    public TextView mTimePeriodTextView;

    public VerbatologEventViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setChildName(String childName) {
        mChildNameTextView.setText(childName);
    }

    public void setAge(String age) {
        mAgeTextView.setText(age);
    }

    public void setTimePeriod(String timePeriod) {
        mTimePeriodTextView.setText(timePeriod);
    }

}
