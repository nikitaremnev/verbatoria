package com.verbatoria.domain.questionnaire.repository

import com.verbatoria.domain.questionnaire.model.Questionnaire

/**
 * @author n.remnev
 */

interface QuestionnaireRepository {

    fun save(questionnaire: Questionnaire)

    fun findByEventId(eventId: String): Questionnaire?

    fun deleteByEventId(eventId: String)

}

class QuestionnaireRepositoryImpl(
    private val dao: QuestionnaireDao,
    private val converter: QuestionnaireConverter
) : QuestionnaireRepository {

    override fun save(questionnaire: Questionnaire) {
        dao.save(converter.toEntity(questionnaire))
    }

    override fun findByEventId(eventId: String): Questionnaire? =
        dao.findByEventId(eventId)?.let(converter::toDomain)

    override fun deleteByEventId(eventId: String) {
        dao.deleteByEventId(eventId)
    }
}
