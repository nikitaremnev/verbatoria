package com.verbatoria.di.dashboard.settings

import com.remnev.verbatoria.R
import com.verbatoria.business.dashboard.settings.SettingsConfiguratorImpl
import com.verbatoria.business.dashboard.settings.SettingsInteractorImpl
import com.verbatoria.business.dashboard.settings.model.item.SettingsItemModel
import com.verbatoria.di.FragmentScope
import com.verbatoria.domain.dashboard.settings.SettingsRepository
import com.verbatoria.domain.session.SessionManager
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import com.verbatoria.infrastructure.utils.ViewInflater
import com.verbatoria.ui.common.Adapter
import com.verbatoria.ui.common.ItemAdapter
import com.verbatoria.ui.dashboard.settings.SettingsPresenter
import com.verbatoria.ui.dashboard.settings.item.SettingsItemBinder
import com.verbatoria.ui.dashboard.settings.item.SettingsItemViewHolderImpl
import dagger.Module
import dagger.Provides

/**
 * @author n.remnev
 */

@Module
class SettingsModule {

    @Provides
    @FragmentScope
    fun provideSettingsPresenter(
        sessionManager: SessionManager,
        settingsRepository: SettingsRepository,
        schedulersFactory: RxSchedulersFactory
    ): SettingsPresenter =
        SettingsPresenter(
            SettingsInteractorImpl(
                sessionManager,
                settingsRepository,
                SettingsConfiguratorImpl(),
                schedulersFactory
            )
        )

    @Provides
    @FragmentScope
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
