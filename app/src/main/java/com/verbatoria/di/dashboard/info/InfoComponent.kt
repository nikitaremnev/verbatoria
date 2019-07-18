package com.verbatoria.di.dashboard.info

import com.verbatoria.di.BaseInjector
import com.verbatoria.di.FragmentScope
import com.verbatoria.ui.dashboard.info.InfoFragment
import dagger.Subcomponent

/**
 * @author n.remnev
 */

@Subcomponent(modules = [InfoModule::class])
@FragmentScope
interface InfoComponent : BaseInjector<InfoFragment> {

    @Subcomponent.Builder
    interface Builder {

        fun build(): InfoComponent

    }

}
