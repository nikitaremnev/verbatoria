package com.verbatoria.di.writing

import com.verbatoria.di.BaseInjector
import com.verbatoria.ui.writing.WritingActivity
import dagger.Subcomponent

/**
 * @author n.remnev
 */

@Subcomponent(modules = [WritingModule::class])
interface WritingComponent : BaseInjector<WritingActivity> {

    @Subcomponent.Builder
    interface Builder {

        fun build(): WritingComponent

    }

}