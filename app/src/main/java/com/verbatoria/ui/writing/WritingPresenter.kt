package com.verbatoria.ui.writing

import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.util.Log
import com.neurosky.connection.EEGPower
import com.remnev.verbatoria.R
import com.verbatoria.business.writing.WritingInteractor
import com.verbatoria.component.connection.BCIConnectionStateCallback
import com.verbatoria.component.connection.BCIDataCallback
import com.verbatoria.domain.activities.model.Activity
import com.verbatoria.domain.activities.model.ActivityCode
import com.verbatoria.domain.activities.model.GroupedActivities
import com.verbatoria.domain.bci_data.model.BCIData
import com.verbatoria.infrastructure.extensions.MILLISECONDS_IN_DAY
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
private const val MAXIMUM_BCI_DATA_BLOCK_SIZE = 10

class WritingPresenter(
    private val sessionId: String,
    private val writingInteractor: WritingInteractor
) : BasePresenter<WritingView>(),
    WritingView.Callback,
    WritingView.MusicPlayerCallback,
    WritingView.BCIConnectionDialogCallback,
    BCIDataCallback,
    BCIConnectionStateCallback {

    private var groupedActivities: GroupedActivities = GroupedActivities()

    private var selectedActivity: Activity? = null

    private var isGroupedActivitiesLoaded: Boolean = false

    private var isBCIConnected: Boolean = false

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

    //bci data

    private val bciDataBlock = mutableListOf<BCIData>()
    private var currentBCIData = createNewCurrentBCIData()

    init {
        getGroupedActivities()
    }

    override fun onAttachView(view: WritingView) {
        super.onAttachView(view)
        if (isGroupedActivitiesLoaded) {
            updateCodeButtonsState()
            updateFinishButtonState()
        }
        if (!isBCIConnected) {
            view.showBCIConnectionDialog()
        }
    }

    //region WritingView.Callback

    override fun onFinishClicked() {
        if (selectedActivity != null) {
            view?.showFinishActivityFirstError()
        } else {
            view?.apply {
                disconnectBCI()
                view?.openQuestionnaire(sessionId)
            }
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
        updateFinishButtonState()

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

    //region BCIConnectionDialogCallback

    override fun onStartClicked() {
        Log.e("test", "BCIConnectionDialogCallback onStartClicked")

        view?.dismissBCIConnectionDialog()
        view?.startBCIWriting()
    }

    override fun onConnectClicked() {
        Log.e("test", "BCIConnectionDialogCallback onConnectClicked")
        view?.connectBCI()
    }

    override fun onExitClicked() {
        Log.e("test", "onExitClicked onStartClicked")

        view?.apply {
            dismissBCIConnectionDialog()
            close()
        }
    }

    override fun onSettingsClicked() {
        view?.openSettings()
    }

    //endregion

    //region BCIDataCallback

    override fun onAttentionDataReceived(attentionValue: Int) {
        Log.e("test", "onAttentionDataReceived $attentionValue")
        val currentTimeInMillis = System.currentTimeMillis()

        if (isCurrentAndNewValueSameSecond(currentTimeInMillis)) {
            currentBCIData.attention = attentionValue
            tryToAddBCIDataToBlock()
        } else {
            addBCIDataToBlockWhenTimestampChanged()
        }

        view?.addValueToGraph(attentionValue)
    }

    override fun onMediationDataReceived(mediationValue: Int) {
        Log.e("test", "onMediationDataReceived $mediationValue")
        val currentTimeInMillis = System.currentTimeMillis()
        if (isCurrentAndNewValueSameSecond(currentTimeInMillis)) {
            currentBCIData.mediation = mediationValue
            tryToAddBCIDataToBlock()
        } else {
            addBCIDataToBlockWhenTimestampChanged()
        }
    }

    override fun onEEGDataReceivedCallback(eegPower: EEGPower) {
        Log.e("test", "onEEGDataReceivedCallback $eegPower")

        val currentTimeInMillis = System.currentTimeMillis()
        if (isCurrentAndNewValueSameSecond(currentTimeInMillis)) {
            currentBCIData.delta = eegPower.delta
            currentBCIData.theta = eegPower.theta
            currentBCIData.lowAlpha = eegPower.lowAlpha
            currentBCIData.highAlpha = eegPower.highAlpha
            currentBCIData.lowBeta = eegPower.lowBeta
            currentBCIData.highBeta = eegPower.highBeta
            currentBCIData.lowGamma = eegPower.lowGamma
            currentBCIData.middleGamma = eegPower.middleGamma
            tryToAddBCIDataToBlock()
        } else {
            addBCIDataToBlockWhenTimestampChanged()
        }
    }

    //endregion

    //region BCIConnectionStateCallback

    override fun onConnecting() {
        Log.e("test", "BCIConnectionStateCallback onConnecting")

        view?.showConnectingDialogState()
    }

    override fun onConnected() {
        Log.e("test", "BCIConnectionStateCallback onConnected")
        view?.showConnectedDialogState()
        isBCIConnected = true
    }

    override fun onWorking() {
        Log.e("test", "BCIConnectionStateCallback onWorking")

        //empty
    }

    override fun onRecordingStarted() {
        Log.e("test", "BCIConnectionStateCallback onRecordingStarted")
        //empty
    }

    override fun onConnectionFailed() {
        Log.e("test", "BCIConnectionStateCallback onConnectionFailed")
        view?.showConnectionErrorDialogState()
        isBCIConnected = false
    }

    override fun onDisconnected() {
        Log.e("test", "BCIConnectionStateCallback onDisconnected")
        isBCIConnected = false
    }

    override fun onBluetoothDisabled() {
        Log.e("test", "BCIConnectionStateCallback onBluetoothDisabled")

        view?.showBluetoothDisabledDialogState()
    }

    //endregion

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

    //region activities

    private fun updateFinishButtonState() {
        if (groupedActivities.isAllActivitiesDone()) {
            view?.showFinishButton()
        }
    }

    private fun getGroupedActivities() {
        isGroupedActivitiesLoaded = false
        writingInteractor.getGroupedActivities(sessionId)
            .subscribe({ groupedActivities ->
                this.groupedActivities = groupedActivities
                isGroupedActivitiesLoaded = true
                updateCodeButtonsState()
                updateFinishButtonState()
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
            sessionId,
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

    //endregion

    //region bci data

    private fun createNewCurrentBCIData(): BCIData =
        BCIData(
            timestamp = System.currentTimeMillis()
        ).apply {
            selectedActivity?.activityCode?.let { selectedActivityCode ->
                activityCode = selectedActivityCode.code
            }
        }

    private fun tryToAddBCIDataToBlock() {
        if (currentBCIData.isFilled() && currentBCIData.isUnderActivity()) {
            bciDataBlock.add(currentBCIData)
            if (bciDataBlock.size == MAXIMUM_BCI_DATA_BLOCK_SIZE) {
                saveBCIDataBlock()
            }
            currentBCIData = createNewCurrentBCIData()
        } else if (currentBCIData.isFilled() && !currentBCIData.isUnderActivity()) {
            currentBCIData = createNewCurrentBCIData()
        }
    }

    private fun addBCIDataToBlockWhenTimestampChanged() {
        if (currentBCIData.isUnderActivity()) {
            bciDataBlock.add(currentBCIData)
            if (bciDataBlock.size == MAXIMUM_BCI_DATA_BLOCK_SIZE) {
                saveBCIDataBlock()
            }
        }
        currentBCIData = createNewCurrentBCIData()
    }

    private fun saveBCIDataBlock() {
        writingInteractor.saveBCIDataBlock(sessionId, bciDataBlock)
            .subscribe({
                bciDataBlock.clear()
            }, { error ->
                view?.showErrorSnackbar(error.localizedMessage)
            })
            .let(::addDisposable)
    }

    private fun isCurrentAndNewValueSameSecond(timestampCurrent: Long): Boolean =
        currentBCIData.timestamp / MILLISECONDS_IN_DAY == timestampCurrent / MILLISECONDS_IN_DAY

    //endregion

}