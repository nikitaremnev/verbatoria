package com.verbatoria.di.writing

import com.verbatoria.di.BaseInjector
import com.verbatoria.ui.writing.WritingActivity
import dagger.BindsInstance
import dagger.Subcomponent

/**
 * @author n.remnev
 */

@Subcomponent(modules = [WritingModule::class])
interface WritingComponent : BaseInjector<WritingActivity> {

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun sessionId(sessionId: String): Builder

        fun build(): WritingComponent

    }

}