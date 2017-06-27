package com.verbatoria.di.application;

import android.content.Context;

import com.verbatoria.di.login.LoginComponent;
import com.verbatoria.di.login.LoginModule;
import com.verbatoria.presentation.login.presenter.LoginPresenter;
import com.verbatoria.utils.rx.IRxSchedulers;

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

}
