package com.verbatoria.di.login.sms

import com.verbatoria.di.BaseInjector
import com.verbatoria.ui.login.sms.SMSLoginActivity
import dagger.BindsInstance
import dagger.Subcomponent

/**
 * @author n.remnev
 */

@Subcomponent(modules = [SMSLoginModule::class])
interface SMSLoginComponent : BaseInjector<SMSLoginActivity> {

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun phoneFromLogin(phoneFromLogin: String): Builder

        fun build(): SMSLoginComponent

    }

}
