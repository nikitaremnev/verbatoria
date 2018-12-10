package com.verbatoria.presentation.calendar.view.add.clients.search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.remnev.verbatoria.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * View Holder для отображения ячейки ребенка
 *
 * @author nikitaremnev
 */
public class ChildViewHolder extends RecyclerView.ViewHolder {

    private View mRootView;

    @BindView(R.id.child_name_text_view)
    public TextView mChildNameTextView;

    @BindView(R.id.child_birthday_text_view)
    public TextView mChildBirthdayTextView;

    ChildViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mRootView = itemView;
    }

    void setChildName(String name) {
        mChildNameTextView.setText(name);
    }

    void setChildBirthday(String bday) {
        mChildBirthdayTextView.setText(bday);
    }

    void setOnClickListener(View.OnClickListener onClickListener) {
        mRootView.setOnClickListener(onClickListener);
    }

}
