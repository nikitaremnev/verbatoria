package com.verbatoria.domain.activities.repository

import com.verbatoria.domain.activities.model.ActivityEntity

/**
 * @author n.remnev
 */

interface ActivitiesRepository {

    fun save(activities: List<ActivityEntity>)

    fun save(activity: ActivityEntity)

    fun getByEventId(eventId: String): List<ActivityEntity>

    fun deleteByEventId(eventId: String)

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

    override fun getByEventId(eventId: String): List<ActivityEntity> =
        dao.findByEventId(eventId)

    override fun deleteByEventId(eventId: String) {
        dao.deleteByEventId(eventId)
    }

}
