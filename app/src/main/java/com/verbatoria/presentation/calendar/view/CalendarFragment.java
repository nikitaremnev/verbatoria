package com.verbatoria.presentation.calendar.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.philliphsu.bottomsheetpickers.date.DatePickerDialog;
import com.remnev.verbatoriamini.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.di.dashboard.DashboardModule;
import com.verbatoria.presentation.calendar.presenter.ICalendarPresenter;
import com.verbatoria.presentation.calendar.view.detail.EventDetailActivity;
import com.verbatoria.presentation.dashboard.view.main.events.adapter.VerbatologEventsAdapter;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Фрагмент для отображения календаря
 *
 * @author nikitaremnev
 */
public class CalendarFragment extends Fragment implements ICalendarView, DatePickerDialog.OnDateSetListener {

    public static final int ACTIVITY_EVENT_CODE = 30;

    @Inject
    ICalendarPresenter mCalendarPresenter;

    @BindView(R.id.add_event_button)
    public FloatingActionButton mAddEventButton;

    @BindView(R.id.events_recycler_view)
    public RecyclerView mEventsRecyclerView;

    @BindView(R.id.events_text_view)
    public TextView mEventsTextView;

    @BindView(R.id.no_events_text_view)
    public TextView mNoEventsTextView;

    @BindView(R.id.calendar_button)
    public ImageView mCalendarButton;

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

        setUpViews();

        mCalendarPresenter.bindView(this);
        mCalendarPresenter.updateVerbatologEvents();
        return rootView;
    }

    @Override
    public void showVerbatologEvents(List<EventModel> verbatologEvents) {
        setUpEventsLabel(verbatologEvents.size());
        mEventsRecyclerView.setAdapter(new VerbatologEventsAdapter(verbatologEvents, getActivity()));
    }

    private void setUpViews() {
        setUpAddEventButton();
        setUpRecyclerView();
        setUpCalendarButton();
    }

    private void setUpAddEventButton() {
        mAddEventButton.setOnClickListener(v -> startActivityForResult(EventDetailActivity.newInstance(getContext()), ACTIVITY_EVENT_CODE));
        mAddEventButton.show();
    }

    private void setUpRecyclerView() {
        mEventsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mEventsRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    private void setUpCalendarButton() {
        mCalendarButton.setOnClickListener(v -> {
            Calendar now = Calendar.getInstance();
            DatePickerDialog date = new DatePickerDialog.Builder(
                    CalendarFragment.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH))
                    .build();
            date.show(getFragmentManager(), "test");
        });
    }

    private void setUpEventsLabel(int eventsSize) {
        if (eventsSize == 0) {
            mEventsTextView.setVisibility(View.GONE);
            mNoEventsTextView.setVisibility(View.VISIBLE);
        } else {
            mEventsTextView.setVisibility(View.VISIBLE);
            mNoEventsTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {

    }

}
