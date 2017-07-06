package com.verbatoria.presentation.dashboard.view.main.events;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.remnev.verbatoriamini.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.dashboard.models.EventModel;

import java.util.List;

import javax.inject.Inject;

/**
 * Адаптер для отображения событий Вербатолога
 *
 * @author nikitaremnev
 */
public class VerbatologEventsAdapter extends RecyclerView.Adapter<VerbatologEventViewHolder> {

    private final List<EventModel> mEventsList;

    @Inject
    public Context mContext;

    VerbatologEventsAdapter(@NonNull List<EventModel> eventsList) {
        VerbatoriaApplication.getApplicationComponent().inject(this);
        mEventsList = eventsList;
    }

    @Override
    public VerbatologEventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_verbatolog_event, parent, false);
        return new VerbatologEventViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mEventsList.size();
    }

    @Override
    public void onBindViewHolder(VerbatologEventViewHolder holder, int position) {
        EventModel event = mEventsList.get(position);
        holder.setChildName(event.getChild().getName());
        holder.setAge(event.getFullAge(mContext));
        holder.setTimePeriod(event.getEventTime());
    }

}
