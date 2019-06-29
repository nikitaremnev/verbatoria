package com.verbatoria.di.calendar;

import com.verbatoria.ui.calendar.view.add.children.ChildActivity;
import com.verbatoria.ui.calendar.view.add.children.search.SearchChildrenActivity;
import com.verbatoria.ui.calendar.view.add.clients.ClientsActivity;
import com.verbatoria.ui.calendar.view.add.clients.search.SearchClientsActivity;
import com.verbatoria.ui.calendar.view.detail.EventDetailActivity;

import dagger.Subcomponent;

/**
 * Компонент Даггера для календаря
 *
 * @author nikitaremnev
 */
@Subcomponent(modules = {CalendarModule.class})
@CalendarScope
public interface CalendarComponent {

    void inject(ChildActivity childActivity);

    void inject(ClientsActivity clientActivity);

    void inject(EventDetailActivity eventDetailActivity);

    void inject(SearchClientsActivity searchClientsActivity);

    void inject(SearchChildrenActivity searchChildrenActivity);

}
