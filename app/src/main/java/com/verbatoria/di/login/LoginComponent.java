package com.verbatoria.di.login;

import com.verbatoria.di.application.TokenModule;
import com.verbatoria.presentation.login.view.LoginActivity;
import dagger.Subcomponent;

/**
 * Компонент Даггера для логина
 *
 * @author nikitaremnev
 */
@Subcomponent(modules = {LoginModule.class, TokenModule.class})
@LoginScope
public interface LoginComponent {

    void inject(LoginActivity loginActivity);

}
