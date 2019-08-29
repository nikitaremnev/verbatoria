package com.verbatoria.domain.questionnaire.manager

import com.verbatoria.domain.questionnaire.model.Questionnaire
import com.verbatoria.domain.questionnaire.repository.QuestionnaireRepository

/**
 * @author n.remnev
 */

interface QuestionnaireManager {

    fun getQuestionnaireByEventId(eventId: String): Questionnaire

    fun saveQuestionnaire(questionnaire: Questionnaire)

    fun deleteQuestionnaireByEventId(eventId: String)

}

class QuestionnaireManagerImpl(
    private val questionnaireRepository: QuestionnaireRepository
) : QuestionnaireManager {

    override fun getQuestionnaireByEventId(eventId: String): Questionnaire =
        questionnaireRepository.findByEventId(eventId) ?: Questionnaire(eventId)

    override fun saveQuestionnaire(questionnaire: Questionnaire) {
        questionnaireRepository.save(questionnaire)
    }

    override fun deleteQuestionnaireByEventId(eventId: String) {
        questionnaireRepository.deleteByEventId(eventId)
    }

}