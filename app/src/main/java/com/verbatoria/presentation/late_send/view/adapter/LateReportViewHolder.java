package com.verbatoria.presentation.late_send.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.remnev.verbatoriamini.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * View Holder для отображения ячейки позднего отчета
 *
 * @author nikitaremnev
 */
public class LateReportViewHolder extends RecyclerView.ViewHolder {

    private View mRootView;

    @BindView(R.id.field_image_view)
    public ImageView mFieldImageView;

    @BindView(R.id.field_title)
    public TextView mTitle;

    @BindView(R.id.field_subtitle)
    public TextView mSubtitle;

    LateReportViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mRootView = itemView;
    }

    void setTitle(String title) {
        mTitle.setText(title);
    }

    void setSubtitle(String subtitle) {
        mSubtitle.setText(subtitle);
    }

    void showImage(Context context) {
        mFieldImageView.setImageResource(R.drawable.ic_report_ready_large);
    }

    void setOnClickListener(View.OnClickListener onClickListener) {
        mRootView.setOnClickListener(onClickListener);
    }

}
