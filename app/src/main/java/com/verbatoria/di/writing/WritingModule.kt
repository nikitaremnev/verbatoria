package com.verbatoria.di.writing

import com.verbatoria.business.writing.WritingInteractorImpl
import com.verbatoria.domain.activities.manager.ActivitiesManager
import com.verbatoria.domain.bci_data.manager.BCIDataManager
import com.verbatoria.domain.dashboard.info.manager.InfoManager
import com.verbatoria.domain.late_send.manager.LateSendManager
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
        sessionId: String,
        childAge: Int,
        infoManager: InfoManager,
        activitiesManager: ActivitiesManager,
        bciDataManager: BCIDataManager,
        lateSendManager: LateSendManager,
        rxSchedulersFactory: RxSchedulersFactory
    ): WritingPresenter =
        WritingPresenter(
            sessionId,
            childAge,
            WritingInteractorImpl(
                infoManager,
                activitiesManager,
                bciDataManager,
                lateSendManager,
                rxSchedulersFactory
            )
        )

}