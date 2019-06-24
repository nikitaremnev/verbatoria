package com.verbatoria.presentation.calendar.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.remnev.verbatoria.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.presentation.calendar.view.CalendarFragment;
import com.verbatoria.presentation.calendar.view.detail.EventDetailActivity;

import java.lang.ref.SoftReference;
import java.util.List;

import javax.inject.Inject;

/**
 * Адаптер для отображения событий Вербатолога
 *
 * @author nikitaremnev
 */
public class EventsAdapter extends RecyclerView.Adapter<EventViewHolder> {

    private final List<EventModel> mEventsList;

    @Inject
    public Context mContext;

    private SoftReference<Activity> mActivitySoftReference;

    public EventsAdapter(@NonNull List<EventModel> eventsList, Activity activity) {
        VerbatoriaApplication.getInjector().inject(this);
        mEventsList = eventsList;
        mActivitySoftReference = new SoftReference<>(activity);
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_new, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mEventsList.size();
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        EventModel event = mEventsList.get(position);
        EventViewHolderBinder.bind(event, holder, mContext);
        holder.setOnClickListener(new EventOnClickListener(event));
    }

    private class EventOnClickListener implements View.OnClickListener {

        private SoftReference<EventModel> mEventModelSoftReference;

        EventOnClickListener(EventModel eventModel) {
            mEventModelSoftReference = new SoftReference<>(eventModel);
        }

        @Override
        public void onClick(View v) {
            Activity activity = mActivitySoftReference.get();
            EventModel eventModel = mEventModelSoftReference.get();
            if (activity != null && eventModel != null) {
                activity.startActivityForResult(EventDetailActivity.newInstance(activity, eventModel), CalendarFragment.ACTIVITY_EVENT_CODE);
            }
        }
    }


}
