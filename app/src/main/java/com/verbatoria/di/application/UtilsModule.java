package com.verbatoria.di.application;

import com.verbatoria.utils.rx.IRxSchedulers;
import com.verbatoria.utils.rx.RxSchedulers;
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
    IRxSchedulers provideRxSchedulersAbs() {
        return new RxSchedulers();
    }

}
