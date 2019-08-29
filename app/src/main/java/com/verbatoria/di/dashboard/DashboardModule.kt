package com.verbatoria.di.dashboard

import com.verbatoria.business.dashboard.DashboardInteractorImpl
import com.verbatoria.domain.dashboard.info.manager.InfoManager
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import com.verbatoria.ui.dashboard.DashboardPresenter
import dagger.Module
import dagger.Provides
import dagger.Reusable

/**
 * @author n.remnev
 */

@Module
class DashboardModule {

    @Provides
    @Reusable
    fun provideDashboardPresenter(
        infoManager: InfoManager,
        schedulersFactory: RxSchedulersFactory
    ): DashboardPresenter =
        DashboardPresenter(
            DashboardInteractorImpl(
                infoManager,
                schedulersFactory
            )
        )

}
