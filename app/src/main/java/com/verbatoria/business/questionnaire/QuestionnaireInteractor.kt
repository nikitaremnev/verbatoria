package com.verbatoria.business.questionnaire

import com.verbatoria.domain.questionnaire.manager.QuestionnaireManager
import com.verbatoria.domain.questionnaire.model.Questionnaire
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import io.reactivex.Completable
import io.reactivex.Single

/**
 * @author n.remnev
 */


interface QuestionnaireInteractor {

    fun getQuestionnaire(sessionId: String): Single<Questionnaire>

    fun saveQuestionnaire(questionnaire: Questionnaire): Completable

}

class QuestionnaireInteractorImpl(
    private val questionnaireManager: QuestionnaireManager,
    private val schedulersFactory: RxSchedulersFactory
) : QuestionnaireInteractor {

    override fun getQuestionnaire(sessionId: String): Single<Questionnaire> =
        Single.fromCallable {
            questionnaireManager.getQuestionnaireBySessionId(sessionId)
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun saveQuestionnaire(questionnaire: Questionnaire): Completable =
        Completable.fromCallable {
            questionnaireManager.saveQuestionnaire(questionnaire)
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

}