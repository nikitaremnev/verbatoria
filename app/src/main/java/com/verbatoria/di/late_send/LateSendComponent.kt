package com.verbatoria.di.late_send;

import com.verbatoria.di.BaseInjector
import com.verbatoria.ui.late_send.LateSendActivity
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
