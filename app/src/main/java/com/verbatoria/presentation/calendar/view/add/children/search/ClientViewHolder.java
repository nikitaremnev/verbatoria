package com.verbatoria.presentation.calendar.view.add.children.search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.remnev.verbatoria.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * View Holder для отображения ячейки события
 *
 * @author nikitaremnev
 */
public class ClientViewHolder extends RecyclerView.ViewHolder {

    private View mRootView;

    @BindView(R.id.client_name_text_view)
    public TextView mClientNameTextView;

    @BindView(R.id.client_phone_text_view)
    public TextView mClientPhoneTextView;

    @BindView(R.id.client_email_text_view)
    public TextView mClientEmailTextView;

    ClientViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mRootView = itemView;
    }

    void setClientName(String name) {
        mClientNameTextView.setText(name);
    }

    void setClientPhone(String phone) {
        mClientPhoneTextView.setText(phone);
    }

    void setClientEmail(String email) {
        mClientEmailTextView.setText(email);
    }

    void setOnClickListener(View.OnClickListener onClickListener) {
        mRootView.setOnClickListener(onClickListener);
    }

}
