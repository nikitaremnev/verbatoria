package com.verbatoria.presentation.calendar.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.remnev.verbatoriamini.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.di.dashboard.DashboardModule;
import com.verbatoria.presentation.calendar.presenter.ICalendarPresenter;
import com.verbatoria.presentation.calendar.view.detail.EventDetailActivity;
import com.verbatoria.presentation.calendar.view.adapter.EventsAdapter;
import com.verbatoria.utils.DateUtils;

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

    @BindView(R.id.select_date_button)
    public FloatingActionButton mCalendarButton;

    @BindView(R.id.events_recycler_view)
    public RecyclerView mEventsRecyclerView;

    @BindView(R.id.no_events_text_view)
    public TextView mNoEventsTextView;

    @BindView(R.id.current_date_text_view)
    public TextView mCurrentDateTextView;

    private Calendar mCalendar;

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
        mNoEventsTextView.setVisibility(verbatologEvents.size() == 0 ? View.VISIBLE : View.GONE);
        mEventsRecyclerView.setAdapter(new EventsAdapter(verbatologEvents, getActivity()));
    }

    private void setUpViews() {
        mCalendar = Calendar.getInstance();
        setUpAddEventButton();
        setUpCalendarButton();
        setUpCurrentDate();
        setUpRecyclerView();
    }

    private void setUpAddEventButton() {
        mAddEventButton.setOnClickListener(v -> startActivityForResult(EventDetailActivity.newInstance(getContext()), ACTIVITY_EVENT_CODE));
        mAddEventButton.show();
    }

    private void setUpCalendarButton() {
        mCalendarButton.setOnClickListener(v -> showCalendar());
        mCalendarButton.show();
    }

    private void setUpCurrentDate() {
        mCurrentDateTextView.setText(DateUtils.toUIDateString(mCalendar.getTime()));
    }


    private void setUpRecyclerView() {
        mEventsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mEventsRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    private void showCalendar() {
        mCalendarButton.setOnClickListener(v -> {
            DatePickerDialog date = new DatePickerDialog(
                    getActivity(),
                    this,
                    mCalendar.get(Calendar.YEAR),
                    mCalendar.get(Calendar.MONTH),
                    mCalendar.get(Calendar.DAY_OF_MONTH));
            date.show();
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}
