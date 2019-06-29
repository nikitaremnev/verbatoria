package com.verbatoria.di.splash

import com.verbatoria.ui.splash.SplashPresenter
import dagger.Module
import dagger.Provides
import dagger.Reusable

/**
 * @author n.remnev
 */

@Module
class SplashModule {

    @Provides
    @Reusable
    fun provideSplashPresenter(): SplashPresenter =
        SplashPresenter()

}