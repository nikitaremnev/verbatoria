package com.verbatoria.business.questionnaire

import com.verbatoria.domain.dashboard.info.manager.InfoManager
import com.verbatoria.domain.late_send.manager.LateSendManager
import com.verbatoria.domain.late_send.model.LateSendState
import com.verbatoria.domain.questionnaire.manager.QuestionnaireManager
import com.verbatoria.domain.questionnaire.model.Questionnaire
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import io.reactivex.Completable
import io.reactivex.Single

/**
 * @author n.remnev
 */

interface QuestionnaireInteractor {

    fun getQuestionnaire(sessionId: String, childAge: Int): Single<Pair<Questionnaire, Boolean>>

    fun saveQuestionnaire(sessionId: String, questionnaire: Questionnaire): Completable

}

class QuestionnaireInteractorImpl(
    private val questionnaireManager: QuestionnaireManager,
    private val lateSendManager: LateSendManager,
    private val infoManager: InfoManager,
    private val schedulersFactory: RxSchedulersFactory
) : QuestionnaireInteractor {

    override fun getQuestionnaire(sessionId: String, childAge: Int): Single<Pair<Questionnaire, Boolean>> =
        Single.fromCallable {
            Pair(questionnaireManager.getQuestionnaireBySessionId(sessionId), infoManager.isAgeAvailableForArchimedes(childAge))
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun saveQuestionnaire(sessionId: String, questionnaire: Questionnaire): Completable =
        Completable.fromCallable {
            questionnaireManager.saveQuestionnaire(questionnaire)
            lateSendManager.updateLateSendState(sessionId, LateSendState.HAS_QUESTIONNAIRE)
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

}