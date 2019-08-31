package com.verbatoria.domain.activities.repository

import com.verbatoria.domain.questionnaire.model.Questionnaire

/**
 * @author n.remnev
 */

interface ActivitiesRepository {

    fun save(questionnaire: Questionnaire)

    fun findByEventId(eventId: String): Questionnaire?

    fun deleteByEventId(eventId: String)

}

class ActivitiesRepositoryImpl(
    private val dao: ActivitiesDao,
    private val converter: ActivitiesConverter
) : ActivitiesRepository {

    override fun save(questionnaire: Questionnaire) {
        dao.save(converter.toEntity(questionnaire))
    }

    override fun findByEventId(eventId: String): Questionnaire? =
        dao.findByEventId(eventId)?.let(converter::toDomain)

    override fun deleteByEventId(eventId: String) {
        dao.deleteByEventId(eventId)
    }
}
