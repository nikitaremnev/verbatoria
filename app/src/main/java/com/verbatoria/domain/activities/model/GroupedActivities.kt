package com.verbatoria.domain.activities.model

/**
 * @author n.remnev
 */

const val MINIMUM_ACTIVITY_TIME = 15

data class GroupedActivities(
    private val activities: MutableList<Activity> = mutableListOf()
) {

    fun getActivityByCode(code: ActivityCode): Activity? =
        activities.find { activity ->
            activity.activityCode == code
        }

    fun addTimeToActivity(code: ActivityCode, time: Int): Boolean {
        val currentActivity = getActivityByCode(code)
        return if (currentActivity == null) {
            val newActivity = Activity(code)
            activities.add(newActivity)
            newActivity.addTime(time)
        } else {
            currentActivity.addTime(time)
        }
    }

    fun addActivityIfNotAdded(code: ActivityCode) {
        val currentActivity = getActivityByCode(code)
        if (currentActivity == null) {
            val newActivity = Activity(code)
            activities.add(newActivity)
        }
    }

    fun isActivityDone(code: ActivityCode): Boolean =
        getActivityByCode(code)?.isDone ?: false

    fun isAllActivitiesDone(): Boolean {
        if (activities.size < ActivityCode.values().size) {
            return false
        }
        var isAllActivitiesDone = true
        activities.forEach { activity ->
            if (!activity.isDone) {
                isAllActivitiesDone = false
                return@forEach
            }
        }
        return isAllActivitiesDone
    }

}

enum class ActivityCode(val code: Int) {

    CODE_99(99), CODE_11(11),
    CODE_21(21), CODE_31(31),
    CODE_41(41), CODE_51(51),
    CODE_61(61), CODE_71(71);

    companion object {

        fun valueOf(code: Int) =
            when (code) {
                CODE_99.code -> CODE_99
                CODE_11.code -> CODE_11
                CODE_21.code -> CODE_21
                CODE_31.code -> CODE_31
                CODE_41.code -> CODE_41
                CODE_51.code -> CODE_51
                CODE_61.code -> CODE_61
                CODE_71.code -> CODE_71
                else -> throw IllegalArgumentException("Wrong code for ActivityCode")
            }

    }

}