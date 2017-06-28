package com.verbatoria.di.application;

import com.verbatoria.di.dashboard.DashboardComponent;
import com.verbatoria.di.dashboard.DashboardModule;
import com.verbatoria.di.login.LoginComponent;
import com.verbatoria.di.login.LoginModule;
import com.verbatoria.utils.PreferencesStorage;

import javax.inject.Singleton;
import dagger.Component;

/**
 * Компонент Даггера для модуля контекста
 *
 * @author nikitaremnev
 */
@Singleton
@Component(modules = {ApplicationModule.class, UtilsModule.class})
public interface ApplicationComponent {

    LoginComponent addModule(LoginModule loginModule);

    DashboardComponent addModule(DashboardModule dashboardModule);

    void inject(PreferencesStorage preferencesStorage);

}
