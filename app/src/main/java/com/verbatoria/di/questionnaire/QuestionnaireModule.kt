package com.verbatoria.di.questionnaire

import com.verbatoria.business.questionnaire.QuestionnaireInteractorImpl
import com.verbatoria.domain.dashboard.info.manager.InfoManager
import com.verbatoria.domain.late_send.manager.LateSendManager
import com.verbatoria.domain.questionnaire.manager.QuestionnaireManager
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import com.verbatoria.ui.questionnaire.QuestionnairePresenter
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Named

/**
 * @author n.remnev
 */

@Module
class QuestionnaireModule {

    @Provides
    @Reusable
    fun provideQuestionnairePresenter(
        @Named("sessionId") sessionId: String,
        @Named("bluetoothDeviceAddress") bluetoothDeviceAddress: String,
        childAge: Int,
        questionnaireManager: QuestionnaireManager,
        lateSendManager: LateSendManager,
        infoManager: InfoManager,
        rxSchedulersFactory: RxSchedulersFactory
    ): QuestionnairePresenter =
        QuestionnairePresenter(
            sessionId,
            bluetoothDeviceAddress,
            childAge,
            QuestionnaireInteractorImpl(
                questionnaireManager,
                lateSendManager,
                infoManager,
                childAge,
                rxSchedulersFactory
            )
        )

}
