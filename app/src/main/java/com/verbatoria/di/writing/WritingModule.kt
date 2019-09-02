package com.verbatoria.di.writing

import com.verbatoria.business.writing.WritingInteractorImpl
import com.verbatoria.domain.activities.manager.ActivitiesManager
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import com.verbatoria.ui.writing.WritingPresenter
import dagger.Module
import dagger.Provides
import dagger.Reusable

/**
 * @author n.remnev
 */

@Module
class WritingModule {

    @Provides
    @Reusable
    fun provideWritingPresenter(
        eventId: String,
        activitiesManager: ActivitiesManager,
        rxSchedulersFactory: RxSchedulersFactory
    ): WritingPresenter =
        WritingPresenter(
            eventId,
            WritingInteractorImpl(
                activitiesManager,
                rxSchedulersFactory
            )
        )

}