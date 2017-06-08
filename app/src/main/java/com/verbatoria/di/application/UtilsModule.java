package com.verbatoria.di.application;

import com.verbatoria.utils.rx.RxSchedulers;
import com.verbatoria.utils.rx.RxSchedulersAbs;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 *
 * Модуль для Utils
 *
 * @author nikitaremnev
 */
@Module
public class UtilsModule {

    @Provides
    @Singleton
    RxSchedulersAbs provideRxSchedulersAbs() {
        return new RxSchedulers();
    }

}
