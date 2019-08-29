package com.verbatoria.di.dashboard.info

import com.verbatoria.business.dashboard.info.InfoInteractorImpl
import com.verbatoria.di.FragmentScope
import com.verbatoria.domain.dashboard.info.manager.InfoManager
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import com.verbatoria.ui.dashboard.info.InfoPresenter
import dagger.Module
import dagger.Provides

/**
 * @author n.remnev
 */

@Module
class InfoModule {

    @Provides
    @FragmentScope
    fun provideInfoPresenter(
        infoManager: InfoManager,
        schedulersFactory: RxSchedulersFactory
    ): InfoPresenter =
        InfoPresenter(
            InfoInteractorImpl(
                infoManager,
                schedulersFactory)
        )

}
