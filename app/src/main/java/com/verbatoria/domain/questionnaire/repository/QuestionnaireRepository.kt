package com.verbatoria.domain.questionnaire.repository

import com.verbatoria.domain.questionnaire.model.Questionnaire

/**
 * @author n.remnev
 */

interface QuestionnaireRepository {

    fun save(questionnaire: Questionnaire)

    fun findBySessionId(sessionId: String): Questionnaire?

    fun deleteBySessionId(sessionId: String)

}

class QuestionnaireRepositoryImpl(
    private val dao: QuestionnaireDao,
    private val converter: QuestionnaireConverter
) : QuestionnaireRepository {

    override fun save(questionnaire: Questionnaire) {
        dao.save(converter.toEntity(questionnaire))
    }

    override fun findBySessionId(sessionId: String): Questionnaire? =
        dao.findBySessionId(sessionId)?.let(converter::toDomain)

    override fun deleteBySessionId(sessionId: String) {
        dao.deleteBySessionId(sessionId)
    }
}
