package com.verbatoria.di.application;

import android.content.Context;
import android.support.annotation.NonNull;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

/**
 * Модуль для контекста
 *
 * @author nikitaremnev
 */

@Module
public class ApplicationModule {

    private final Context mApplicationContext;

    public ApplicationModule(@NonNull Context context) {
        mApplicationContext = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return mApplicationContext;
    }

}
