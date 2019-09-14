package com.verbatoria.domain.questionnaire.manager

import com.verbatoria.domain.questionnaire.model.Questionnaire
import com.verbatoria.domain.questionnaire.repository.QuestionnaireRepository

/**
 * @author n.remnev
 */

interface QuestionnaireManager {

    fun getQuestionnaireBySessionId(sessionId: String): Questionnaire

    fun saveQuestionnaire(questionnaire: Questionnaire)

    fun deleteQuestionnaireBySessionId(sessionId: String)

}

class QuestionnaireManagerImpl(
    private val questionnaireRepository: QuestionnaireRepository
) : QuestionnaireManager {

    override fun getQuestionnaireBySessionId(sessionId: String): Questionnaire =
        questionnaireRepository.findBySessionId(sessionId) ?: Questionnaire(sessionId)

    override fun saveQuestionnaire(questionnaire: Questionnaire) {
        questionnaireRepository.save(questionnaire)
    }

    override fun deleteQuestionnaireBySessionId(sessionId: String) {
        questionnaireRepository.deleteBySessionId(sessionId)
    }

}