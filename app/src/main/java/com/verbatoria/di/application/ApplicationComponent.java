package com.verbatoria.di.application;

import com.verbatoria.di.dashboard.DashboardComponent;
import com.verbatoria.di.dashboard.DashboardModule;
import com.verbatoria.di.login.LoginComponent;
import com.verbatoria.di.login.LoginModule;
import com.verbatoria.di.token.TokenComponentInjects;
import com.verbatoria.di.token.TokenModule;

import javax.inject.Singleton;
import dagger.Component;

/**
 * Компонент Даггера для модуля контекста
 *
 * @author nikitaremnev
 */
@Singleton
@Component(modules = {ApplicationModule.class, TokenModule.class})
public interface ApplicationComponent extends ApplicationComponentInjects, TokenComponentInjects {

    LoginComponent addModule(LoginModule loginModule);

    DashboardComponent addModule(DashboardModule dashboardModule);

}
