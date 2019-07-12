package com.verbatoria.di.login.sms

import com.verbatoria.di.BaseInjector
import com.verbatoria.ui.login.sms.SMSLoginActivity
import dagger.Subcomponent

/**
 * @author n.remnev
 */

@Subcomponent(modules = [SMSLoginModule::class])
interface SMSLoginComponent : BaseInjector<SMSLoginActivity> {

    @Subcomponent.Builder
    interface Builder {

        fun build(): SMSLoginComponent

    }

}
