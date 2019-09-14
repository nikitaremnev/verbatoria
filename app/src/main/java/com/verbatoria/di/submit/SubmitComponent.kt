package com.verbatoria.di.submit

import com.verbatoria.di.BaseInjector
import com.verbatoria.ui.submit.SubmitActivity
import dagger.BindsInstance
import dagger.Subcomponent

/**
 * @author n.remnev
 */

@Subcomponent(modules = [SubmitModule::class])
interface SubmitComponent : BaseInjector<SubmitActivity> {

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun sessionId(sessionId: String): Builder

        fun build(): SubmitComponent

    }

}