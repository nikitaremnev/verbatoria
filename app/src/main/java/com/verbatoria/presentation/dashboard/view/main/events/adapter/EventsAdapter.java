package com.verbatoria.presentation.dashboard.view.main.events.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.remnev.verbatoriamini.R;
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
public class EventsAdapter extends RecyclerView.Adapter<VerbatologEventViewHolder> {

    private final List<EventModel> mEventsList;

    @Inject
    public Context mContext;

    private SoftReference<Activity> mActivitySoftReference;

    public EventsAdapter(@NonNull List<EventModel> eventsList, Activity activity) {
        VerbatoriaApplication.getApplicationComponent().inject(this);
        mEventsList = eventsList;
        mActivitySoftReference = new SoftReference<>(activity);
    }

    @Override
    public VerbatologEventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new VerbatologEventViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mEventsList.size();
    }

    @Override
    public void onBindViewHolder(VerbatologEventViewHolder holder, int position) {
        EventModel event = mEventsList.get(position);
        String childName = event.getChild().getName();
        if (childName == null) {
            holder.setChildName(mContext.getString(R.string.dashboard_unknown));
        } else {
            holder.setChildName(childName);
        }
        holder.setAge(event.getFullAge(mContext));
        holder.setTimePeriod(event.getEventTime());
        holder.setOnClickListener(new VerbatologEventOnClickListener(event));
    }

    private class VerbatologEventOnClickListener implements View.OnClickListener {

        private SoftReference<EventModel> mEventModelSoftReference;

        VerbatologEventOnClickListener(EventModel eventModel) {
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
