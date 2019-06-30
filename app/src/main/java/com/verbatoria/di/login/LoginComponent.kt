package com.verbatoria.di.login

import com.verbatoria.di.base.BaseInjector
import com.verbatoria.ui.login.LoginActivity
import dagger.Subcomponent

/**
 * @author n.remnev
 */

@Subcomponent(modules = [LoginModule::class])
@LoginScope
interface LoginComponent : BaseInjector<LoginActivity> {

    @Subcomponent.Builder
    interface Builder {

        fun build(): LoginComponent

    }

}
