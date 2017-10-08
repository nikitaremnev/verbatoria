package com.verbatoria.di.dashboard;

import com.verbatoria.presentation.calendar.view.CalendarFragment;
import com.verbatoria.presentation.calendar.view.add.AddCalendarEventActivity;
import com.verbatoria.presentation.dashboard.view.DashboardActivity;
import com.verbatoria.presentation.dashboard.view.info.VerbatologInfoFragment;
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

    void inject(VerbatologInfoFragment verbatologInfoFragment);

    void inject(CalendarFragment calendarFragment);

    void inject(SettingsFragment settingsFragment);

}
