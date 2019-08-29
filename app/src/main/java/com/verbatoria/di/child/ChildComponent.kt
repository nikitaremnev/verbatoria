package com.verbatoria.di.child

import com.verbatoria.domain.child.model.Child
import com.verbatoria.di.BaseInjector
import com.verbatoria.ui.child.ChildActivity
import dagger.BindsInstance
import dagger.Subcomponent

/**
 * @author n.remnev
 */

@Subcomponent(modules = [ChildModule::class])
interface ChildComponent : BaseInjector<ChildActivity> {

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun eventDetailMode(eventDetailModeOrdinal: Int): Builder

        @BindsInstance
        fun child(child: Child?): Builder

        @BindsInstance
        fun clientId(clientId: String): Builder

        fun build(): ChildComponent

    }

}