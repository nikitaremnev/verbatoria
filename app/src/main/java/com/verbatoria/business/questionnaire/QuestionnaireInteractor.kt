package com.verbatoria.business.questionnaire

import com.verbatoria.domain.questionnaire.manager.QuestionnaireManager
import com.verbatoria.domain.questionnaire.model.Questionnaire
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import io.reactivex.Single

/**
 * @author n.remnev
 */


interface QuestionnaireInteractor {

    fun getQuestionnaire(eventId: String): Single<Questionnaire>

}

class QuestionnaireInteractorImpl(
    private val questionnaireManager: QuestionnaireManager,
    private val schedulersFactory: RxSchedulersFactory
) : QuestionnaireInteractor {

    override fun getQuestionnaire(eventId: String): Single<Questionnaire> =
        Single.fromCallable {
            questionnaireManager.getQuestionnaireByEventId(eventId)
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

}