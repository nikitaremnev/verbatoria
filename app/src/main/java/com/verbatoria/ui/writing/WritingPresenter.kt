package com.verbatoria.ui.writing

import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import com.remnev.verbatoria.R
import com.verbatoria.business.writing.WritingInteractor
import com.verbatoria.domain.activities.model.Activity
import com.verbatoria.domain.activities.model.ActivityCode
import com.verbatoria.domain.activities.model.GroupedActivities
import com.verbatoria.ui.base.BasePresenter
import java.io.IOException
import java.util.*

/**
 * @author n.remnev
 */

private const val TIMER_TASK_INTERVAL = 1000L
private const val TIMER_TASK_INTERVAL_IN_SECONDS = 1
private const val FIRST_MUSIC_FILE_INDEX = 1
private const val MUSIC_BUTTON_CODE = 31

class WritingPresenter(
    private val eventId: String,
    private val writingInteractor: WritingInteractor
) : BasePresenter<WritingView>(), WritingView.Callback, WritingView.MusicPlayerCallback {

    private var groupedActivities: GroupedActivities = GroupedActivities()

    private var selectedActivity: Activity? = null

    private var isGroupedActivitiesLoaded: Boolean = false

    private var startActivityTime: Long = 0L

    //timer

    private var timerTask = createTimerTask()
    private var timer = Timer()

    //player

    private var mediaPlayer: MediaPlayer? = null
    private val musicFiles = arrayOf(
        R.raw.zvuk1,
        R.raw.zvuk2,
        R.raw.zvuk3,
        R.raw.zvuk4,
        R.raw.zvuk5,
        R.raw.zvuk6,
        R.raw.zvuk7,
        R.raw.zvuk8,
        R.raw.zvuk9,
        R.raw.zvuk10,
        R.raw.zvuk11,
        R.raw.zvuk12
    )
    private var currentMusicFileIndex: Int = 1

    init {
        getGroupedActivities()
    }

    override fun onAttachView(view: WritingView) {
        super.onAttachView(view)
        if (isGroupedActivitiesLoaded) {
            updateCodeButtonsState()
            if (groupedActivities.isAllActivitiesDone()) {
                view.showFinishButton()
            }
        }
    }

    //region WritingView.Callback

    override fun onFinishClicked() {
        if (selectedActivity != null) {
            view?.showFinishActivityFirstError()
        } else {
            view?.close()
        }
    }

    override fun onCodeButtonClicked(activityCode: ActivityCode) {
        timerTask.cancel()
        timer.cancel()
        timerTask = createTimerTask()
        timer = Timer()

        groupedActivities.addActivityIfNotAdded(activityCode)
        val currentTimeInMillis = System.currentTimeMillis()

        when {
            selectedActivity?.activityCode == activityCode -> {
                saveActivity(currentTimeInMillis)

                selectedActivity = null

                view?.hideTimer()
            }
            selectedActivity == null -> {
                selectedActivity = groupedActivities.getActivityByCode(activityCode)
                startActivityTime = currentTimeInMillis

                startTimer()
            }
            else -> {
                saveActivity(currentTimeInMillis)

                val previousSelectedActivity = selectedActivity

                selectedActivity = groupedActivities.getActivityByCode(activityCode)
                startActivityTime = currentTimeInMillis

                updateSingleCodeButtonState(previousSelectedActivity?.activityCode ?: throw IllegalStateException("trying to update code button while selectedActivity is null"))

                startTimer()
            }
        }

        updateSingleCodeButtonState(activityCode)


        if (MUSIC_BUTTON_CODE == selectedActivity?.activityCode?.code) {
            view?.setUpPauseMode()
            view?.showMusicFileName(currentMusicFileIndex.toString())
        } else {
            pausePlayer()
            view?.hidePlayer()
        }
    }

    //endregion

    //region WritingView.MusicPlayerCallback

    override fun onPlayClicked() {
        view?.showMusicFileName(currentMusicFileIndex.toString())
        if (mediaPlayer != null) {
            mediaPlayer?.start()
            view?.setUpPlayMode()
        } else if (musicFiles.size >= currentMusicFileIndex) {
            setUpPlayer()
            val assetFileDescriptor = getAssetFileDescriptor()
            if (assetFileDescriptor == null) {
                view?.showErrorSnackbar("asset file descriptor is null")
            } else {
                preparePlayer(assetFileDescriptor)
            }
        } else {
            view?.showErrorSnackbar("error in player state")
        }
    }

    override fun onPauseClicked() {
        mediaPlayer?.pause()
        view?.setUpPauseMode()
    }

    override fun onNextClicked() {
        currentMusicFileIndex ++
        if (musicFiles.size < currentMusicFileIndex) {
            currentMusicFileIndex = FIRST_MUSIC_FILE_INDEX
        }
        pausePlayer()
        view?.showMusicFileName(currentMusicFileIndex.toString())
    }

    override fun onBackClicked() {
        currentMusicFileIndex --
        if (currentMusicFileIndex < FIRST_MUSIC_FILE_INDEX) {
            currentMusicFileIndex = FIRST_MUSIC_FILE_INDEX
        }
        pausePlayer()
        view?.showMusicFileName(currentMusicFileIndex.toString())
    }

    //endregion

    private fun getGroupedActivities() {
        isGroupedActivitiesLoaded = false
        writingInteractor.getGroupedActivities(eventId)
            .subscribe({ groupedActivities ->
                this.groupedActivities = groupedActivities
                isGroupedActivitiesLoaded = true
                updateCodeButtonsState()
                if (groupedActivities.isAllActivitiesDone()) {
                    view?.showFinishButton()
                }
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
                selectedActivity?.activityCode == activityCode -> view?.setActivitySelectedState(activityCode)
            }
        }
    }

    private fun updateSingleCodeButtonState(activityCode: ActivityCode) {
        val activityByCode = groupedActivities.getActivityByCode(activityCode)
        when {
            selectedActivity?.activityCode == activityCode -> view?.setActivitySelectedState(activityCode)
            activityByCode?.isDone == true -> view?.setActivityDoneState(activityCode)
            else -> view?.setActivityNewState(activityCode)
        }
    }

    private fun saveActivity(endActivityTime: Long) {
        writingInteractor.saveActivity(
            eventId,
            selectedActivity?.activityCode ?: throw IllegalStateException("trying to save activity while activityCode is null"),
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

    private fun startTimer() {
        view?.showTimer()
        updateTimerTime(false)
        timer.schedule(timerTask, TIMER_TASK_INTERVAL, TIMER_TASK_INTERVAL)
    }

    private fun createTimerTask(): TimerTask =
        object : TimerTask() {
            override fun run() {
                updateTimerTime()
            }
        }

    private fun updateTimerTime(withTimeUpdate: Boolean = true) {
        if (withTimeUpdate) {
            selectedActivity?.addTime(TIMER_TASK_INTERVAL_IN_SECONDS)
        }
        view?.updateTimerTime(selectedActivity?.totalTime ?: TIMER_TASK_INTERVAL_IN_SECONDS)
    }

    //region player methods

    private fun setUpPlayer() {
        mediaPlayer = MediaPlayer().apply {
            setOnPreparedListener {
                start()
                view?.setUpPlayMode()
            }
            setOnCompletionListener {
                view?.setUpPauseMode()
                pausePlayer()
            }
        }
    }

    private fun preparePlayer(assetFileDescriptor: AssetFileDescriptor) {
        try {
            mediaPlayer?.setDataSource(
                assetFileDescriptor.fileDescriptor,
                assetFileDescriptor.startOffset,
                assetFileDescriptor.length
            )
            assetFileDescriptor.close()
            mediaPlayer?.prepare()
        } catch (exception: IOException) {
            exception.printStackTrace()
            view?.showErrorSnackbar(exception.localizedMessage)
        }
    }

    private fun pausePlayer() {
        mediaPlayer?.let { player ->
            player.stop()
            player.reset()
            player.release()
            mediaPlayer = null

            view?.setUpPauseMode()
        }
    }

    private fun getAssetFileDescriptor(): AssetFileDescriptor? =
        view?.getAssetFileDescriptor(musicFiles[currentMusicFileIndex - 1])

    //endregion


}