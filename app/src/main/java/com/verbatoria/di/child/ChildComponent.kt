package com.verbatoria.di.child

import com.verbatoria.di.BaseInjector
import com.verbatoria.ui.child.ChildActivity
import dagger.Subcomponent

/**
 * @author n.remnev
 */

@Subcomponent(modules = [ChildModule::class])
interface ChildComponent : BaseInjector<ChildActivity> {

    @Subcomponent.Builder
    interface Builder {

        fun build(): ChildComponent

    }

}