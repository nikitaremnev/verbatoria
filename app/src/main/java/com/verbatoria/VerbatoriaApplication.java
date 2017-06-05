package com.verbatoria;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.verbatoria.di.application.ApplicationComponent;
import com.verbatoria.di.application.ApplicationModule;

/**
 * Application-класс. Инициализирует даггер-компонент для построения всех зависимостей.
 *
 * @author nikitaremnev
 */

public class VerbatoriaApplication extends Application {

    @NonNull
    private ApplicationComponent mApplicationComponent;

    @NonNull
    public static VerbatoriaApplication get(@NonNull Context context) {
        return (VerbatoriaApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = prepareAppComponent().build();
    }

    @NonNull
    private DaggerAppComponent.Builder prepareAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new ApplicationModule(this));
    }

    @NonNull
    public ApplicationComponent applicationComponent() {
        return mApplicationComponent;
    }

}
