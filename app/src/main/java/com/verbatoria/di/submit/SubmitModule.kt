package com.verbatoria.di.submit

import com.verbatoria.business.submit.SubmitInteractorImpl
import com.verbatoria.domain.submit.SubmitManager
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import com.verbatoria.ui.submit.SubmitPresenter
import dagger.Module
import dagger.Provides
import dagger.Reusable

/**
 * @author n.remnev
 */

@Module
class SubmitModule {

    @Provides
    @Reusable
    fun provideSubmitPresenter(
        eventId: String,
        submitManager: SubmitManager,
        rxSchedulersFactory: RxSchedulersFactory
    ): SubmitPresenter =
        SubmitPresenter(eventId, SubmitInteractorImpl(submitManager, rxSchedulersFactory))

}