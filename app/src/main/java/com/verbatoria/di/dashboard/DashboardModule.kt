package com.verbatoria.di.dashboard

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
    fun provideDashboardPresenter(): DashboardPresenter =
        DashboardPresenter()

}
