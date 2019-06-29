package com.verbatoria.di.splash

import com.verbatoria.di.base.BaseInjector
import com.verbatoria.ui.splash.SplashActivity
import dagger.Subcomponent

/**
 * @author n.remnev
 */

@Subcomponent(modules = [SplashModule::class])
interface SplashComponent : BaseInjector<SplashActivity> {

    @Subcomponent.Builder
    interface Builder {

        fun build(): SplashComponent

    }

}