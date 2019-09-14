package com.verbatoria.di.questionnaire

import com.verbatoria.business.questionnaire.QuestionnaireInteractorImpl
import com.verbatoria.domain.questionnaire.manager.QuestionnaireManager
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import com.verbatoria.ui.questionnaire.QuestionnairePresenter
import dagger.Module
import dagger.Provides
import dagger.Reusable

/**
 * @author n.remnev
 */

@Module
class QuestionnaireModule {

    @Provides
    @Reusable
    fun provideQuestionnairePresenter(
        sessionId: String,
        questionnaireManager: QuestionnaireManager,
        rxSchedulersFactory: RxSchedulersFactory
    ): QuestionnairePresenter =
        QuestionnairePresenter(
            sessionId,
            QuestionnaireInteractorImpl(
                questionnaireManager,
                rxSchedulersFactory
            )
        )

}