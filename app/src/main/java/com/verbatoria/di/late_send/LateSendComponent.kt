package com.verbatoria.di.late_send;

import com.verbatoria.di.base.BaseInjector
import com.verbatoria.presentation.late_send.LateSendActivity
import dagger.Subcomponent

/**
 * @author n.remnev
 */

@Subcomponent(modules = [LateSendModule::class])
interface LateSendComponent : BaseInjector<LateSendActivity> {

    @Subcomponent.Builder
    interface Builder {

        fun build(): LateSendComponent

    }

}
