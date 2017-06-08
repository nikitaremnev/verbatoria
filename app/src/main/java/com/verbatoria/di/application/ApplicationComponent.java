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

@Singleton
@Component(modules = {ApplicationModule.class, UtilsModule.class})
public interface ApplicationComponent {

    LoginComponent addModule(LoginModule loginModule);

}
