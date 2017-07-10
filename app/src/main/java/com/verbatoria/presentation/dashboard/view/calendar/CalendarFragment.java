package com.verbatoria.presentation.dashboard.view.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.remnev.verbatoriamini.R;
import com.verbatoria.presentation.dashboard.view.DashboardActivity;
import com.verbatoria.presentation.dashboard.view.calendar.add.AddCalendarEventActivity;
import com.verbatoria.presentation.dashboard.view.settings.ISettingsView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Фрагмент для отображения календаря
 *
 * @author nikitaremnev
 */
public class CalendarFragment extends Fragment implements ICalendarView {

    @BindView(R.id.add_event_button)
    public FloatingActionButton mAddEventButton;

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
        setUpAddEventButton();
        return rootView;
    }

    private void setUpAddEventButton() {
        mAddEventButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddCalendarEventActivity.class);
            startActivity(intent);
        });
    }

}
