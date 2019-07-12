package com.verbatoria.di.recovery_password

import com.verbatoria.di.BaseInjector
import com.verbatoria.ui.recovery_password.RecoveryPasswordActivity
import dagger.BindsInstance
import dagger.Subcomponent

/**
 * @author n.remnev
 */

@Subcomponent(modules = [RecoveryPasswordModule::class])
interface RecoveryPasswordComponent : BaseInjector<RecoveryPasswordActivity> {

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance fun phoneFromLogin(phoneFromLogin: String): Builder

        fun build(): RecoveryPasswordComponent

    }

}
