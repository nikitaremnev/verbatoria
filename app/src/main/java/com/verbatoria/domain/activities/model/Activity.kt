package com.verbatoria.domain.activities.model

/**
 * @author n.remnev
 */

data class Activity(
    val activityCode: ActivityCode,
    var totalTime: Int = 0,
    var residualTime: Int = MINIMUM_ACTIVITY_TIME,
    var isDone: Boolean = false
) {

    fun addTime(time: Int): Boolean {
        totalTime += time
        residualTime = MINIMUM_ACTIVITY_TIME - totalTime
        isDone = residualTime <= 0
        return isDone
    }

}