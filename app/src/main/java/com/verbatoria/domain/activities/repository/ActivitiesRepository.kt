package com.verbatoria.domain.activities.repository

import com.verbatoria.domain.activities.model.ActivityEntity

/**
 * @author n.remnev
 */

interface ActivitiesRepository {

    fun save(activities: List<ActivityEntity>)

    fun save(activity: ActivityEntity)

    fun getBySessionId(sessionId: String): List<ActivityEntity>

    fun deleteBySessionId(sessionId: String)

}

class ActivitiesRepositoryImpl(
    private val dao: ActivitiesDao
) : ActivitiesRepository {

    override fun save(activities: List<ActivityEntity>) {
        dao.save(activities)
    }

    override fun save(activity: ActivityEntity) {
        dao.save(activity)
    }

    override fun getBySessionId(sessionId: String): List<ActivityEntity> =
        dao.findBySessionId(sessionId)

    override fun deleteBySessionId(sessionId: String) {
        dao.deleteBySessionId(sessionId)
    }

}
