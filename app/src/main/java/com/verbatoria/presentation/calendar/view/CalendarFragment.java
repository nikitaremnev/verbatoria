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

import com.remnev.verbatoria.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.di.dashboard.DashboardModule;
import com.verbatoria.presentation.calendar.presenter.ICalendarPresenter;
import com.verbatoria.presentation.calendar.view.detail.EventDetailActivity;
import com.verbatoria.presentation.calendar.view.adapter.EventsAdapter;
import com.verbatoria.utils.DateUtils;
import com.verbatoria.utils.Helper;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.verbatoria.utils.LocaleHelper.LOCALE_RU;

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
    public TextView mAddEventButton;

    @BindView(R.id.tomorrow_date_button)
    public FloatingActionButton mTomorrowDateButton;

    @BindView(R.id.yesterday_date_button)
    public FloatingActionButton mYesterdayDateButton;

    @BindView(R.id.current_date_text_view)
    public TextView mCalendarButton;

    @BindView(R.id.events_recycler_view)
    public RecyclerView mEventsRecyclerView;

    @BindView(R.id.no_events_text_view)
    public TextView mNoEventsTextView;

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

        mCalendarPresenter.restoreLastDate();
        return rootView;
    }

    @Override
    public void showVerbatologEvents(List<EventModel> verbatologEvents) {
        mNoEventsTextView.setVisibility(verbatologEvents.size() == 0 ? View.VISIBLE : View.GONE);
        mEventsRecyclerView.setAdapter(new EventsAdapter(verbatologEvents, getActivity()));
    }

    private void setUpCurrentDate() {
        mCalendarButton.setText(DateUtils.toUIDateString(mCalendar.getTime()));
    }

    @Override
    public void updateTime(Date date) {
        mCalendar.setTime(date);
        setUpCurrentDate();
        mCalendarPresenter.updateVerbatologEvents(mCalendar);
    }

    private void setUpViews() {
        mCalendar = Calendar.getInstance(new Locale(LOCALE_RU));
        setUpAddEventButton();
        setUpCalendarButtons();
        setUpCurrentDate();
        setUpRecyclerView();
    }

    private void setUpAddEventButton() {
        mAddEventButton.setOnClickListener(v -> startActivityForResult(EventDetailActivity.newInstance(getContext()), ACTIVITY_EVENT_CODE));
    }

    private void setUpCalendarButtons() {
        mCalendarButton.setOnClickListener(v -> showCalendar());
        mTomorrowDateButton.setOnClickListener(v -> showTomorrowCalendar());
        mYesterdayDateButton.setOnClickListener(v -> showYesterdayCalendar());
        mTomorrowDateButton.show();
        mYesterdayDateButton.show();
    }

    private void setUpRecyclerView() {
        mEventsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mEventsRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    private void showCalendar() {
        DatePickerDialog date = new DatePickerDialog(
                getActivity(),
                this,
                mCalendar.get(Calendar.YEAR),
                mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH));
        date.show();
    }

    private void showTomorrowCalendar() {
        mCalendar.set(Calendar.DAY_OF_MONTH, mCalendar.get(Calendar.DAY_OF_MONTH) + 1);
        setUpCurrentDate();
        mCalendarPresenter.updateVerbatologEvents(mCalendar);
        Helper.showShortHintSnackBar(mEventsRecyclerView, String.format(getString(R.string.event_detail_activity_choose_date_hint), DateUtils.toUIDateString(mCalendar.getTime())));
    }

    private void showYesterdayCalendar() {
        mCalendar.set(Calendar.DAY_OF_MONTH, mCalendar.get(Calendar.DAY_OF_MONTH) - 1);
        setUpCurrentDate();
        mCalendarPresenter.updateVerbatologEvents(mCalendar);
        Helper.showShortHintSnackBar(mEventsRecyclerView, String.format(getString(R.string.event_detail_activity_choose_date_hint), DateUtils.toUIDateString(mCalendar.getTime())));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        setUpCurrentDate();
        mCalendarPresenter.updateVerbatologEvents(mCalendar);
        Helper.showShortHintSnackBar(mEventsRecyclerView, String.format(getString(R.string.event_detail_activity_choose_date_hint), DateUtils.toUIDateString(mCalendar.getTime())));
    }
}
