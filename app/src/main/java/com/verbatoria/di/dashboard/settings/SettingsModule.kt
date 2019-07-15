package com.verbatoria.di.dashboard.settings

import com.remnev.verbatoria.R
import com.verbatoria.business.dashboard.settings.SettingsConfiguratorImpl
import com.verbatoria.business.dashboard.settings.SettingsInteractorImpl
import com.verbatoria.business.dashboard.settings.model.SettingsItemModel
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import com.verbatoria.infrastructure.utils.ViewInflater
import com.verbatoria.ui.common.Adapter
import com.verbatoria.ui.common.ItemAdapter
import com.verbatoria.ui.dashboard.settings.SettingsPresenter
import com.verbatoria.ui.dashboard.settings.item.SettingsItemBinder
import com.verbatoria.ui.dashboard.settings.item.SettingsItemViewHolderImpl
import dagger.Module
import dagger.Provides
import dagger.Reusable

/**
 * @author n.remnev
 */

@Module
class SettingsModule {

    @Provides
    @Reusable
    fun provideSettingsPresenter(schedulersFactory: RxSchedulersFactory): SettingsPresenter =
        SettingsPresenter(
            SettingsInteractorImpl(SettingsConfiguratorImpl(), schedulersFactory)
        )

    @Provides
    @Reusable
    fun provideAdapter(
        settingsPresenter: SettingsPresenter
    ): Adapter =
        Adapter(
            listOf(
                ItemAdapter(
                    { it is SettingsItemModel },
                    {
                        SettingsItemViewHolderImpl(
                            ViewInflater.inflate(R.layout.item_settings, it), settingsPresenter
                        )
                    },
                    SettingsItemBinder()
                )
            )
        )

}
