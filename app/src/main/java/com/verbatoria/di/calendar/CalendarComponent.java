package com.verbatoria.di.calendar;

import com.verbatoria.di.login.LoginScope;
import com.verbatoria.presentation.calendar.view.add.children.ChildrenActivity;
import com.verbatoria.presentation.calendar.view.add.clients.ClientsActivity;

import dagger.Subcomponent;

/**
 * Компонент Даггера для логина
 *
 * @author nikitaremnev
 */
@Subcomponent
@LoginScope
public interface CalendarComponent {

    void inject(ChildrenActivity childrenActivity);

    void inject(ClientsActivity clientActivity);

}
