package com.verbatoria.di.late_send;

import com.verbatoria.di.session.SessionModule;
import com.verbatoria.di.session.SessionScope;
import com.verbatoria.presentation.late_send.view.LateSendActivity;

import dagger.Subcomponent;

/**
 * Компонент Даггера для логина
 *
 * @author nikitaremnev
 */
@Subcomponent(modules = {LateSendModule.class, SessionModule.class})
@SessionScope
public interface LateSendComponent {
    
    void inject(LateSendActivity lateSendActivity);

}
