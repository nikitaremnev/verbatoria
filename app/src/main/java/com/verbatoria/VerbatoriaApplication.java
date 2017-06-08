package com.verbatoria;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.verbatoria.di.application.ApplicationComponent;
import com.verbatoria.di.application.DaggerApplicationComponent;

/**
 * Application-класс. Инициализирует даггер-компонент для построения всех зависимостей.
 *
 * @author nikitaremnev
 */

public class VerbatoriaApplication extends MultiDexApplication {

    @NonNull
    private ApplicationComponent mApplicationComponent;

    @NonNull
    public static VerbatoriaApplication get(@NonNull Context context) {
        return (VerbatoriaApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = DaggerApplicationComponent.create();
        MultiDex.install(this);
    }

    @NonNull
    public ApplicationComponent applicationComponent() {
        return mApplicationComponent;
    }

}
