package com.verbatoria.di.calendar;

import com.verbatoria.di.login.LoginModule;
import com.verbatoria.di.login.LoginScope;
import com.verbatoria.di.token.TokenModule;
import com.verbatoria.presentation.login.view.login.LoginActivity;
import com.verbatoria.presentation.login.view.recovery.RecoveryActivity;

import dagger.Subcomponent;

/**
 * Компонент Даггера для логина
 *
 * @author nikitaremnev
 */
@Subcomponent
@LoginScope
public interface CalendarComponent {


}
