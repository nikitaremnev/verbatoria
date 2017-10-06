package com.verbatoria.di.calendar;

import com.verbatoria.presentation.calendar.view.add.children.ChildrenActivity;
import com.verbatoria.presentation.calendar.view.add.clients.ClientsActivity;
import com.verbatoria.presentation.calendar.view.detail.EventDetailActivity;

import dagger.Subcomponent;

/**
 * Компонент Даггера для календаря
 *
 * @author nikitaremnev
 */
@Subcomponent(modules = {CalendarModule.class})
@CalendarScope
public interface CalendarComponent {

    void inject(ChildrenActivity childrenActivity);

    void inject(ClientsActivity clientActivity);

    void inject(EventDetailActivity eventDetailActivity);

}
