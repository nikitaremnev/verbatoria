package com.verbatoria.di.token;

import dagger.Subcomponent;

/**
 * Компонент Даггера для логина
 *
 * @author nikitaremnev
 */
@Subcomponent(modules = {TokenModule.class})
@TokenScope
public interface TokenComponent {

//    void inject(LoginRepository loginRepository);

}
