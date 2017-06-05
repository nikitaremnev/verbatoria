package com.verbatoria.di.application;

import com.verbatoria.di.login.LoginComponent;
import com.verbatoria.di.login.LoginModule;

import javax.inject.Singleton;
import dagger.Component;

/**
 * Компонент даггера для модуля контекста
 *
 * @author nikitaremnev
 */

@Component(modules = {ApplicationComponent.class})
@Singleton
public interface ApplicationComponent {

    LoginComponent addModule(LoginModule loginModule);

}
