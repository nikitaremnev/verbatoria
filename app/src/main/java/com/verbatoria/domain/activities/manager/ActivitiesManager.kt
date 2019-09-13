package com.verbatoria.domain.activities.manager

import com.verbatoria.domain.activities.model.Activity
import com.verbatoria.domain.activities.model.ActivityCode
import com.verbatoria.domain.activities.model.ActivityEntity
import com.verbatoria.domain.activities.model.GroupedActivities
import com.verbatoria.domain.activities.repository.ActivitiesRepository
import com.verbatoria.infrastructure.extensions.millisecondsToSeconds
import java.util.*

/**
 * @author n.remnev
 */

interface ActivitiesManager {

    fun getByEventId(eventId: String): GroupedActivities

    fun saveActivity(eventId: String, activityCode: ActivityCode, startTime: Long, endTime: Long)

    fun deleteByEventId(eventId: String)

}

class ActivitiesManagerImpl(
    private val activitiesRepository: ActivitiesRepository
) : ActivitiesManager {

    override fun getByEventId(eventId: String): GroupedActivities {
        val activitiesList = mutableListOf<Activity>()
        val groupedActivities = activitiesRepository
            .getByEventId(eventId)
            .groupBy { activity ->
                activity.activityCode
            }
        groupedActivities.keys
            .forEach { activityCode ->
                val activity = Activity(ActivityCode.valueOf(activityCode))
                val activities = groupedActivities[activityCode]
                activities?.forEach { activityEntity ->
                    activity.addTime(
                        (activityEntity.endTime - activityEntity.startTime).millisecondsToSeconds()
                    )
                }
                activitiesList.add(activity)
            }
        return GroupedActivities(
            activitiesList
        )
    }

    override fun saveActivity(
        eventId: String,
        activityCode: ActivityCode,
        startTime: Long,
        endTime: Long
    ) {
        activitiesRepository.save(
            ActivityEntity(
                id = generateId(),
                eventId = eventId,
                activityCode = activityCode.code,
                startTime = startTime,
                endTime = endTime
            )
        )
    }

    override fun deleteByEventId(eventId: String) {
        activitiesRepository.deleteByEventId(eventId)
    }

    private fun generateId() = UUID.randomUUID().toString()

}