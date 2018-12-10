package com.verbatoria.presentation.late_send.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.remnev.verbatoria.R;
import com.verbatoria.business.late_send.models.LateReportModel;
import com.verbatoria.presentation.late_send.view.ILateSendView;

import java.util.List;

/**
 * Адаптер для отображения событий Вербатолога
 *
 * @author nikitaremnev
 */
public class LateReportAdapter extends RecyclerView.Adapter<LateReportViewHolder> {

    private final List<LateReportModel> mLateReportModels;

    private ILateSendView.Callback mCallback;

    public LateReportAdapter(@NonNull List<LateReportModel> reportModels, ILateSendView.Callback callback) {
        mLateReportModels = reportModels;
        mCallback = callback;
    }

    @Override
    public LateReportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_late_send_report, parent, false);
        return new LateReportViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mLateReportModels.size();
    }

    @Override
    public void onBindViewHolder(LateReportViewHolder holder, int position) {
        LateReportModel lateReportModel = mLateReportModels.get(position);
        LateReportHolderBinder.bind(lateReportModel, holder);
        holder.setOnClickListener(view -> mCallback.onItemClicked(position));
    }

}
