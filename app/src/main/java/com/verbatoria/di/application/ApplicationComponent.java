package com.verbatoria.di.application;

import com.verbatoria.di.dashboard.DashboardComponent;
import com.verbatoria.di.dashboard.DashboardModule;
import com.verbatoria.di.login.LoginComponent;
import com.verbatoria.di.login.LoginModule;
import com.verbatoria.di.token.TokenComponent;
import com.verbatoria.di.utils.UtilsComponent;
import com.verbatoria.di.utils.UtilsModule;

import javax.inject.Singleton;
import dagger.Component;

/**
 * Компонент Даггера для модуля контекста
 *
 * @author nikitaremnev
 */
@Singleton
@Component(modules = {ApplicationModule.class, UtilsModule.class})
public interface ApplicationComponent extends TokenComponent, UtilsComponent {

    LoginComponent addModule(LoginModule loginModule);

    DashboardComponent addModule(DashboardModule dashboardModule);

}
