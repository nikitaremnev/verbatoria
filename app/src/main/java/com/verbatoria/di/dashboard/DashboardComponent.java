package com.verbatoria.di.dashboard;

import com.verbatoria.di.token.TokenComponentInjects;
import com.verbatoria.di.token.TokenModule;
import com.verbatoria.presentation.dashboard.view.DashboardActivity;
import com.verbatoria.presentation.dashboard.view.calendar.CalendarFragment;
import com.verbatoria.presentation.dashboard.view.calendar.add.AddCalendarEventActivity;
import com.verbatoria.presentation.dashboard.view.calendar.detail.CalendarEventDetailActivity;
import com.verbatoria.presentation.dashboard.view.main.DashboardMainFragment;
import com.verbatoria.presentation.dashboard.view.settings.SettingsFragment;

import dagger.Subcomponent;

/**
 * Компонент Даггера для dashboard
 *
 * @author nikitaremnev
 */
@Subcomponent(modules = {DashboardModule.class})
@DashboardScope
public interface DashboardComponent {

    void inject(DashboardActivity dashboardActivity);

    void inject(AddCalendarEventActivity addCalendarEventActivity);

    void inject(CalendarEventDetailActivity calendarEventDetailActivity);

    void inject(DashboardMainFragment dashboardMainFragment);

    void inject(SettingsFragment dashboardMainFragment);

    void inject(CalendarFragment calendarFragment);
}
