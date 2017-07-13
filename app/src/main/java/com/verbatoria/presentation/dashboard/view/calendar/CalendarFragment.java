package com.verbatoria.presentation.dashboard.view.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.remnev.verbatoriamini.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.di.dashboard.DashboardModule;
import com.verbatoria.presentation.dashboard.presenter.calendar.ICalendarPresenter;
import com.verbatoria.presentation.dashboard.presenter.main.IDashboardMainPresenter;
import com.verbatoria.presentation.dashboard.view.DashboardActivity;
import com.verbatoria.presentation.dashboard.view.calendar.add.AddCalendarEventActivity;
import com.verbatoria.presentation.dashboard.view.calendar.detail.CalendarEventDetailActivity;
import com.verbatoria.presentation.dashboard.view.main.events.adapter.VerbatologEventsAdapter;
import com.verbatoria.presentation.dashboard.view.settings.ISettingsView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Фрагмент для отображения календаря
 *
 * @author nikitaremnev
 */
public class CalendarFragment extends Fragment implements ICalendarView {

    @Inject
    ICalendarPresenter mCalendarPresenter;

    @BindView(R.id.add_event_button)
    public FloatingActionButton mAddEventButton;

    @BindView(R.id.events_recycler_view)
    public RecyclerView mEventsRecyclerView;

    @BindView(R.id.events_text_view)
    public TextView mEventsTextView;

    public CalendarFragment() {
        // Required empty public constructor
    }

    public static CalendarFragment newInstance() {
        return new CalendarFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        ButterKnife.bind(this, rootView);
        VerbatoriaApplication.getApplicationComponent().addModule(new DashboardModule()).inject(this);
        setUpAddEventButton();
        setUpRecyclerView();

        mCalendarPresenter.bindView(this);
        mCalendarPresenter.updateVerbatologEvents();
        return rootView;
    }

    @Override
    public void showVerbatologEvents(List<EventModel> verbatologEvents) {
        setUpEventsLabel(verbatologEvents.size());
        mEventsRecyclerView.setAdapter(new VerbatologEventsAdapter(verbatologEvents, getActivity()));
    }

    private void setUpAddEventButton() {
        mAddEventButton.setOnClickListener(v -> startActivity(AddCalendarEventActivity.newInstance(getContext())));
    }

    private void setUpRecyclerView() {
        mEventsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mEventsRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    private void setUpEventsLabel(int eventsSize) {
        if (eventsSize == 0) {
            mEventsTextView.setText(getString(R.string.dashboard_no_events_title));
        } else {
            mEventsTextView.setText(getString(R.string.dashboard_events_title));
        }
    }

}
