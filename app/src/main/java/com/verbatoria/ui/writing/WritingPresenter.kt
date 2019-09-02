package com.verbatoria.ui.writing

import com.verbatoria.business.writing.WritingInteractor
import com.verbatoria.domain.activities.model.ActivityCode
import com.verbatoria.domain.activities.model.GroupedActivities
import com.verbatoria.infrastructure.extensions.millisecondsToSeconds
import com.verbatoria.ui.base.BasePresenter
import java.lang.IllegalStateException

/**
 * @author n.remnev
 */


class WritingPresenter(
    private val eventId: String,
    private val writingInteractor: WritingInteractor
) : BasePresenter<WritingView>(), WritingView.Callback {

    private var groupedActivities: GroupedActivities = GroupedActivities()

    private var selectedActivity: ActivityCode? = null

    private var isGroupedActivitiesLoaded: Boolean = false

    private var startActivityTime: Long = 0L


    init {
        getGroupedActivities()
    }

    override fun onAttachView(view: WritingView) {
        super.onAttachView(view)
        if (isGroupedActivitiesLoaded) {
            updateCodeButtonsState()
        }
    }

    //region WritingView.Callback

    override fun onCodeButtonClicked(activityCode: ActivityCode) {
        groupedActivities.addActivityIfNotAdded(activityCode)
        val currentTimeInMillis = System.currentTimeMillis()
        when (selectedActivity) {
            activityCode -> {
                saveActivity(currentTimeInMillis)

                val timeWritten = (currentTimeInMillis - startActivityTime).millisecondsToSeconds()
                groupedActivities.addTimeToActivity(activityCode, timeWritten)

                selectedActivity = null
            }
            null -> {
                selectedActivity = activityCode
                startActivityTime = currentTimeInMillis
            }
            else -> {
                saveActivity(currentTimeInMillis)

                val timeWritten = (currentTimeInMillis - startActivityTime).millisecondsToSeconds()
                groupedActivities.addTimeToActivity(
                    selectedActivity ?: throw IllegalStateException("trying to add time to activity while selectedActivity is null"),
                    timeWritten
                )

                val previousSelectedActivity = selectedActivity

                selectedActivity = activityCode
                startActivityTime = currentTimeInMillis

                updateSingleCodeButtonState(previousSelectedActivity ?: throw IllegalStateException("trying to update code button while selectedActivity is null"))
            }
        }

        updateSingleCodeButtonState(activityCode)
    }

    //endregion

    private fun getGroupedActivities() {
        isGroupedActivitiesLoaded = false
        writingInteractor.getGroupedActivities(eventId)
            .subscribe({ groupedActivities ->
                this.groupedActivities = groupedActivities
                isGroupedActivitiesLoaded = true
                updateCodeButtonsState()
            }, { error ->
                view?.showErrorSnackbar(error.localizedMessage)
            })
            .let(::addDisposable)
    }

    private fun updateCodeButtonsState() {
        ActivityCode.values().forEach { activityCode ->
            val activityByCode = groupedActivities.getActivityByCode(activityCode)
            when {
                activityByCode == null -> view?.setActivityNewState(activityCode)
                activityByCode.isDone -> view?.setActivityDoneState(activityCode)
                selectedActivity == activityCode -> view?.setActivitySelectedState(activityCode)
            }
        }
    }

    private fun updateSingleCodeButtonState(activityCode: ActivityCode) {
        val activityByCode = groupedActivities.getActivityByCode(activityCode)
        when {
            selectedActivity == activityCode -> view?.setActivitySelectedState(activityCode)
            activityByCode == null -> view?.setActivityNewState(activityCode)
            !activityByCode.isDone -> view?.setActivityNewState(activityCode)
            activityByCode.isDone -> view?.setActivityDoneState(activityCode)
        }
    }

    private fun saveActivity(endActivityTime: Long) {
        writingInteractor.saveActivity(
            eventId,
            selectedActivity
                ?: throw IllegalStateException("trying to save activity while activityCode is null"),
            startActivityTime,
            endActivityTime
        )
            .subscribe({
                //empty
            }, { error ->
                view?.showErrorSnackbar(error.localizedMessage)
            })
            .let(::addDisposable)
    }


}