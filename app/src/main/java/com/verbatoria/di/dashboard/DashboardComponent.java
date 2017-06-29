package com.verbatoria.di.dashboard;

import com.verbatoria.di.token.TokenComponentInjects;
import com.verbatoria.di.token.TokenModule;
import com.verbatoria.presentation.dashboard.view.DashboardActivity;

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

}
