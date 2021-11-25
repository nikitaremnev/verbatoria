package com.verbatoria.business.questionnaire

import com.verbatoria.domain.dashboard.info.manager.InfoManager
import com.verbatoria.domain.late_send.manager.LateSendManager
import com.verbatoria.domain.late_send.model.LateSendState
import com.verbatoria.domain.questionnaire.manager.QuestionnaireManager
import com.verbatoria.domain.questionnaire.model.QuestionYesOrNoAnswer
import com.verbatoria.domain.questionnaire.model.Questionnaire
import com.verbatoria.domain.questionnaire.model.ReportType
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import io.reactivex.Completable
import io.reactivex.Single

/**
 * @author n.remnev
 */

private const val MINIMUM_HOBBY_AGE = 18

interface QuestionnaireInteractor {

    fun getQuestionnaire(sessionId: String, bluetoothDeviceAddress: String, childAge: Int): Single<Pair<Questionnaire, Boolean>>

    fun saveQuestionnaire(sessionId: String, questionnaire: Questionnaire): Completable

}

class QuestionnaireInteractorImpl(
    private val questionnaireManager: QuestionnaireManager,
    private val lateSendManager: LateSendManager,
    private val infoManager: InfoManager,
    private val childAge: Int,
    private val schedulersFactory: RxSchedulersFactory
) : QuestionnaireInteractor {

    override fun getQuestionnaire(sessionId: String, bluetoothDeviceAddress: String, childAge: Int): Single<Pair<Questionnaire, Boolean>> =
        Single.fromCallable {
            Pair(questionnaireManager.getQuestionnaireBySessionId(sessionId, bluetoothDeviceAddress), infoManager.isAgeAvailableForHobby(childAge))
        }
            .subscribeOn(schedulersFactory.database)
            .observeOn(schedulersFactory.main)

    override fun saveQuestionnaire(sessionId: String, questionnaire: Questionnaire): Completable =
        Completable.fromCallable {
            questionnaire.reportType = ReportType.TYPE_5
            questionnaire.includeAttentionMemory = QuestionYesOrNoAnswer.ANSWER_YES
            if (childAge < MINIMUM_HOBBY_AGE) {
                questionnaire.includeHobby = QuestionYesOrNoAnswer.ANSWER_NO
            } else {
                questionnaire.includeHobby = QuestionYesOrNoAnswer.ANSWER_YES
            }
            questionnaireManager.saveQuestionnaire(questionnaire)
            lateSendManager.updateLateSendState(sessionId, LateSendState.HAS_QUESTIONNAIRE)
        }
            .subscribeOn(schedulersFactory.database)
            .observeOn(schedulersFactory.main)

}
