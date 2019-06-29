package com.verbatoria.di.dashboard;

import com.verbatoria.ui.calendar.view.CalendarFragment;
import com.verbatoria.ui.dashboard.view.DashboardActivity;
import com.verbatoria.ui.dashboard.view.info.VerbatologInfoFragment;
import com.verbatoria.ui.dashboard.view.settings.SettingsFragment;

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

    void inject(VerbatologInfoFragment verbatologInfoFragment);

    void inject(CalendarFragment calendarFragment);

    void inject(SettingsFragment settingsFragment);

}
