package com.verbatoria.presentation.calendar.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.remnev.verbatoriamini.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * View Holder для отображения ячейки события
 *
 * @author nikitaremnev
 */
public class EventViewHolder extends RecyclerView.ViewHolder {

    private View mRootView;

    @BindView(R.id.report_status_image_view)
    public ImageView mStatusImageView;

    @BindView(R.id.instant_report_image_view)
    public ImageView mInstantReportImageView;

    @BindView(R.id.report_status_text_view)
    public TextView mStatusTextView;

    @BindView(R.id.age_text_view)
    public TextView mAgeTextView;

    @BindView(R.id.child_name_text_view)
    public TextView mChildNameTextView;

    @BindView(R.id.report_id_text_view)
    public TextView mReportIdTextView;

    @BindView(R.id.time_period_text_view)
    public TextView mTimePeriodTextView;

    EventViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mRootView = itemView;
    }

    void setDoneBackground() {
        mRootView.setBackgroundResource(R.color.main_shadowed);
    }

    void setDeletedBackground() {
        mRootView.setBackgroundResource(R.color.main_shadowed);
    }


    void setChildName(String childName) {
        mChildNameTextView.setText(childName);
    }

    void setAge(String age) {
        mAgeTextView.setText(age);
    }

    void setReportId(String reportId) {
        mReportIdTextView.setText(reportId);
    }

    void showNewReport(Context context) {
        mStatusImageView.setImageResource(R.drawable.ic_report_new_large);
        mStatusTextView.setText(context.getString(R.string.calendar_report_new));
    }

    void showReadyReport(Context context) {
        mStatusImageView.setImageResource(R.drawable.ic_report_ready_large);
        mStatusTextView.setText(context.getString(R.string.calendar_report_ready));
    }

    void showUploadedReport(Context context) {
        mStatusImageView.setImageResource(R.drawable.ic_report_uploaded_large);
        mStatusTextView.setText(context.getString(R.string.calendar_report_uploaded));
    }

    void showSentReport(Context context) {
        mStatusImageView.setImageResource(R.drawable.ic_report_sent_large);
        mStatusTextView.setText(context.getString(R.string.calendar_report_sent));
    }

    void showInstantReport() {
        mInstantReportImageView.setVisibility(View.VISIBLE);
    }

    void hideInstantReport() {
        mInstantReportImageView.setVisibility(View.GONE);
    }

    void setTimePeriod(String timePeriod) {
        mTimePeriodTextView.setText(timePeriod);
    }

    void setOnClickListener(View.OnClickListener onClickListener) {
        mRootView.setOnClickListener(onClickListener);
    }

}
