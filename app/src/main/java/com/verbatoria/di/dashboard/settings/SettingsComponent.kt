package com.verbatoria.di.dashboard.settings

import com.verbatoria.di.BaseInjector
import com.verbatoria.di.FragmentScope
import com.verbatoria.ui.dashboard.settings.SettingsFragment
import dagger.Subcomponent

/**
 * @author n.remnev
 */

@Subcomponent(modules = [SettingsModule::class])
@FragmentScope
interface SettingsComponent : BaseInjector<SettingsFragment> {

    @Subcomponent.Builder
    interface Builder {

        fun build(): SettingsComponent

    }

}
